package com.example.superpizza.service.impl;

import com.example.superpizza.service.LoadAndUploadImgService;
import com.example.superpizza.utils.CurrentDirectory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class LoadAndUploadImgServiceImpl implements LoadAndUploadImgService {
    private final CurrentDirectory currentDirectory;

    @Value("${smartPizza.users.default.avatars.path}")
    private String defaultAvatar;

    @Override
    public String uploadImg(String path, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String uploadFileName = multipartFile.getOriginalFilename();
            String avatarName = System.nanoTime() + "_" + uploadFileName;

            Path fileNameAndPath = Paths.get(path, avatarName);
            Files.write(fileNameAndPath, multipartFile.getBytes());
            return avatarName;
        }
        return null;
    }

    @Override
    public byte[] getBytes(String path, String avatar) throws IOException {
        File file = new File(path + avatar);
        FileInputStream fis;
        if (!avatar.equals("")) {
            fis = new FileInputStream(file);
            return IOUtils.toByteArray(fis);
        } else {
            file = new File(currentDirectory.getCurrentDirectory() + defaultAvatar);
            fis = new FileInputStream(file);
        }
        return IOUtils.toByteArray(fis);
    }

}
