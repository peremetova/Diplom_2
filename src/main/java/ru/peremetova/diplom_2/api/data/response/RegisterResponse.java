package ru.peremetova.diplom_2.api.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.peremetova.diplom_2.api.data.request.UserData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private UserData user;

    public RegisterResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
}
