package com.TaskHub.TaskHub.Controllers;

import com.TaskHub.TaskHub.Service.MessageService;
import com.TaskHub.TaskHub.Service.ProjectService;
import com.TaskHub.TaskHub.Service.UserService;
import com.TaskHub.TaskHub.entities.Message;
import com.TaskHub.TaskHub.entities.Project;
import com.TaskHub.TaskHub.entities.User;
import com.TaskHub.TaskHub.repo.ProjectRepository;
import com.TaskHub.TaskHub.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @GetMapping("/add_project")
    public String getAddProjectPage(@ModelAttribute("project") Project project) {
        return "add_project";
    }

    // Ваш контроллер
    @PostMapping("/add_project")
    public String saveProject(@ModelAttribute("project") Project project, HttpSession session, Model model) {
        // Получение информации о текущем пользователе из сессии или из другого источника
        Long currentUserId = (Long) session.getAttribute("userId");

        // Установка значения поля CreatedBy в сущности Project
        project.setCreatedBy(currentUserId);

        // Сохранение проекта
        projectRepository.save(project);

        // Добавление сообщения об успешном сохранении
        model.addAttribute("message", "Submitted Successfully");

        // Редирект на главную страницу
        return "redirect:/main";
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("newMessage", new Message()); // Инициализируем объект нового сообщения
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
            return "redirect:/login";
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
            return "redirect:/main";
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

    @ModelAttribute("currentUser")
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping("/main")
    public String showMainPage() {
        return "main";
    }
    @GetMapping("/profile")
    public String userProfile() {
        return "profile";
    }

    @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.removeAttribute("user");
            session.setAttribute("authenticated", false);
            return "redirect:/home";
        }

    @Autowired
    private MessageService messageService;


    @GetMapping("/")
    public String message(Model model) {
        model.addAttribute("messages", messageService.getAllMessages());
        model.addAttribute("newMessage", new Message()); // Добавляем новый объект сообщения в модель
        model.addAttribute("userService", userService); // Передаем сервис пользователей в представление
        return "main"; // Возвращаем имя вашего Thymeleaf-шаблона
    }

    @PostMapping("/addMessage")
    public String addMessage(Message message) {
        messageService.addMessage(message);
        return "redirect:/main";
    }

    @Autowired
    private ProjectService projectService;
    @ModelAttribute
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "home"; // Возвращаем имя вашего Thymeleaf-шаблона
    }
}





