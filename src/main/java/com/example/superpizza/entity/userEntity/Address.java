package com.example.superpizza.entity.userEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Region should not be empty!")
    private String region;
    @NotEmpty(message = "City should not be empty!")
    private String city;
    @NotEmpty(message = "Street should not be empty!")
    private String street;
    @NotEmpty(message = "Apartment number should not be empty!")
    private String apartmentNumber;
    @ManyToOne
    private User user;
}
