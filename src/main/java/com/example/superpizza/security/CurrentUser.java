package com.example.superpizza.security;


import com.example.superpizza.entity.userEntity.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private final User user;

    public CurrentUser(User user) {
        super(user.getContactData().getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true,
                AuthorityUtils.createAuthorityList(user.getUserRole().name()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
