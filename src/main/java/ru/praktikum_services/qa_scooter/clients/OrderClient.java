package ru.praktikum_services.qa_scooter.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum_services.qa_scooter.models.Order;
import ru.praktikum_services.qa_scooter.models.OrderTrack;
import ru.praktikum_services.qa_scooter.paths.ApiPaths;

import static io.restassured.RestAssured.given;

public class OrderClient extends ScooterRestClient {

    @Step("Создать заказ")
    public Response create(Order order) {
        return given()
                .spec(getBaseSpecification())
                .body(order)
                .when()
                .post(ApiPaths.ORDERS_PATH)
                .then()
                .extract().response();
    }

    @Step("Создать заказ и сохранить трек-номер")
    public Order createAndSaveTrack(Order order) {
        order.setTrack(create(order).jsonPath().get("track"));
        return order;
    }

    @Step("Получить все заказы")
    public Response getAll() {
        return given()
                .spec(getBaseSpecification())
                .when()
                .get(ApiPaths.ORDERS_PATH)
                .then()
                .extract().response();
    }

    @Step("Получить заказ по его номеру")
    public Response getByTrack(int track) {
        return given()
                .spec(getBaseSpecification())
                .when()
                .get(String.format(ApiPaths.ORDER_GET_BY_TRACK_PATH, track))
                .then()
                .extract().response();
    }

    @Step("Отменить заказ по его номеру")
    public Response cancel(OrderTrack track) {
        return given()
                .spec(getBaseSpecification())
                .body(track)
                .when()
                .put(ApiPaths.ORDER_CANCEL_PATH)
                .then()
                .extract().response();
    }
}
