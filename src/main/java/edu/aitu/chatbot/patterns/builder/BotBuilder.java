package edu.aitu.chatbot.patterns.builder;

import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.exception.InvalidInputException;

public class BotBuilder {
    private int id;
    private String name;
    private String greeting;
    private String definition;
    private int tokenLimit;

    public BotBuilder id(int id) {
        this.id = id;
        return this;
    }

    public BotBuilder name(String name) {
        this.name = name;
        return this;
    }

    public BotBuilder greeting(String greeting) {
        this.greeting = greeting;
        return this;
    }

    public BotBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    public BotBuilder tokenLimit(int tokenLimit) {
        this.tokenLimit = tokenLimit;
        return this;
    }

    public Bot build() {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Bot name cannot be empty.");
        }
        if (tokenLimit <= 0) {
            throw new InvalidInputException("Token limit must be positive.");
        }
        return new Bot(id, name, greeting, definition, tokenLimit);
    }
}
