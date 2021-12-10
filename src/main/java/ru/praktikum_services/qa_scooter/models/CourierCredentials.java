package ru.praktikum_services.qa_scooter.models;

public class CourierCredentials {
    public int id;
    public String login;
    public String password;

    public CourierCredentials() {
    }

    public CourierCredentials setId(int id) {
        this.id = id;
        return this;
    }
}