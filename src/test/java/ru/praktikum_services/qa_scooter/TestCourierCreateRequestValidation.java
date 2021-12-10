package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.clients.CourierClient;
import ru.praktikum_services.qa_scooter.generators.CourierGenerator;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.response_templates.ScooterApiGeneralResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class TestCourierCreateRequestValidation {

    private final Courier courier;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public TestCourierCreateRequestValidation(Courier courier, int expectedStatusCode, String expectedMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][] {
                { CourierGenerator.getWithLoginOnly(), 400, "Недостаточно данных для создания учетной записи" },
                { CourierGenerator.getWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи" },
                { CourierGenerator.getWithoutLoginAndPassword(), 400, "Недостаточно данных для создания учетной записи" }
        };
    }

    @Test
    @DisplayName("Запрос на создание курьера без логина или пароля")
    @Description("Переданы не все обязательные параметры, возвращается сообщение об ошибке")
    public void invalidCreateRequestIsNotAllowed() {
        Response response = new CourierClient().create(courier);
        ScooterApiGeneralResponse mappedCreateResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(mappedCreateResponse.code, equalTo(expectedStatusCode));
        assertThat(mappedCreateResponse.message, equalTo(expectedMessage));
    }
}
