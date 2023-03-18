package ru.peremetova.diplom_2.api.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.peremetova.diplom_2.api.data.request.LoginData;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.request.UserData;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public static final String API_AUTH_REGISTER = "/api/auth/register";
    public static final String API_AUTH_USER = "/api/auth/user";
    public static final String API_AUTH_LOGIN = "/api/auth/login";

    @Step("Создание пользователя. Send POST request to " + API_AUTH_REGISTER)
    public ValidatableResponse register(RegisterData registerData) {
        return given()
                .header("Content-type", "application/json")
                .body(registerData)
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

    @Step("Вход пользователя. Send POST request to " + API_AUTH_LOGIN)
    public ValidatableResponse login(LoginData loginData) {
        return given()
                .header("Content-type", "application/json")
                .body(loginData)
                .when()
                .post(API_AUTH_LOGIN)
                .then();
    }

    @Step("Обновление данных пользователя. Send PATCH request to " + API_AUTH_USER)
    public ValidatableResponse updateUserData(String token, UserData userData) {
        RequestSpecification requestSpecification = given()
                .header("Content-type", "application/json");
        if (token != null) {
            requestSpecification
                    .header("Authorization", token);
        }
        return requestSpecification
                .body(userData)
                .when()
                .patch(API_AUTH_USER)
                .then();
    }

}
