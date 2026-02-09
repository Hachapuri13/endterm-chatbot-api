package edu.aitu.chatbot.dto;

public class SessionCreateRequest {
    private int botId;
    private int userId;
    private int tokensUsed;

    public SessionCreateRequest() {
    }

    public int getBotId() {
        return botId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTokensUsed() {
        return tokensUsed;
    }

    public void setBotId(int botId) {
        this.botId = botId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTokensUsed(int tokensUsed) {
        this.tokensUsed = tokensUsed;
    }
}
