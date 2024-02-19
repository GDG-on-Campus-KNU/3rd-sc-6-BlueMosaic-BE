package com.gdsc.knu.service;

import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.util.ConstVariables;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MediaFileService {
    private final MediaFileRepository mediaFileRepository;
    private final ConstVariables constVariables;

    @Transactional
    public MediaFile saveFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Path fileStorageLocation = Paths.get(constVariables.getFileDirectory()).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);

            MediaFile mediaFile = new MediaFile(
                    fileName,
                    FilenameUtils.getExtension(fileName),
                    targetLocation.toString()
            );
            return mediaFileRepository.save(mediaFile);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public MediaFile getFile(Long id) {
        return mediaFileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }

    public List<MediaFile> getAllFiles() {
        return mediaFileRepository.findAll();
    }

    public void deleteFile(Long id) {
        MediaFile mediaFile = getFile(id);
        try {
            Path fileToDelete = Paths.get(mediaFile.getUrl());
            Files.delete(fileToDelete);
            mediaFileRepository.deleteById(id);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file with id " + id, e);
        }
    }

    public MediaFile updateFile(Long id, MultipartFile file) {
        deleteFile(id);
        return saveFile(file);
    }
}
