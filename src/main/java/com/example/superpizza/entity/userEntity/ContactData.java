package com.example.superpizza.entity.userEntity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contact_data")
public class ContactData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Email should be valid!")
    private String email;
    @NotEmpty(message = "Phone number should not be empty(123456)!")
    @Pattern(regexp = "\\+374\\s\\d{2}\\s\\d{6}|\\d{6}",
            message = "Phone number should be valid(123456)!")
    private String phoneNumber;

}
