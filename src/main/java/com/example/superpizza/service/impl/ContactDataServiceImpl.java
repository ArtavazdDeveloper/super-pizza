package com.example.superpizza.service.impl;

import com.example.superpizza.entity.userEntity.ContactData;
import com.example.superpizza.repository.ContactDataRepository;
import com.example.superpizza.service.ContactDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactDataServiceImpl implements ContactDataService {
    private final ContactDataRepository contactDataRepository;


    @Override
    public ContactData save(ContactData contactData) {
        return contactDataRepository.save(contactData);
    }

    @Override
    public Optional<ContactData> findUserEmail(String email) {
        return contactDataRepository.findByEmail(email);
    }

    @Override
    public ContactData getDataById(int id) {
        return contactDataRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        contactDataRepository.deleteById(id);
    }
}
