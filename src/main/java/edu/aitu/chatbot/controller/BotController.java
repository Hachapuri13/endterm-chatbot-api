package edu.aitu.chatbot.controller;

import edu.aitu.chatbot.dto.BotRequest;
import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bots")
public class BotController {

    private final ChatService chatService;

    public BotController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<Bot> getAll() {
        return chatService.getAllBots();
    }

    @GetMapping("/{id}")
    public Bot getById(@PathVariable int id) {
        return chatService.getBotById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bot create(@RequestBody BotRequest req) {
        return chatService.createBot(req.getName(), req.getGreeting(), req.getDefinition(), req.getTokenLimit());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody BotRequest req) {
        chatService.updateBot(id, req.getName(), req.getGreeting(), req.getDefinition(), req.getTokenLimit());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        chatService.deleteBot(id);
    }
}
