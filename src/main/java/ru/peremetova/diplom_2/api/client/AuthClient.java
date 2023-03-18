package ru.peremetova.diplom_2.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.peremetova.diplom_2.api.data.request.UserRegisterData;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public static final String API_AUTH_REGISTER = "/api/auth/register";
    public static final String API_AUTH_USER = "/api/auth/user";

    @Step("Создание пользователя. Send POST request to " + API_AUTH_REGISTER)
    public ValidatableResponse createUser(UserRegisterData userRegisterData) {
        return given()
                .header("Content-type", "application/json")
                .body(userRegisterData)
                .when()
                .post(API_AUTH_REGISTER)
                .then();
    }

    @Step("Удаление пользователя. Send DELETE request to " + API_AUTH_USER)
    public ValidatableResponse deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .delete(API_AUTH_USER)
                .then();
    }

}
