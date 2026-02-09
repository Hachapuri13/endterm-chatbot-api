package edu.aitu.chatbot.dto;

public class SessionTokensUpdateRequest {
    private int totalTokensUsed;

    public SessionTokensUpdateRequest() {
    }

    public int getTotalTokensUsed() {
        return totalTokensUsed;
    }

    public void setTotalTokensUsed(int totalTokensUsed) {
        this.totalTokensUsed = totalTokensUsed;
    }
}
