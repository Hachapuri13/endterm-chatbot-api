package edu.aitu.chatbot.patterns.singleton;

import java.time.Instant;

public final class AppLogger {
    private static volatile AppLogger instance;

    private AppLogger() {
    }

    public static AppLogger getInstance() {
        AppLogger local = instance;
        if (local == null) {
            synchronized (AppLogger.class) {
                local = instance;
                if (local == null) {
                    local = new AppLogger();
                    instance = local;
                }
            }
        }
        return local;
    }

    public void info(String message) {
        System.out.println("[INFO] " + Instant.now() + " " + message);
    }

    public void warn(String message) {
        System.out.println("[WARN] " + Instant.now() + " " + message);
    }

    public void error(String message) {
        System.err.println("[ERROR] " + Instant.now() + " " + message);
    }
}
