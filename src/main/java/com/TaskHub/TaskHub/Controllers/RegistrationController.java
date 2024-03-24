package com.TaskHub.TaskHub.Controllers;

import com.TaskHub.TaskHub.Service.UserService;
import com.TaskHub.TaskHub.entities.User;
import com.TaskHub.TaskHub.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        if (!user.getUsername().matches("^[a-zA-Z0-9]+$")) {
            model.addAttribute("message", "Username should contain only English letters and digits");
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
}
