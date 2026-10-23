package io.github.yasidup123.zenvy_storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "zenvy.storage")
public class StorageProperties {
    private String uploadDir = "uploads";
    private long maxFileSize = 10485760; // 10MB default
    private List<String> allowedMimeTypes = List.of(
            "image/jpeg",
            "image/png",
            "image/webp",
            "application/pdf"
    );
}