package com.gdsc.knu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.knu.dto.MarineApiResultDto;
import com.gdsc.knu.dto.WasteApiResultDto;
import com.gdsc.knu.dto.response.GetImageResponseDto;
import com.gdsc.knu.dto.response.ai.Part;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.exception.ResourceNotFoundException;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.repository.UserRepository;
import com.gdsc.knu.util.ConstVariables;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private final WasteService wasteService;
    private final RankingService rankingService;
    private final GoogleAiService googleAiService;

    @Transactional
    public GetImageResponseDto saveFile(Authentication authentication, MultipartFile file) {
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
            GetImageResponseDto getImageResponseDto = new GetImageResponseDto(mediaFile.getId(), mediaFile.getUserId(), mediaFile.getUrl(), mediaFile.getFileName(), mediaFile.getFileType(), Base64.getEncoder().encodeToString(file.getBytes()));

            mediaFileRepository.save(mediaFile);

            return getImageResponseDto;
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

            return new GetImageResponseDto(mediaFile.getId(), mediaFile.getUserId(), mediaFile.getUrl(), mediaFile.getFileName(), mediaFile.getFileType(), base64Encoded);
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

    public GetImageResponseDto updateFile(Authentication authentication, Long id, MultipartFile file) {
        deleteFile(id);
        return saveFile(authentication, file);
    }

    public void processWasteImageAnalysis(GetImageResponseDto getImageResponseDto) {
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

        String response = googleAiService.sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);

        Part part = googleAiService.parseGoogleApiResponse(response);
        WasteApiResultDto wasteApiResultDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            wasteApiResultDto = objectMapper.readValue(part.text, WasteApiResultDto.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to parse json in waste image analysis.");
        }
        int score = wasteService.calculateWasteScore(getImageResponseDto.getUserId(), wasteApiResultDto);
        rankingService.createRanking(getImageResponseDto.getUserId(), score);
    }

    public void processMarineImageAnalysis(GetImageResponseDto getImageResponseDto) {
        String prompt = """
                Requirements : analyze the image and provide "type : number" information.\s
                condition 1. Each entity must have type of marine animal. if entity not in Type, then mark as others. Type : []
                condition 2. Each entity must have number of entity. ex) 1, 2, 3...
                condition 3. words in english.
                condition 4. don't include human or inanimate.
                
                ex)
                Estuarine Fish : 31, Menhaden : 24, others : 62
                """;
        String apiEndpoint = constVariables.getAPI_URL() + constVariables.getAPI_KEY();

        String response = googleAiService.sendApiRequest(prompt, getImageResponseDto.getBase64EncodedImage(), apiEndpoint);

        Part part = googleAiService.parseGoogleApiResponse(response);
        MarineApiResultDto marineApiResultDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            marineApiResultDto = objectMapper.readValue(part.text, MarineApiResultDto.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to parse json in marine image analysis.");
        }
        // TODO : Implement marine score calculation & other related logic.
    }
}