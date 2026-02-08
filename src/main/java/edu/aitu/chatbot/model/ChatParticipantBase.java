package model;

import model.interfaces.Loggable;
import model.interfaces.Validatable;

public abstract class ChatParticipantBase extends BaseEntity implements Loggable, Validatable {
    protected String name;

    public ChatParticipantBase(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean isValid() {
        // Используем статический метод интерфейса для проверки имени
        return Validatable.isNotNullOrEmpty(name);
    }
    
    public abstract String getSystemPrompt();
    public abstract String getRole();
    public abstract void displayInfo();
}