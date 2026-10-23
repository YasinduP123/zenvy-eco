package io.github.yasidup123.zenvy_storage.service.impl;

import io.github.yasidup123.zenvy_storage.config.StorageProperties;
import io.github.yasidup123.zenvy_storage.dto.FileUploadResponse;
import io.github.yasidup123.zenvy_storage.exception.StorageException;
import io.github.yasidup123.zenvy_storage.service.StorageService;
import io.github.yasidup123.zenvy_storage.utils.FileValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private final StorageProperties storageProperties;

    @Autowired
    public StorageServiceImpl(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.rootLocation = Paths.get(storageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage directory location.", e);
        }
    }

    @Override
    public FileUploadResponse store(MultipartFile file, String subFolder) {
        FileValidationUtils.validateFile(file, storageProperties);

        String originalFilename = StringUtils.cleanPath(
                file.getOriginalFilename() != null ? file.getOriginalFilename() : "file"
        );
        String fileExtension = getFileExtension(originalFilename);
        String generatedFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetDirectory = this.rootLocation.resolve(subFolder).normalize();
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            Path destinationFile = targetDirectory.resolve(Paths.get(generatedFileName)).normalize();

            if (!destinationFile.getParent().startsWith(this.rootLocation)) {
                throw new StorageException("Cannot store file outside current storage directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            String relativeFilePath = subFolder + "/" + generatedFileName;

            return FileUploadResponse.builder()
                    .fileName(generatedFileName)
                    .originalName(originalFilename)
                    .fileUrl(relativeFilePath)
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .uploadedAt(LocalDateTime.now())
                    .build();

        } catch (IOException e) {
            throw new StorageException("Failed to store file: " + originalFilename, e);
        }
    }

    @Override
    public Path load(String filename, String subFolder) {
        return rootLocation.resolve(subFolder).resolve(filename).normalize();
    }

    @Override
    public Resource loadAsResource(String filename, String subFolder) {
        try {
            Path file = load(filename, subFolder);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public boolean delete(String filename, String subFolder) {
        try {
            Path file = load(filename, subFolder);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Could not delete file: " + filename, e);
        }
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return (lastIndex != -1) ? filename.substring(lastIndex) : "";
    }
}