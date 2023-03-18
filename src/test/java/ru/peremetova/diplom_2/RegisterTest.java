package ru.peremetova.diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.peremetova.diplom_2.api.client.AuthClient;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.response.RegisterResponse;

import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterTest {
    private final AuthClient authClient = new AuthClient();
    private final RegisterData registerData = new RegisterData(
            TestConfig.EMAIL,
            TestConfig.NAME,
            TestConfig.PASSWORD
    );
    private String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site/";
    }

    @After
    public void setDown() {
        if (token != null) {
            authClient.deleteUser(token);
        }
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверка cоздания пользователя.")
    public void createUserTest() {
        RegisterResponse response = authClient
                .register(registerData)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .extract()
                .body()
                .as(RegisterResponse.class);

        token = response.getAccessToken();
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка пользователя, который уже зарегистрирован.")
    public void createDoubleUserTest() {
        RegisterResponse response = authClient
                .register(registerData)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true))
                .extract()
                .body()
                .as(RegisterResponse.class);
        authClient
                .register(registerData)
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false));

        token = response.getAccessToken();
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверка создания пользователя без email.")
    public void createNoEmailUserTest() {
        registerData.setEmail(null);
        authClient
                .register(registerData)
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    @Description("Проверка создания пользователя без имени.")
    public void createNoNameUserTest() {
        registerData.setName(null);
        authClient
                .register(registerData)
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Проверка создания пользователя без пароля.")
    public void createNoPasswordUserTest() {
        registerData.setPassword(null);
        authClient
                .register(registerData)
                .statusCode(403)
                .assertThat()
                .body("success", equalTo(false));
    }
}
