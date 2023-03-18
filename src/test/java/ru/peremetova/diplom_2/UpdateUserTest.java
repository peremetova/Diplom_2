package ru.peremetova.diplom_2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.peremetova.diplom_2.api.client.AuthClient;
import ru.peremetova.diplom_2.api.data.request.LoginData;
import ru.peremetova.diplom_2.api.data.request.RegisterData;
import ru.peremetova.diplom_2.api.data.request.UserData;
import ru.peremetova.diplom_2.api.data.response.RegisterResponse;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Parameterized.class)
public class UpdateUserTest {
    private final AuthClient authClient = new AuthClient();
    private final RegisterData registerData = new RegisterData(
            TestConfig.EMAIL,
            TestConfig.NAME,
            TestConfig.PASSWORD
    );
    private final UserData userData;
    private String token;

    public UpdateUserTest(String email, String name){
        userData = new UserData(email, name);
    }

    @Parameterized.Parameters(name = "Email: {0}; Имя: {1}")
    public static Object[][] getUserData() {
        return new Object[][]{
                {TestConfig.NEW_EMAIL, TestConfig.NEW_NAME},
                {TestConfig.NEW_EMAIL, null},
                {null, TestConfig.NEW_NAME},
        };
    }

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
    @DisplayName("Обновление данных авторизованного пользователя")
    @Description("Проверка обновления данных авторизованного пользователя.")
    public void authorizedUserDataUpdateTest() {
       authClient
                .updateUserData(token, userData)
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Обновление данных неавторизованного пользователя")
    @Description("Проверка обновления данных неавторизованного пользователя.")
    public void unauthorizedUserDataUpdateTest() {
        authClient
                .updateUserData(null, userData)
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false));
    }

}
