package com.example.superpizza.service;

import com.example.superpizza.entity.userEntity.ContactData;

import java.util.Optional;

public interface ContactDataService {
    ContactData save(ContactData contactData);

    Optional<ContactData> findUserEmail(String email);

    ContactData getDataById(int id);

    void deleteById(int id);
}
