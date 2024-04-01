package com.TaskHub.TaskHub.Service;// MessageService.java
import com.TaskHub.TaskHub.entities.Message;
import com.TaskHub.TaskHub.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }
}
