package com.example.superpizza.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface LoadAndUploadImgService {
    String uploadImg(String path, MultipartFile multipartFile) throws IOException;
    byte[] getBytes(String path, String avatar) throws IOException;
}
