package ru.praktikum_services.qa_scooter.generators;

import com.github.javafaker.Faker;
import ru.praktikum_services.qa_scooter.models.Color;
import ru.praktikum_services.qa_scooter.models.Order;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {
    private static Faker faker = new Faker(new Locale("ru"));

    public static Order getRandom() {
        return new Order().setFirstName(faker.name().firstName())
                .setLastName(faker.name().lastName())
                .setAddress(faker.address().streetAddress())
                .setMetroStation(faker.funnyName().name())
                .setPhone(faker.phoneNumber().phoneNumber())
                .setRentTime(faker.number().numberBetween(1, 7))
                .setDeliveryDate(faker.date().future(14, 1, TimeUnit.DAYS).toString())
                .setComment(faker.shakespeare().hamletQuote());
    }

    public static Order getRandom(List<Color> chosenColors) {
        return getRandom().setColor(chosenColors);
    }
}
