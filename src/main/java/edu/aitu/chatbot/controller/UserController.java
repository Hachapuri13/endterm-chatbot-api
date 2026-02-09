package edu.aitu.chatbot.controller;

import edu.aitu.chatbot.dto.UserRequest;
import edu.aitu.chatbot.model.User;
import edu.aitu.chatbot.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ChatService chatService;

    public UserController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<User> getAll() {
        return chatService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        return chatService.getUserById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody UserRequest req) {
        return chatService.createUser(req.getName(), req.getPersona(), req.isPremium());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody UserRequest req) {
        chatService.updateUser(id, req.getName(), req.getPersona(), req.isPremium());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        chatService.deleteUser(id);
    }
}
