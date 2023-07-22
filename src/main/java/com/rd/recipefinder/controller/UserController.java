package com.rd.recipefinder.controller;

import com.rd.recipefinder.model.RolesEntity;
import com.rd.recipefinder.model.UserEntity;
import com.rd.recipefinder.repository.RolesRepository;
import com.rd.recipefinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

/**
 * Goals:
 * Email verification system
 * Change/Forgot password
 */

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder encoder;
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    public UserController(RolesRepository rolesRepository, UserRepository userRepository) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/createAccount")
    public String createAccount() {
        return "/createAccount";
    }

    @PostMapping("/createAccount")
    public String handleCreateAccount(@RequestParam(name = "username") String username,
                                      @RequestParam(name = "password") String password,
                                      @RequestParam(name = "reEnterPassword") String reEnterPassword,
                                      @RequestParam(name = "email") String email,
                                      RedirectAttributes redirectAttributes) {
        if (userRepository.findByUsername(username).isPresent()) {
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
            userRepository.save(new UserEntity(username, encoder.encode(password), email));
            rolesRepository.save(new RolesEntity(username, "ROLE_USER"));
        }
        return "/success";

    }

    private static Boolean isValidEmail(String email) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(email).matches();
    }
}
