package com.TaskHub.TaskHub.Service;

import com.TaskHub.TaskHub.entities.User;
import com.TaskHub.TaskHub.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    public String getUsernameById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getUsername();
        } else {
            return "Unknown User"; // Возвращаем это, если пользователь не найден по идентификатору
        }
    }


}