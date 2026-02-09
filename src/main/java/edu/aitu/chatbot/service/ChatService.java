package edu.aitu.chatbot.service;

import edu.aitu.chatbot.exception.InvalidInputException;
import edu.aitu.chatbot.exception.ResourceNotFoundException;
import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.model.ChatSession;
import edu.aitu.chatbot.model.User;
import edu.aitu.chatbot.patterns.factory.ChatParticipantFactory;
import edu.aitu.chatbot.patterns.factory.ParticipantSpec;
import edu.aitu.chatbot.patterns.factory.ParticipantType;
import edu.aitu.chatbot.patterns.singleton.AppLogger;
import edu.aitu.chatbot.repository.BotRepository;
import edu.aitu.chatbot.repository.ChatSessionRepository;
import edu.aitu.chatbot.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    private final BotRepository botRepository;
    private final UserRepository userRepository;
    private final ChatSessionRepository sessionRepository;

    public ChatService(BotRepository botRepository, UserRepository userRepository, ChatSessionRepository sessionRepository) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<Bot> getAllBots() {
        AppLogger.getInstance().info("Fetching all bots");
        return botRepository.getAll();
    }

    public Bot getBotById(int id) {
        AppLogger.getInstance().info("Fetching bot id=" + id);
        Bot bot = botRepository.getById(id);
        if (bot == null) {
            throw new ResourceNotFoundException("Bot with ID " + id + " not found.");
        }
        return bot;
    }

    public Bot createBot(String name, String greeting, String definition, int tokenLimit) {
        AppLogger.getInstance().info("Creating bot name=" + name);
        Bot bot = (Bot) ChatParticipantFactory.createParticipant(
                ParticipantType.BOT,
                new ParticipantSpec(0, name, greeting, definition, tokenLimit, null, null)
        );
        return botRepository.create(bot);
    }

    public void updateBot(int id, String newName, String newGreet, String newDef, int newLimit) {
        AppLogger.getInstance().info("Updating bot id=" + id);
        Bot updatedBot = (Bot) ChatParticipantFactory.createParticipant(
                ParticipantType.BOT,
                new ParticipantSpec(id, newName, newGreet, newDef, newLimit, null, null)
        );
        boolean isUpdated = botRepository.update(updatedBot);

        if (!isUpdated) {
            throw new ResourceNotFoundException("Bot with ID " + id + " not found.");
        }
    }

    public void deleteBot(int id) {
        AppLogger.getInstance().warn("Deleting bot id=" + id);
        boolean isDeleted = botRepository.delete(id);
        if (!isDeleted) {
            throw new ResourceNotFoundException("Bot with ID " + id + " not found.");
        }
    }

    public List<User> getAllUsers() {
        AppLogger.getInstance().info("Fetching all users");
        return userRepository.getAll();
    }

    public User getUserById(int id) {
        AppLogger.getInstance().info("Fetching user id=" + id);
        User user = userRepository.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
        return user;
    }

    public User createUser(String name, String persona, boolean isPremium) {
        AppLogger.getInstance().info("Creating user name=" + name);
        User user = (User) ChatParticipantFactory.createParticipant(
                ParticipantType.USER,
                new ParticipantSpec(0, name, null, null, null, persona, isPremium)
        );
        return userRepository.create(user);
    }

    public void updateUser(int id, String newName, String newPersona, boolean isPremium) {
        AppLogger.getInstance().info("Updating user id=" + id);
        User updatedUser = (User) ChatParticipantFactory.createParticipant(
                ParticipantType.USER,
                new ParticipantSpec(id, newName, null, null, null, newPersona, isPremium)
        );
        boolean success = userRepository.update(updatedUser);
        if (!success) {
            throw new ResourceNotFoundException("Cannot update: User with ID " + id + " not found.");
        }
    }

    public void deleteUser(int id) {
        AppLogger.getInstance().warn("Deleting user id=" + id);
        boolean success = userRepository.delete(id);
        if (!success) {
            throw new ResourceNotFoundException("Cannot delete: User with ID " + id + " not found.");
        }
    }

    public List<ChatSession> getAllSessions() {
        AppLogger.getInstance().info("Fetching all sessions");
        return sessionRepository.getAll();
    }

    public ChatSession getSessionById(int id) {
        AppLogger.getInstance().info("Fetching session id=" + id);
        ChatSession session = sessionRepository.getById(id);
        if (session == null) {
            throw new ResourceNotFoundException("Session with ID " + id + " not found.");
        }
        return session;
    }

    public ChatSession logChatSession(Bot bot, User user, Date startTime, int tokensUsed) {
        AppLogger.getInstance().info("Creating session botId=" + bot.getId() + " userId=" + user.getId());
        ChatSession session = new ChatSession(0, bot, user, startTime, tokensUsed);
        return sessionRepository.create(session);
    }

    public void updateSessionTokens(int id, int newTotalTokens) {
        if (newTotalTokens < 0) {
            throw new InvalidInputException("Token count cannot be negative.");
        }

        AppLogger.getInstance().info("Updating session tokens id=" + id + " tokens=" + newTotalTokens);

        boolean success = sessionRepository.updateTokens(id, newTotalTokens);
        if (!success) {
            throw new ResourceNotFoundException("Cannot update: Session with ID " + id + " not found.");
        }
    }

    public void deleteSession(int id) {
        AppLogger.getInstance().warn("Deleting session id=" + id);
        boolean success = sessionRepository.delete(id);
        if (!success) {
            throw new ResourceNotFoundException("Cannot delete: Session with ID " + id + " not found.");
        }
    }
}
