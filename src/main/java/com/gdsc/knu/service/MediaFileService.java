package com.gdsc.knu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.knu.dto.GoogleApiResultDto;
import com.gdsc.knu.dto.request.GoogleAiAnalysisRequestDto;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.dto.response.ai.GoogleAiAnalysisResponse;
import com.gdsc.knu.dto.response.ai.Part;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.repository.UserRepository;
import com.gdsc.knu.util.ConstVariables;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
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
    private final UserRepository userRepository;
    private final ConstVariables constVariables;
    private final RestTemplate restTemplate;
    private final WasteService wasteService;
    private final RankingService rankingService;

    @Transactional
    public MediaFile saveFile(Authentication authentication, MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = originalFileName;
        String fileExtension = FilenameUtils.getExtension(originalFileName);
        Path fileStorageLocation = Paths.get(constVariables.getFileDirectory()).toAbsolutePath().normalize();

        Long userId = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found with email " + authentication.getName())).getId();

        try {
            Files.createDirectories(fileStorageLocation);

            fileName = generateUniqueFileName(fileStorageLocation, originalFileName, fileExtension);

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            MediaFile mediaFile = new MediaFile(
                    fileName,
                    fileExtension,
                    targetLocation.toString(),
                    userId
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
        mediaFileRepository.deleteById(id);
    }

    public MediaFile updateFile(Authentication authentication, Long id, MultipartFile file) {
        deleteFile(id);
        return saveFile(authentication, file);
    }

    public void processImageAndCallExternalAPI(MediaFile file) {
        GetImageResponseDto getImageResponseDto = getFile(file.getId());
        String prompt = """
                Requirements :\s
                analyze image and make info with under conditions.
                
                condition 0. result will be json string.
                condition 1-1. Each trash must have one type. Types : [plastic, styrofoam, fiber, vinyl]
                condition 1-2. If trash is not in the list, it is generalWaste. So result must have 5 types of trash.
                condition 2. Each trash must have number of trashes. Number : [Integer]. ex) 0, 1, 2, 3...

                example result)
                String result = "{
                    "plastic" : 1,
                    "styrofoam" : 0,
                    "fiber" : 6,
                    "vinyl" : 3,
                    "generalWaste" : 1
                }";
                """;
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response;
        try {
            response = sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);
        } catch (JsonProcessingException e) {
            throw new ResourceNotFoundException("Failed to call external API");
        }

        GoogleApiResultDto googleApiResultDto = processApiResponse(response);
        int score = wasteService.calculateWasteScore(file.getUserId(), googleApiResultDto);
        rankingService.createRanking(file.getUserId(), score);
    }

    private GoogleApiResultDto processApiResponse(String responseJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            GoogleAiAnalysisResponse response = objectMapper.readValue(responseJson, GoogleAiAnalysisResponse.class);

            if (!(!response.candidates.isEmpty() && !response.candidates.get(0).content.parts.isEmpty()))
                throw new ResourceNotFoundException("No data found in Google AI API response");

            Part part = response.candidates.get(0).content.parts.get(0);
            System.out.println("Text: " + part.text);


            try {
                return objectMapper.readValue(part.text, GoogleApiResultDto.class);
            } catch (Exception e) {
                throw new ResourceNotFoundException("Failed to parse json");
            }

//            return new GoogleApiResultDto(part.text);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Invalid Response from Google AI API response" + e.getMessage());
        }
    }

    private String sendApiRequest(String prompt, String base64Image, String apiEndpoint) throws JsonProcessingException {
        GoogleAiAnalysisRequestDto request = new GoogleAiAnalysisRequestDto(prompt, base64Image); // 요청 객체 생성

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GoogleAiAnalysisRequestDto> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(apiEndpoint, entity, String.class);
    }
}