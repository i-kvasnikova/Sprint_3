package ru.praktikum_services.qa_scooter.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.models.Courier;
import ru.praktikum_services.qa_scooter.models.CourierCredentials;
import ru.praktikum_services.qa_scooter.paths.ApiPaths;
import ru.praktikum_services.qa_scooter.response_templates.LoginCourierResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    @Step("Создать курьера")
    public Response create(Courier courier) {
        return given()
                .spec(getBaseSpecification())
                .body(courier)
                .when()
                .post(ApiPaths.COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин курьера в системе")
    public Response login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpecification())
                .body(courierCredentials)
                .when()
                .post(ApiPaths.COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step("Удалить курьера")
    public Response delete(int courierId) {
        return given()
                .spec(getBaseSpecification())
                .delete(String.format(ApiPaths.COURIER_DELETE_PATH, courierId))
                .then()
                .extract().response();
    }

    @Step("Отправить запрос на удаление курьера без указания id")
    public Response deleteWithoutId() {
        return given()
                .spec(getBaseSpecification())
                .delete(String.format(ApiPaths.COURIER_DELETE_PATH, ""))
                .then()
                .extract().response();
    }

    @Step("Создать курьера и сохранить идентификатор")
    public Courier createAndSaveId(Courier courier) {
        create(courier);
        LoginCourierResponse mappedLoginResponse = login(courier).getBody().as(LoginCourierResponse.class);
        courier.setId(mappedLoginResponse.id);
        return courier;
    }
}
