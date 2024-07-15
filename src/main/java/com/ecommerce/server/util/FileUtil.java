package com.ecommerce.server.util;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtil {
    @Value("${application.file.uploads.photos-output-path}")
    private String fileUploadPath;

    public String uploadFile(
            MultipartFile sourceFile
    ) {
        File targetFolder = new File(fileUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs();
            if (!folderCreated) {
                log.warn("Failed to create the target folder: " + targetFolder);
                return null;
            }
        }

        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = fileUploadPath + separator + System.currentTimeMillis() + "." + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File saved to: " + targetFilePath);
            return targetFilePath.substring(fileUploadPath.length()+1);
        } catch (IOException e) {
            log.error("File was not saved", e);
        }
        return null;
    }

    public String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }


    public byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        try {
            Path filePath = new File(fileUploadPath + separator + fileUrl).toPath();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.warn("No file found in the path {}", fileUrl);
        }
        return null;
    }

    public boolean deleteFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return false;
        }
        try {
            Path filePath = new File(fileUploadPath + separator + fileUrl).toPath();
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.warn("No file found in the path {}", fileUrl);
        }
        return false;
    }
}
