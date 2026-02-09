package edu.aitu.chatbot.patterns.factory;

import edu.aitu.chatbot.exception.InvalidInputException;
import edu.aitu.chatbot.model.Bot;
import edu.aitu.chatbot.model.ChatParticipantBase;
import edu.aitu.chatbot.model.User;
import edu.aitu.chatbot.patterns.builder.BotBuilder;

public final class ChatParticipantFactory {
    private ChatParticipantFactory() {
    }

    public static ChatParticipantBase createParticipant(ParticipantType type, ParticipantSpec spec) {
        if (type == null) {
            throw new InvalidInputException("Participant type is required.");
        }
        if (spec == null) {
            throw new InvalidInputException("Participant spec is required.");
        }
        return switch (type) {
            case BOT -> createBot(spec);
            case USER -> createUser(spec);
        };
    }

    private static Bot createBot(ParticipantSpec spec) {
        return new BotBuilder()
                .id(spec.id())
                .name(spec.name())
                .greeting(spec.greeting())
                .definition(spec.definition())
                .tokenLimit(spec.tokenLimit() == null ? 0 : spec.tokenLimit())
                .build();
    }

    private static User createUser(ParticipantSpec spec) {
        String name = spec.name();
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("User name cannot be empty.");
        }
        return new User(spec.id(), name, spec.persona(), spec.isPremium() != null && spec.isPremium());
    }
}
