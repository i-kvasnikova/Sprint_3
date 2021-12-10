package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.clients.CourierClient;
import ru.praktikum_services.qa_scooter.generators.CourierGenerator;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;
import ru.praktikum_services.qa_scooter.response_templates.LoginCourierResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCourierLogin {
    private CourierClient courierClient;
    private CourierCredentials courierCredentials;

    @Before
    public void setUp() {
        courierCredentials = CourierGenerator.getWithLoginAndPassword();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierCredentials.id);
    }

    @Test
    @DisplayName("Успешный логин курьера со всеми параметрами")
    @Description("Переданы логин и пароль существующего курьера, успешный запрос возвращает ok: true")
    public void canLoginWithExistingCourier() {
        courierClient.create((Courier) courierCredentials);

        LoginCourierResponse mappedLoginResponse = courierClient.login(courierCredentials).getBody().as(LoginCourierResponse.class);
        assertThat(mappedLoginResponse.message, equalTo(null));
        assertThat(mappedLoginResponse.id, not(equalTo(null)));
        courierCredentials.setId(mappedLoginResponse.id);
    }

    @Test
    @DisplayName("Неуспешный логин курьера c несуществующей парой логин-пароль")
    @Description("Переданы логин и пароль существующего курьера, успешный запрос возвращает ok: true")
    public void canNotLoginWithNonExistingCourier() {
        courierCredentials = courierClient.createAndSaveId((Courier) courierCredentials);
        courierClient.delete(courierCredentials.id);

        LoginCourierResponse mappedLoginResponse = courierClient.login(courierCredentials).getBody().as(LoginCourierResponse.class);
        assertThat(mappedLoginResponse.code, equalTo(404));
        assertThat(mappedLoginResponse.message, equalTo("Учетная запись не найдена"));
    }
}
