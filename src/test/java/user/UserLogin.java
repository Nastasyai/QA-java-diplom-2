package user;

import helper.UserGeneration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UserLogin {
    private final UserClient userClient = new UserClient();
    private final UserApi userApi = new UserApi();
    private String accessToken;

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loggedIsSuccessfully() {
        var user = UserGeneration.random();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        LoginUser loginUser = LoginUser.from(user);
        ValidatableResponse loginResponse = userClient.login(loginUser);
        accessToken = userApi.loggedIsSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void loggedWithoutLogin() {
        var user = UserGeneration.random();
        Map<String, String> wrongLogData = new HashMap<>();
        wrongLogData.put("email", user.getEmail());
        wrongLogData.put("password", user.getPassword()+"password111");
        System.out.println(wrongLogData);
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse response1 = userClient.loginWithoutBody(wrongLogData);
        userApi.loggedNotSuccessfully(response1);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loggedWithoutPassword() {
        var user = UserGeneration.random();
        Map <String, String> wrongLogData = new HashMap<>();
        wrongLogData.put("email", user.getEmail()+"email111");
        wrongLogData.put("password", user.getPassword());
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse responseNew = userClient.loginWithoutBody(wrongLogData);
        userApi.loggedNotSuccessfully(responseNew);
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void editWithAuth() {
        var user = UserGeneration.random();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        LoginUser loginUser = LoginUser.from(user);
        ValidatableResponse loginResponse = userClient.login(loginUser);
        accessToken = userApi.loggedIsSuccessfully(loginResponse);
        ValidatableResponse editResponse = userClient.edit(accessToken);
        userApi.editWithAuth(editResponse);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void editWithoutAuth() {
        var user = UserGeneration.random();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse editResponse = userClient.editNoAuth();
        userApi.editWithoutAuth(editResponse);
    }
}

