package com.rd.recipefinder.service;

import com.rd.recipefinder.model.UserEntity;
import com.rd.recipefinder.repository.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public void registerEmail(UserEntity user) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setIsVerified(false);
        sendVerificationEmail(user, user.getEmail(), token);
    }

    public Boolean isVerificationSuccessful(String token, String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (user.get().getVerificationToken().equals(token)) {
                user.get().setIsVerified(true);
                user.get().setVerificationToken(null);
                userRepository.save(user.get());
                return true;
            }
        }
        return false;
    }

    public Boolean isVerified(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get().getIsVerified();
        }
        return false;
    }

    private void sendVerificationEmail(UserEntity user, String to, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Verification Email | Recipe Finder");
        mailMessage.setText(emailTextGenerator(token, user));
        mailMessage.setFrom("rdgms10@gmail.com");
        javaMailSender.send(mailMessage);
    }

    private String emailTextGenerator(String token, UserEntity user) {
        return "Hey, " + user.getUsername() + "!\n\n" +
               "Thank you for choosing to use our product. We hope it " +
               "serves you well! To complete your account sign-up, please " +
               "click the attached link to verify your email: \n" +
               "http://localhost:8080/" + token + "\n\n" +
               "Sincerely,\n" +
               "Recipe Finder";
    }

}
