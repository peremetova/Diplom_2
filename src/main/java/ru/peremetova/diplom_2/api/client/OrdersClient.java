package ru.peremetova.diplom_2.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.peremetova.diplom_2.api.data.request.IngredientsData;
import ru.peremetova.diplom_2.api.data.request.LoginData;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.request.UserData;
import ru.peremetova.diplom_2.api.data.response.IngredientData;
import ru.peremetova.diplom_2.api.data.response.IngredientResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrdersClient {

    public static final String API_INGREDIENTS = "/api/ingredients";
    public static final String API_ORDERS = "/api/orders";

    @Step("Получение ингредиентов. Send GET request to " + API_INGREDIENTS)
    public ValidatableResponse getIngredients() {
        return given()
                .get(API_INGREDIENTS)
                .then();
    }

    @Step("Создание заказа. Send POST request to " + API_ORDERS)
    public ValidatableResponse createOrder(String token, IngredientsData data) {
        RequestSpecification requestSpecification = given()
                .header("Content-type", "application/json");
        if (token != null) {
            requestSpecification
                    .header("Authorization", token);
        }
        return requestSpecification
                .body(data)
                .when()
                .post(API_ORDERS)
                .then();
    }

    @Step("Получение заказов пользователя. Send GET request to " + API_ORDERS)
    public ValidatableResponse getOrders(String token) {
        RequestSpecification requestSpecification = given();
        if (token != null) {
            requestSpecification
                    .header("Authorization", token);
        }
        return requestSpecification
                .get(API_ORDERS)
                .then();
    }

    @Step("Парсим список заказов")
    public List<IngredientData> getIngredientsData() {
        return getIngredients()
                .extract()
                .body()
                .as(IngredientResponse.class)
                .getData();
    }
}
