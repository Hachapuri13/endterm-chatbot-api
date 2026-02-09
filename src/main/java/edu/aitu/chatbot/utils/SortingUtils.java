package edu.aitu.chatbot.utils;
import edu.aitu.chatbot.model.Bot;
import java.util.Comparator;
import java.util.List;

public class SortingUtils {

    public static void sortBotsByTokenLimit(List<Bot> bots) {
        bots.sort((b1, b2) -> Integer.compare(b1.getTokenLimit(), b2.getTokenLimit()));
        System.out.println(">> Sorted bots by Token Limit (Ascending).");
    }

    public static void sortBotsByName(List<Bot> bots) {
        bots.sort((b1, b2) -> b1.getName().compareToIgnoreCase(b2.getName()));
        System.out.println(">> Sorted bots by Name.");
    }
}