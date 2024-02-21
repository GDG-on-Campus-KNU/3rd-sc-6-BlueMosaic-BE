package com.gdsc.knu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.knu.dto.request.GoogleAiAnalysisRequest;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.dto.response.ai.GoogleAiAnalysisResponse;
import com.gdsc.knu.dto.response.ai.Part;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.util.ConstVariables;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaFileService {
    private final MediaFileRepository mediaFileRepository;
    private final ConstVariables constVariables;

    private final RestTemplate restTemplate;

    @Transactional
    public MediaFile saveFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = originalFileName;
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        Path fileStorageLocation = Paths.get(constVariables.getFileDirectory()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);

            // 파일 이름 중복 확인 및 새 파일 이름 생성
            fileName = generateUniqueFileName(fileStorageLocation, originalFileName, fileExtension);

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            MediaFile mediaFile = new MediaFile(
                    fileName,
                    fileExtension,
                    targetLocation.toString()
            );
            return mediaFileRepository.save(mediaFile);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    private String generateUniqueFileName(Path fileStorageLocation, String originalFileName, String fileExtension) {
        String fileName = originalFileName;
        int fileNumber = 0;

        String baseName = FilenameUtils.removeExtension(originalFileName);

        while (Files.exists(fileStorageLocation.resolve(fileName))) {
            fileNumber++;
            fileName = String.format("%s(%d).%s", baseName, fileNumber, fileExtension);
        }
        return fileName;
    }

    public GetImageResponseDto getFile(Long id) {
        try {
            MediaFile mediaFile = mediaFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));
            String fileName = mediaFile.getUrl();
            Path fileStorageLocation = Paths.get(constVariables.getFileDirectory()).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            log.info("File path: " + filePath);

            if (!filePath.startsWith(fileStorageLocation)) {
                throw new SecurityException("Cannot access the file outside of the current directory.");
            }

            byte[] fileContent = Files.readAllBytes(filePath); // 파일 내용을 바이트 배열로 읽음
            String base64Encoded = Base64.getEncoder().encodeToString(fileContent); // 바이트 배열을 Base64로 인코딩

            return new GetImageResponseDto(mediaFile.getUrl(), mediaFile.getFileName(), base64Encoded);
        } catch (IOException e) {
            throw new RuntimeException("Could not read file with id " + id, e);
        }
    }

    public List<MediaFile> getAllFiles() {
        return mediaFileRepository.findAll();
    }

    public void deleteFile(Long id) {
        MediaFile mediaFile = mediaFileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + id));
//            Path fileToDelete = Paths.get(mediaFile.getUrl());
//            Files.delete(fileToDelete);
        mediaFileRepository.deleteById(id);
    }

    public MediaFile updateFile(Long id, MultipartFile file) {
        deleteFile(id);
        return saveFile(file);
    }

    public String processImageAndCallExternalAPI(MediaFile file) throws JsonProcessingException {
        GetImageResponseDto getImageResponseDto = getFile(file.getId());
        String prompt = "Requirements : \nanalyze image and make info.\n1. number of trashes. Number : [Integer]. ex) 1, 2, 3...\n2. type of trashes. Type : [paper, plastic bag, plastic, styrofoam, glass, metal, paper pack, clothing, battery]\n\n";
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response = sendApiRequest(prompt, getImageResponseDto.getData(), apiEndpoint);

        if (response == null) throw new ResourceNotFoundException("Failed to call external API");

        processApiResponse(response);

        // TODO : waste function 진행.

        return response;
    }

    private void processApiResponse(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GoogleAiAnalysisResponse response = objectMapper.readValue(responseJson, GoogleAiAnalysisResponse.class);

            // "parts" 안의 데이터에 접근
            if (!(!response.candidates.isEmpty() && !response.candidates.get(0).content.parts.isEmpty()))
                throw new ResourceNotFoundException("No data found in Google AI API response");

            Part part = response.candidates.get(0).content.parts.get(0);
            System.out.println("Text: " + part.text);

            // TODO : 여기에서 "part.text"를 사용하여 필요한 작업을 수행.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String sendApiRequest(String prompt, String base64Image, String apiEndpoint) throws JsonProcessingException {
        GoogleAiAnalysisRequest request = new GoogleAiAnalysisRequest(prompt, base64Image); // 요청 객체 생성

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GoogleAiAnalysisRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(apiEndpoint, entity, String.class);
    }
}