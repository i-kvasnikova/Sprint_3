package ru.praktikum_services.qa_scooter.models;

import java.util.List;

public class Order extends OrderTrack {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<Color> color;

    public Order setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Order setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Order setMetroStation(String metroStation) {
        this.metroStation = metroStation;
        return this;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Order setRentTime(int rentTime) {
        this.rentTime = rentTime;
        return this;
    }

    public Order setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public Order setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Order setColor(List<Color> color) {
        this.color = color;
        return this;
    }

    public Order() {
    }
}
