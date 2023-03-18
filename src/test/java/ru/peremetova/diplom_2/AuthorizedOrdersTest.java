package ru.peremetova.diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.diplom_2.api.client.AuthClient;
import ru.peremetova.diplom_2.api.client.OrdersClient;
import ru.peremetova.diplom_2.api.data.request.IngredientsData;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.response.IngredientData;
import ru.peremetova.diplom_2.api.data.response.OrderResponse;
import ru.peremetova.diplom_2.api.data.response.RegisterResponse;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;

public class AuthorizedOrdersTest {
    private final AuthClient authClient = new AuthClient();
    private final RegisterData registerData = new RegisterData(
            TestConfig.EMAIL,
            TestConfig.NAME,
            TestConfig.PASSWORD
    );
    private final OrdersClient ordersClient = new OrdersClient();
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        RegisterResponse response = authClient
                .register(registerData)
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(RegisterResponse.class);
        token = response.getAccessToken();
    }

    @After
    public void setDown() {
        if (token != null) {
            authClient.deleteUser(token);
        }
    }

    @Test
    @DisplayName(value = "Создание заказа для авторизованного пользователя с игридиентами")
    @Description(value = "Создание заказа для авторизованного пользователя с игридиентами")
    public void createAuthorizedOrderWithIngredientsTest() {
        List<IngredientData> data = ordersClient.getIngredientsData();
        String id = data.get(0).getId();
        IngredientsData ingredientsData = new IngredientsData(List.of(id));
        ordersClient
                .createOrder(token, ingredientsData)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName(value = "Создание заказа для авторизованного пользователя без игридиентов")
    @Description(value = "Создание заказа для авторизованного пользователя без игридиентов")
    public void createAuthorizedOrderWithOutIngredientsTest() {
        IngredientsData ingredientsData = new IngredientsData(List.of());
        ordersClient
                .createOrder(token, ingredientsData)
                .statusCode(400)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName(value = "Создание заказа для авторизованного пользователя с неверным кэшем ингридиета")
    @Description(value = "Создание заказа для авторизованного пользователя с неверным кэшем ингридиета")
    public void createAuthorizedOrderWithWrongIngredientsTest() {
        IngredientsData ingredientsData = new IngredientsData(List.of("wronghash"));
        ordersClient
                .createOrder(token, ingredientsData)
                .statusCode(500);
    }

    @Test
    @DisplayName(value = "Получение заказов для авторизованного пользователя")
    @Description(value = "Получение заказов для авторизованного пользователя")
    public void getAuthorizedOrdersTest() {
        List<IngredientData> data = ordersClient.getIngredientsData();
        String id = data.get(0).getId();
        IngredientsData ingredientsData = new IngredientsData(List.of(id));
        int number = ordersClient
                .createOrder(token, ingredientsData)
                .extract()
                .body()
                .as(OrderResponse.class)
                .getOrder()
                .getNumber();
        ordersClient
                .getOrders(token)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .body("orders.number", hasItem(number));
    }
}
