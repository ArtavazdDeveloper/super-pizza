package com.example.superpizza.utils;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Data
@Service
public class CurrentDirectory {
    private final String currentDirectory = Paths.get("").toAbsolutePath().toString();
}
