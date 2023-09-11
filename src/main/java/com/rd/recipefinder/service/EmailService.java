package com.rd.recipefinder.service;

import com.rd.recipefinder.model.UserEntity;
import com.rd.recipefinder.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    @Value("${email.address}")
    private String email;

    public EmailService(UserRepository userRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.javaMailSender = javaMailSender;
    }

    public void registerEmail(UserEntity user, boolean isForEmail) {
        String token = UUID.randomUUID().toString(), subject;
        if (isForEmail) {
            user.setEmailVerificationToken(token);
            user.setIsVerified(false);
            subject = "Verification Email | Recipe Finder";
        } else {
            user.setPwordVerificationToken(token);
            subject = "Password Reset | Recipe Finder";
        }
        sendVerificationEmail(user, user.getEmail(), token, subject, isForEmail);
    }

    public Boolean isVerificationSuccessful(String token, boolean isForEmail) {
        Optional<UserEntity> user;
        if (isForEmail) {
            user = userRepository.findByEmailVerificationToken(token);
            if (user.isPresent()) {
                user.get().setIsVerified(true);
                user.get().setEmailVerificationToken(null);
                userRepository.save(user.get());
                return true;
            }
        } else {
            user = userRepository.findByPwordVerificationToken(token);
            return user.isPresent();
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

    private void sendVerificationEmail(UserEntity user, String to, String token, String subject, boolean isForEmail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        if (isForEmail) {
            mailMessage.setText(emailTextGenerator(token, user));
        } else {
            mailMessage.setText(pwordTextGenerator(token));
        }
        mailMessage.setFrom(email);
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

    private String pwordTextGenerator(String token) {
        return "Please click the attached link to reset your password: \n" +
                "http://localhost:8080/forgotPassword" + token + "\n\n" +
                "Sincerely,\n" +
                "Recipe Finder";
    }

}
