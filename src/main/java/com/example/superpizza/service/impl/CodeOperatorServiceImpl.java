package com.example.superpizza.service.impl;

import com.example.superpizza.entity.util.PhoneOperatorCode;
import com.example.superpizza.repository.util.CodeOperatorRepository;
import com.example.superpizza.service.CodeOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeOperatorServiceImpl implements CodeOperatorService {
    private final CodeOperatorRepository codeOperatorRepository;

    @Override
    public List<PhoneOperatorCode> getAll() {
        return codeOperatorRepository.findAll();
    }
}
