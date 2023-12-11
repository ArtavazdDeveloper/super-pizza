package com.example.superpizza.service;

public interface MailService {

    void sendOrderCheck(String to, String subject, String text);
}
