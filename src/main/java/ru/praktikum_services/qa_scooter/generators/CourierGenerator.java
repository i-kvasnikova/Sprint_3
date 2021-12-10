package ru.praktikum_services.qa_scooter.generators;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum_services.qa_scooter.models.Courier;

public class CourierGenerator {
    private static int DATA_SIZE = 10;

    public static Courier getRandom() {
        return new Courier(RandomStringUtils.randomAlphabetic(DATA_SIZE),
                RandomStringUtils.randomAlphabetic(DATA_SIZE),
                RandomStringUtils.randomAlphabetic(DATA_SIZE));
    }

    public static Courier getWithLoginOnly() {
        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(DATA_SIZE));
    }

    public static Courier getWithPasswordOnly() {
        return new Courier().setPassword(RandomStringUtils.randomAlphabetic(DATA_SIZE));
    }

    public static Courier getWithoutLoginAndPassword() {
        return new Courier().setFirstName(RandomStringUtils.randomAlphabetic(DATA_SIZE));
    }

    public static Courier getWithLoginAndPassword() {
        return new Courier().setLogin(RandomStringUtils.randomAlphabetic(DATA_SIZE))
                .setPassword(RandomStringUtils.randomAlphabetic(DATA_SIZE));
    }
}
