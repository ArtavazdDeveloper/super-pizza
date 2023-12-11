package com.example.superpizza.repository;

import com.example.superpizza.entity.userEntity.ContactData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactDataRepository extends JpaRepository<ContactData, Integer> {
    Optional<ContactData> findByEmail(String email);
}
