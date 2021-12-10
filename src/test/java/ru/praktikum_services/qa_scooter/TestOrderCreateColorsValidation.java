package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum_services.qa_scooter.clients.OrderClient;
import ru.praktikum_services.qa_scooter.generators.OrderGenerator;
import ru.praktikum_services.qa_scooter.models.Color;
import ru.praktikum_services.qa_scooter.models.Order;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class TestOrderCreateColorsValidation {
    private Order order;
    private OrderClient orderClient;
    private final List<Color> chosenColors;
    private final int expectedStatusCode;

    public TestOrderCreateColorsValidation(List<Color> chosenColors, int expectedStatusCode) {
        this.chosenColors = chosenColors;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        orderClient.cancel(order);
    }

    @Parameterized.Parameters
    public static Object[] getOrderColorData() {
        return new Object[][] {
                { List.of(Color.BLACK), 201 },
                { List.of(Color.GREY), 201 },
                { List.of(Color.BLACK, Color.GREY), 201 },
                { null, 201 }
        };
    }

    @Test
    @DisplayName("Запрос на создание заказа с любым цветом проходит успешно")
    @Description("Передан список цветов (может быть пустым), заказ создан")
    public void canCreateOrderWithOrWithoutColors() {
        order = OrderGenerator.getRandom(chosenColors);
        Response response = orderClient.create(order);

        assertThat(response.statusCode(), equalTo(expectedStatusCode));
        response.then().assertThat().body("track", not(equalTo(null)));

        JsonPath jsonPathEvaluator = response.jsonPath();
        order.setTrack(jsonPathEvaluator.get("track"));
        Object actualColorList = orderClient.getByTrack(order.track).jsonPath().get("order.color");
        Object expectedColors = chosenColors == null ? null : chosenColors.stream().map(Enum::name).collect(Collectors.toList());

        assertThat(actualColorList, equalTo(expectedColors));
    }
}
