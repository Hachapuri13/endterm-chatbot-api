package edu.aitu.chatbot.model.interfaces;

public interface Validatable {
    boolean isValid();

    default void printValidationStatus() {
        if (isValid()) {
            System.out.println(" [✔] Entity is valid.");
        } else {
            System.out.println(" [X] Entity is INVALID (check fields).");
        }
    }

    static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}