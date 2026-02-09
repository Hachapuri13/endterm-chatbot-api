package edu.aitu.chatbot.dto;

public class UserRequest {
    private String name;
    private String persona;
    private boolean premium;

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public String getPersona() {
        return persona;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
