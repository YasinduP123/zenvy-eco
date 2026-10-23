package io.github.yasidup123.zenvy_storage.utils;

import io.github.yasidup123.zenvy_storage.config.StorageProperties;
import io.github.yasidup123.zenvy_storage.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

public class FileValidationUtils {

    public static void validateFile(MultipartFile file, StorageProperties properties) {
        if (file == null || file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }

        if (file.getSize() > properties.getMaxFileSize()) {
            throw new StorageException("File size exceeds maximum permitted limit.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !properties.getAllowedMimeTypes().contains(contentType)) {
            throw new StorageException("Invalid file type: " + contentType);
        }
    }
}