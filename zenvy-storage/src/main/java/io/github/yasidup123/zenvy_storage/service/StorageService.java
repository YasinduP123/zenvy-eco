package io.github.yasidup123.zenvy_storage.service;

import io.github.yasidup123.zenvy_storage.dto.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    FileUploadResponse store(MultipartFile file, String subFolder);
    Resource loadAsResource(String filename, String subFolder);
    Path load(String filename, String subFolder);
    boolean delete(String filename, String subFolder);
}