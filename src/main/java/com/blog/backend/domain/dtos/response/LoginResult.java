package com.blog.backend.domain.dtos.response;

import org.springframework.http.ResponseCookie;

public class LoginResult {
    private LoginResponse loginResponse;
    private ResponseCookie responseCookie;

    public LoginResult(LoginResponse loginResponse, ResponseCookie responseCookie) {
        this.loginResponse = loginResponse;
        this.responseCookie = responseCookie;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public ResponseCookie getResponseCookie() {
        return responseCookie;
    }

    public void setResponseCookie(ResponseCookie responseCookie) {
        this.responseCookie = responseCookie;
    }
}
