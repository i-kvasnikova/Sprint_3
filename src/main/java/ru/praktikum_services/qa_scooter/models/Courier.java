package ru.praktikum_services.qa_scooter.models;

public class Courier extends CourierCredentials {

    public String firstName;

    public Courier setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Courier setLogin(String login) {
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public Courier() {
        super();
    }

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}
