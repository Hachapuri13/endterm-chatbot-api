package edu.aitu.chatbot.patterns.factory;

public record ParticipantSpec(
        int id,
        String name,
        String greeting,
        String definition,
        Integer tokenLimit,
        String persona,
        Boolean isPremium
) {
}
