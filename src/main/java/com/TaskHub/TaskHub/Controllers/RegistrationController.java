package com.TaskHub.TaskHub.Controllers;

import com.TaskHub.TaskHub.Service.UserService;
import com.TaskHub.TaskHub.entities.User;
import com.TaskHub.TaskHub.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }
    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("user") User user) {
        return "home";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        // Проверяем, существует ли пользователь с таким же именем пользователя или адресом электронной почты
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("message", "User with the same username or email already exists");
            return "registration"; // Возвращаемся на страницу регистрации с сообщением об ошибке
        }
        if (!user.getUsername().matches("^[a-zA-Z]+$")) {
            model.addAttribute("message", "Username should contain only English letters");
            return "registration";
        }
        if (!user.getPassword().matches(".*[A-Z].*") || !user.getPassword().matches(".*\\d.*")) {
            model.addAttribute("message", "Password should contain at least one uppercase letter and one digit");
            return "registration";
        }
        else {
            // Если пользователя с таким именем пользователя или адресом электронной почты нет, сохраняем нового пользователя
            userRepository.save(user);
            model.addAttribute("message", "Submitted Successfully");
            return "redirect:/home";
        }
    }


    //  LOGIN PANEL
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("message", "User successfully authenticated");
            return "redirect:/home";
        } else {
            model.addAttribute("message", "Authentication failed");
            return "login";
        }
    }
    @GetMapping("/forgot-password")
    public String showEmailCheck() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String changePassword(@RequestParam String email, @RequestParam String newPassword, Model model) {
        // Проверяем наличие почты в базе данных
        User user = userRepository.findByEmail(email);
        if (user == null) {
            model.addAttribute("message", "Email not found");
            return "forgot-password";
        }

        // Проверяем, содержит ли новый пароль хотя бы одну заглавную букву и одну цифру
        if (!isValidPassword(newPassword)) {
            model.addAttribute("message", "Password must contain at least one uppercase letter and one digit");
            return "forgot-password";
        }

        // Устанавливаем новый пароль и сохраняем пользователя
        user.setPassword(newPassword);
        userRepository.save(user);

        model.addAttribute("message", "Password changed successfully");
        return "redirect:/login";
    }

    // Метод для проверки пароля на наличие заглавной буквы и цифры
    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        return Pattern.compile(regex).matcher(password).matches();
    }

}


