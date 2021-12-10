package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.clients.CourierClient;
import ru.praktikum_services.qa_scooter.generators.CourierGenerator;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;
import ru.praktikum_services.qa_scooter.response_templates.LoginCourierResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class TestCourierLoginRequestValidation {
    private final CourierCredentials courierCredentials;
    private final int expectedStatusCode;
    private final String expectedMessage;

    public TestCourierLoginRequestValidation(CourierCredentials courierCredentials, int expectedStatusCode, String expectedMessage) {
        this.courierCredentials = courierCredentials;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][] {
                { CourierGenerator.getWithLoginOnly(), 400, "Недостаточно данных для входа" },
                { CourierGenerator.getWithPasswordOnly(), 400, "Недостаточно данных для входа" },
                { CourierGenerator.getWithoutLoginAndPassword(), 400, "Недостаточно данных для входа" }
        };
    }

    @Test
    @DisplayName("Запрос без логина или пароля или для несуществующего курьера")
    @Description("Переданы не все параметры или параметры несуществующего курьера, возвращается сообщение об ошибке")
    public void invalidLoginRequestIsNotAllowed() {
        Response response = new CourierClient().login(courierCredentials);
        assertThat(response.statusCode(), equalTo(expectedStatusCode));

        LoginCourierResponse mappedCreateResponse = response.getBody().as(LoginCourierResponse.class);
        assertThat(mappedCreateResponse.message, equalTo(expectedMessage));
    }
}
