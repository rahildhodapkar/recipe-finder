package com.rd.recipefinder.controller;

import com.rd.recipefinder.model.RolesEntity;
import com.rd.recipefinder.model.UserEntity;
import com.rd.recipefinder.repository.RolesRepository;
import com.rd.recipefinder.repository.UserRepository;
import com.rd.recipefinder.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class LoginController {
    private final PasswordEncoder encoder;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/createAccount")
    public String createAccount() {
        return "createAccount";
    }

    @GetMapping("/resetPassword")
    public String resetPassword() {
        return "/resetPassword";
    }

    @PostMapping("/createAccount")
    public String handleCreateAccount(@RequestParam(name = "username") String username,
                                      @RequestParam(name = "password") String password,
                                      @RequestParam(name = "reEnterPassword") String reEnterPassword,
                                      @RequestParam(name = "email") String email,
                                      RedirectAttributes redirectAttributes) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "\"" +username +"\"" + " is already taken. Please enter another username.");
            return "redirect:/createAccount";
        } else if (!password.equals(reEnterPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match. Please try again.");
            return "redirect:/createAccount";
        } else if (!isValidEmail(email)) {
            redirectAttributes.addFlashAttribute("error", "\"" + email + "\"" + " is an invalid email. Please enter another email.");
            return "redirect:/createAccount";
        } else if (userRepository.findByEmail(email).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "\"" + email + "\"" + " is already being used. Please enter another email.");
            return "redirect:/createAccount";
        } else {
            userRepository.save(new UserEntity(username, encoder.encode(password), email, null, null));
            rolesRepository.save(new RolesEntity(username, "ROLE_USER"));
            emailService.registerEmail(userRepository.findByUsernameAndEmail(username, email));
        }
        return "/success";

    }

    // resetPassword is kind of useless right now

    @PostMapping("/resetPassword")
    public String handleResetPassword(@RequestParam(name = "username") String username,
                                      @RequestParam(name = "oldPassword") String oldPassword,
                                      @RequestParam(name = "newPassword") String newPassword,
                                      @RequestParam(name = "reEnterNewPassword") String reEnterNewPassword,
                                      RedirectAttributes redirectAttributes) {
        Optional<UserEntity> entity = userRepository.findByUsername(username);
        if (entity.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "You have not created an account. Please return to login page to do so.");
            return "redirect:/resetPassword";
        } else if (!encoder.matches(oldPassword, entity.get().getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Old password does not match account's current password.");
            return "redirect:/resetPassword";
        }
        return handleResetPasswordHelper(entity.get(), newPassword, reEnterNewPassword, redirectAttributes);
    }

    private String handleResetPasswordHelper(UserEntity user, String newPassword, String reEnterNewPassword, RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(reEnterNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "New passwords do not match");
            return "redirect:/resetPassword";
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        return "/success";
    }

    private static Boolean isValidEmail(String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }

    public LoginController(PasswordEncoder encoder, RolesRepository rolesRepository,
                           UserRepository userRepository, EmailService emailService) {
        this.encoder = encoder;
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

}
