package com.example.superpizza.entity.userEntity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters!")
    private String name;
    @NotEmpty(message = "Surname should not be empty!")
    @Size(min = 3, max = 30, message = "Surname should be between 3 and 30 characters!")
    private String surname;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "dob")
    private LocalDate dateOfBirth;
    @NotEmpty(message = "Password should not be empty!")
    @Size(min = 6, message = "Password should be between 6 and 30 characters!")
    private String password;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private final Date registrationDate = new Date();
    @OneToOne
    private ContactData contactData;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;
    @Column(name = "profile_img")
    private String avatar;
    private boolean enabled;
    private String token;
}
