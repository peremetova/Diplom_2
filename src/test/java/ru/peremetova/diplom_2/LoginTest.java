package ru.peremetova.diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.diplom_2.api.client.AuthClient;
import ru.peremetova.diplom_2.api.data.request.LoginData;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.response.RegisterResponse;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginTest {
    private final AuthClient authClient = new AuthClient();
    private final RegisterData registerData = new RegisterData(
            TestConfig.EMAIL,
            TestConfig.NAME,
            TestConfig.PASSWORD
    );
    private final LoginData loginData = new LoginData(
            TestConfig.EMAIL,
            TestConfig.PASSWORD
    );
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
        RegisterResponse response = authClient
                .register(registerData)
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
    @DisplayName("Вход пользователя")
    @Description("Проверка входа пользователя.")
    public void loginTest() {
        authClient
                .login(loginData)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход пользователя с неверным паролем")
    @Description("Проверка входа пользователя с неверным паролем.")
    public void loginWrongPasswordTest() {
        loginData.setPassword(TestConfig.WRONG_PASSWORD);
        authClient
                .login(loginData)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Вход пользователя с неверным email")
    @Description("Проверка входа пользователя с email.")
    public void loginWrongEmailTest() {
        loginData.setEmail(TestConfig.WRONG_EMAIL);
        authClient
                .login(loginData)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

}
