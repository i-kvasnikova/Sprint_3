package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.clients.OrderClient;
import ru.praktikum_services.qa_scooter.generators.OrderGenerator;
import ru.praktikum_services.qa_scooter.models.Order;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestOrderGetList {
    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getRandom();
        orderClient.createAndSaveTrack(order);
    }

    @After
    public void tearDown() {
        orderClient.cancel(order);
    }

    @Test
    @DisplayName("Успешное получение списка всех заказов")
    @Description("Получение списка заказов без дополнительных параметров")
    public void getAllOrdersReturnsList() {
        Response response = orderClient.getAll();
        assertThat(response.statusCode(), equalTo(200));
        response.then().assertThat().body("orders", not(equalTo(null)));
        List<Object> ordersList = response.jsonPath().getList("orders");
        assertTrue("Созданные заказы не отображаются", ordersList.size() > 0);
    }
}
