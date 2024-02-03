package com.example.superpizza.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.superpizza.entity.userEntity.Address;
import com.example.superpizza.entity.userEntity.ContactData;
import com.example.superpizza.entity.userEntity.User;
import com.example.superpizza.security.CurrentUser;
import com.example.superpizza.service.CodeOperatorService;
import com.example.superpizza.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CodeOperatorService codeOperatorService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/register")
    public String getRequestRegisterPage(ModelMap modelMap,
                                         @ModelAttribute User user,
                                         @ModelAttribute ContactData contactData,
                                         @ModelAttribute Address address) {
        modelMap.addAttribute("maxDate", LocalDate.now());
        modelMap.addAttribute("operatorCodes", codeOperatorService.getAll());
        return "signup";
    }

    @PostMapping("/register")
    public String postRequestRegisterPage(@ModelAttribute @Valid User user, BindingResult userBindingResult,
                                          @ModelAttribute @Valid ContactData contactData, BindingResult phoneBindingResult,
                                          @ModelAttribute @Valid Address address, BindingResult addressBindingResult,
                                          ModelMap modelMap,
                                          @RequestParam("profileImg") MultipartFile multipartFile,
                                          @RequestParam String operatorCode,
                                          RedirectAttributes redirectAttributes) throws IOException {

        if (userBindingResult.hasErrors() || phoneBindingResult.hasErrors() || addressBindingResult.hasErrors()) {
            modelMap.addAttribute("operatorCodes", codeOperatorService.getAll());
            return "signup";
        }
        contactData.setPhoneNumber("+374 " + operatorCode + " " + contactData.getPhoneNumber());

        User save = userService.save(user, contactData, address, multipartFile);

        if (save == null) {
            redirectAttributes.addFlashAttribute("msg", "Username or password is exist");
            return "redirect:/user/register";
        }
        return "redirect:/";
    }

    @GetMapping("/verify")
    public String getVerifyUser(@RequestParam String email,
                                @RequestParam UUID token) {

        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty() || user.get().isEnabled()) {
            return "redirect:/";
        }
        if (user.get().getToken().equals(token.toString())) {
            user.get().setEnabled(true);
            user.get().setToken(null);
            userService.saveVerifyData(user.get());
        }
        return "redirect:/";
    }

    @GetMapping("/account")
    public String getUserAccountPage(ModelMap modelMap) {
        modelMap.addAttribute("operatorCodes", codeOperatorService.getAll());
        return "userAccount";
    }

    @PostMapping("/{id}/updateData")
    public String updateProfileData(RedirectAttributes redirectAttributes,
                                    @AuthenticationPrincipal CurrentUser currentUser,
                                    @PathVariable("id") int userId,
                                    @RequestParam String name,
                                    @RequestParam String surname,
                                    @RequestParam String operatorCode,
                                    @RequestParam String phoneNumber) {

        if (currentUser.getUser().getId() == userId) {
            String response = userService.updateUserData(userId, name, surname, operatorCode, phoneNumber);
            if (response.equals("reject")) {
                redirectAttributes.addFlashAttribute("msg", "Please enter correct data.");
                return "redirect:/user/account";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/deactivate_account")
    public String getDeactivateAccountPage() {
        return "deactivate_account";
    }

    @PostMapping("/deactivate_account")
    public String postDeactivateAccount(RedirectAttributes redirectAttributes,
                                        @AuthenticationPrincipal CurrentUser currentUser,
                                        @RequestParam String password) {

        if (passwordEncoder.matches(password, currentUser.getUser().getPassword())) {
            User user = currentUser.getUser();
            user.setEnabled(false);
            userService.saveUserObject(user);
            return "redirect:/logout";
        }
        redirectAttributes.addFlashAttribute("msg", "Incorrect password!");
        return "redirect:/user/deactivate_account";
    }

    @GetMapping("/change_password")
    public String getChangePasswordPage() {
        return "change_password";

    }

    @PostMapping("/change_password")
    public String postChangePassword(RedirectAttributes redirectAttributes,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword,
                                     @AuthenticationPrincipal CurrentUser currentUser) {
        if (passwordEncoder.matches(oldPassword, currentUser.getUser().getPassword())) {
            User user = currentUser.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.saveUserObject(user);
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("msg", "Incorrect password!");
        return "redirect:/user/change_password";
    }

}
