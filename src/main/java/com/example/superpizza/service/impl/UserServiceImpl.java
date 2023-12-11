package com.example.superpizza.service.impl;

import com.example.superpizza.entity.userEntity.Address;
import com.example.superpizza.entity.userEntity.ContactData;
import com.example.superpizza.entity.userEntity.User;
import com.example.superpizza.entity.userEntity.UserRole;
import com.example.superpizza.repository.UserRepository;
import com.example.superpizza.security.CurrentUser;
import com.example.superpizza.service.*;
import com.example.superpizza.utils.CurrentDirectory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LoadAndUploadImgService loadAndUploadImgService;
    private final ContactDataService contactDataService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final CurrentDirectory currentDirectory;

    @Value("${smartPizza.users.avatar.path}")
    private String userAvatarImagePath;

    @Value("${application.url}")
    private String applicationUrl;


    @Override
    public User save(User user, ContactData contactData, Address address, MultipartFile multipartFile) throws IOException {

        String email = contactData.getEmail();
        Optional<ContactData> findUserEmail = contactDataService.findUserEmail(email);

        if (findUserEmail.isEmpty()) {
            String avatarName = loadAndUploadImgService.uploadImg(
                    currentDirectory.getCurrentDirectory() + userAvatarImagePath, multipartFile);
            user.setAvatar(avatarName);
            user.setUserRole(UserRole.USER);

            ContactData userContactData = contactDataService.save(contactData);

            user.setContactData(userContactData);
            user.setEnabled(false);
            user.setToken(UUID.randomUUID().toString());

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            mailService.sendOrderCheck(userContactData.getEmail(),
                    "Verify account", "\nPlease click url and verify your account.\n" +
                            applicationUrl + "/user/verify?email=" + user.getContactData().getEmail() +
                            "&token=" + user.getToken());
            User save = userRepository.save(user);
            address.setUser(save);
            addressService.save(address);

            return save;
        }
        return null;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByContactDataEmail(email);
    }

    @Override
    public void saveVerifyData(User user) {
        userRepository.save(user);
    }

    @Override
    public String updateUserData(int id, String name, String surname, String operatorCode, String phoneNumber) {
        Optional<User> user = userRepository.findById(id);
        String phone = "+374 " + operatorCode + " " + phoneNumber;

        if (name.trim().length() >= 3 && surname.trim().length() >= 3 &&
                Pattern.matches("\\+374\\s\\d{2}\\s\\d{6}|\\d{6}", phone)) {
            if (user.isPresent()) {
                user.get().setName(name);
                user.get().setSurname(surname);
                user.get().getContactData().setPhoneNumber(phone);
                ContactData contactData = contactDataService.getDataById(user.get().getContactData().getId());
                contactData.setPhoneNumber(phone);
                contactDataService.save(contactData);
                user.get().setContactData(contactData);

                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                CurrentUser principal1 = (CurrentUser) principal;
                principal1.getUser().setName(name);
                principal1.getUser().setSurname(surname);
                principal1.getUser().getContactData().setPhoneNumber(phone);
                principal1.getUser().setContactData(contactData);
                userRepository.save(user.get());
                return "ok";
            }

        }
        return "reject";
    }

    @Override
    public void saveUserObject(User user) {
        userRepository.save(user);
    }

    @Override
    public int userListSize() {
        return userRepository.userListSize() - 1;
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findUserById(id).get();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
