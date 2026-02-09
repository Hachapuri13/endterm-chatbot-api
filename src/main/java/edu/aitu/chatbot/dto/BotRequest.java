package edu.aitu.chatbot.dto;

public class BotRequest {
    private String name;
    private String greeting;
    private String definition;
    private int tokenLimit;

    public BotRequest() {
    }

    public String getName() {
        return name;
    }

    public String getGreeting() {
        return greeting;
    }

    public String getDefinition() {
        return definition;
    }

    public int getTokenLimit() {
        return tokenLimit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setTokenLimit(int tokenLimit) {
        this.tokenLimit = tokenLimit;
    }
}
