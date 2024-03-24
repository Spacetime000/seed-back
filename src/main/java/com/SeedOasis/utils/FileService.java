package com.SeedOasis.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${resource.uploadUrl}")
    private String uploadUrl;

    private final static String PREFIX = "/files";


    /**
     * 파일을 업로드.
     *
     * @param multipartFile
     * @param path          e.g. /exam
     * @return e.g. UploadFile : '/files/exam/ + uuid + . + ext'
     * @throws IOException
     */
    public UploadFile upload(MultipartFile multipartFile, String path) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String originalName = multipartFile.getOriginalFilename();
        String ext = StringUtils.getFilenameExtension(originalName);
        String destFileName = uuid + "." + ext;
        Long fileSize = multipartFile.getSize();
        File destFile = new File(uploadUrl + path + "/" + destFileName);
        destFile.getParentFile().mkdirs();
        multipartFile.transferTo(destFile);
        String filePath = PREFIX + path + "/" + destFileName;
        return UploadFile.builder()
                .originalName(originalName)
                .fileName(destFileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();

    }

    public void delete(String path) {
//        File file = new File(path.replace(PREFIX, uploadUrl));
        Path filePath = Paths.get(path.replace(PREFIX, uploadUrl));
        try {
            Files.deleteIfExists(filePath);
        } catch (DirectoryNotEmptyException e) {
            System.out.println("디렉토리가 비어있지 않음.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
