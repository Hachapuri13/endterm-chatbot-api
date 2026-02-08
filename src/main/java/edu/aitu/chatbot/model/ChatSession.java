package model;

import java.util.Date;

public class ChatSession extends BaseEntity {
    private Bot bot;
    private User user;
    private Date startedAt;
    private int totalTokensUsed;

    public ChatSession(int id, Bot bot, User user, Date startedAt, int totalTokensUsed) {
        super(id);
        this.bot = bot;
        this.user = user;
        this.startedAt = startedAt;
        this.totalTokensUsed = totalTokensUsed;
    }

    public Bot getBot() { return bot; }
    public User getUser() { return user; }
    public Date getStartedAt() { return startedAt; }
    public int getTotalTokensUsed() { return totalTokensUsed; }
    public void setTotalTokensUsed(int totalTokensUsed) { this.totalTokensUsed = totalTokensUsed; }

    @Override
    public String toString() {
        return "Session ID: " + getId() +
                " | Bot ID: " + (bot != null ? bot.getId() : "null") +
                " | User ID: " + (user != null ? user.getId() : "null") +
                " | Tokens: " + totalTokensUsed;
    }
}