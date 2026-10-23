package io.github.yasidup123.zenvy_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String fileName;
    private String originalName;
    private String fileUrl;
    private String fileType;
    private long size;
    private LocalDateTime uploadedAt;
}