package com.example.superpizza.repository.util;

import com.example.superpizza.entity.util.PhoneOperatorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeOperatorRepository extends JpaRepository<PhoneOperatorCode, Integer> {
}
