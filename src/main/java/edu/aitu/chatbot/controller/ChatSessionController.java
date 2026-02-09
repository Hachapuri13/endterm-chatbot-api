package edu.aitu.chatbot.controller;

import edu.aitu.chatbot.dto.SessionCreateRequest;
import edu.aitu.chatbot.dto.SessionTokensUpdateRequest;
import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.model.ChatSession;
import edu.aitu.chatbot.model.User;
import edu.aitu.chatbot.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class ChatSessionController {

    private final ChatService chatService;

    public ChatSessionController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public List<ChatSession> getAll() {
        return chatService.getAllSessions();
    }

    @GetMapping("/{id}")
    public ChatSession getById(@PathVariable int id) {
        return chatService.getSessionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatSession create(@RequestBody SessionCreateRequest req) {
        Bot bot = chatService.getBotById(req.getBotId());
        User user = chatService.getUserById(req.getUserId());
        return chatService.logChatSession(bot, user, new Date(), req.getTokensUsed());
    }

    @PatchMapping("/{id}/tokens")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTokens(@PathVariable int id, @RequestBody SessionTokensUpdateRequest req) {
        chatService.updateSessionTokens(id, req.getTotalTokensUsed());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        chatService.deleteSession(id);
    }
}
