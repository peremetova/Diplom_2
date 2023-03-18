package ru.peremetova.diplom_2.api.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {
    private boolean success;
    private String accessToken;
    private String refreshToken;

    public RegisterResponse() {
    }

    public RegisterResponse(boolean success, String accessToken, String refreshToken) {
        this.success = success;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
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
}
