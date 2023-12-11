package com.example.superpizza.repository;

import com.example.superpizza.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByContactDataId(int id);
    Optional<User> findUserByContactDataEmail(String email);

    Optional<User> findUserById(int userId);
    @Query(value = "SELECT COUNT(*) AS size FROM users", nativeQuery = true)
    int userListSize();
}
