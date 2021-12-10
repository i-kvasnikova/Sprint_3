package ru.praktikum_services.qa_scooter;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.clients.CourierClient;
import ru.praktikum_services.qa_scooter.generators.CourierGenerator;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.response_templates.LoginCourierResponse;
import ru.praktikum_services.qa_scooter.response_templates.ScooterApiGeneralResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestCourierCreateAndDelete {
    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courier.id);
    }

    @Test
    @DisplayName("Успешное создание учетной записи курьера со всеми параметрами")
    @Description ("Переданы все параметры курьера, успешный запрос возвращает ok: true")
    public void canCreateValidCourier() {
        Response response = courierClient.create(courier);
        ScooterApiGeneralResponse mappedCreateResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(mappedCreateResponse.ok, equalTo(true));

        LoginCourierResponse mappedLoginResponse = courierClient.login(courier).getBody().as(LoginCourierResponse.class);
        assertThat(mappedLoginResponse.message, equalTo(null));
        assertThat(mappedLoginResponse.id, not(equalTo(null)));
        courier.setId(mappedLoginResponse.id);
    }

    @Test
    @DisplayName("Успешное создание учетной записи курьера только с обязательными параметрами")
    @Description ("Переданы только обязательные параметры курьера, успешный запрос возвращает ok: true")
    public void canCreateValidCourierWithRequiredParamsOnly() {
        courier = CourierGenerator.getWithLoginAndPassword();
        Response response = courierClient.create(courier);
        ScooterApiGeneralResponse mappedCreateResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(mappedCreateResponse.ok, equalTo(true));

        LoginCourierResponse mappedLoginResponse = courierClient.login(courier).getBody().as(LoginCourierResponse.class);
        assertThat(mappedLoginResponse.message, equalTo(null));
        assertThat(mappedLoginResponse.id, not(equalTo(null)));
        courier.setId(mappedLoginResponse.id);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description ("Дважды отправлен запрос на создание курьера с одинаковыми параметрами")
    public void cannotCreateDuplicatedCourier() {
        courierClient.createAndSaveId(courier);
        Response response = courierClient.create(courier);
        ScooterApiGeneralResponse mappedCreateResponse = response.getBody().as(ScooterApiGeneralResponse.class);

        assertThat(mappedCreateResponse.code, equalTo(409));
        assertThat(mappedCreateResponse.message, equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Успешное удаление существующего курьера")
    @Description("Курьер создан, id передан, успешный запрос возвращает ok: true")
    public void canDeleteExistingCourier() {
        courierClient.createAndSaveId(courier);
        Response response = courierClient.delete(courier.id);
        ScooterApiGeneralResponse mappedDeleteResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(mappedDeleteResponse.ok, equalTo(true));
    }

    @Test
    @DisplayName("Нельзя удалить несуществующего курьера")
    @Description("Передан id курьера, которого нет в системе")
    public void canNotDeleteNotExistingCourier() {
        courierClient.createAndSaveId(courier);
        courierClient.delete(courier.id);

        Response response = courierClient.delete(courier.id);
        ScooterApiGeneralResponse mappedDeleteResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(response.statusCode(), equalTo(404));
        assertThat(mappedDeleteResponse.message, equalTo("Курьера с таким id нет"));
    }

    @Test
    @DisplayName("Нельзя удалить курьера без передачи id")
    @Description("Не передан id курьера")
    public void canNotDeleteCourierWithoutPassingId() {
        Response response = courierClient.deleteWithoutId();
        ScooterApiGeneralResponse mappedDeleteResponse = response.getBody().as(ScooterApiGeneralResponse.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(mappedDeleteResponse.code, equalTo(400));
        assertThat(mappedDeleteResponse.message, equalTo("Недостаточно данных для удаления курьера"));
    }
}