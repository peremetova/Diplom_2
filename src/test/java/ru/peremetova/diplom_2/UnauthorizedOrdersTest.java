package ru.peremetova.diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.diplom_2.api.client.OrdersClient;
import ru.peremetova.diplom_2.api.data.request.IngredientsData;
import ru.peremetova.diplom_2.api.data.response.IngredientData;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;

public class UnauthorizedOrdersTest {
    private final OrdersClient ordersClient = new OrdersClient();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public void setDown() {
    }

    @Test
    @DisplayName("Создание заказа для неавторизованного пользователя с игридиентами")
    @Description("Создание заказа для неавторизованного пользователя с игридиентами")
    public void createUnauthorizedOrderWithIngredientsTest() {
        List<IngredientData> data = ordersClient.getIngredientsData();
        String id = data.get(0).getId();
        System.out.println(id);
        IngredientsData ingredientsData = new IngredientsData(List.of(id));
        ordersClient
                .createOrder(null, ingredientsData)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание заказа для неавторизованного пользователя без игридиентов")
    @Description("Создание заказа для неавторизованного пользователя без игридиентов")
    public void createUnauthorizedOrderWithOutIngredientsTest() {
        IngredientsData ingredientsData = new IngredientsData(List.of());
        ordersClient
                .createOrder(null, ingredientsData)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Получение заказов для неавторизованного пользователя")
    @Description("Получение заказов для неавторизованного пользователя")
    public void getUnauthorizedOrdersTest() {
        ordersClient
                .getOrders(null)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }
}
