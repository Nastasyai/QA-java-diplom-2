package user;

import helper.UserGeneration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class UserLogin {
    private final UserClient userClient = new UserClient();
    private final UserApi userApi = new UserApi();
    private String accessToken;

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loggedIsSuccessfully() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(user.getEmail(), user.getPassword()));
        accessToken = String.valueOf(userApi.loggedIsSuccessfully(loginResponse));
        //accessToken = userApi.loggedIsSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Логин с неверным логином")
    public void loggedWithoutLogin() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(null, user.getPassword()));
        userApi.loggedNotSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loggedWithoutPassword() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(user.getEmail(), null));
        userApi.loggedNotSuccessfully(loginResponse);
    }


//    @Test
//    @DisplayName("Некорректный пароль курьера")
//    public void courierLoginPassword404() {
//        var courier = CourierGenerator.random();
//        ValidatableResponse response = client.create(courier);
//        check.createdSuccessfully(response);
//        LoginCourier loginCourier = LoginCourier.from(courier);
//        ValidatableResponse loginResponseSuccessfully = client.login(loginCourier);
//        courierId = check.loggedIsSuccessfully(loginResponseSuccessfully);
//        Map<String, String> logData = new HashMap<>();
//        logData.put("login", courier.getLogin());
//        logData.put("password", courier.getPassword() + "0000");
//        ValidatableResponse loginResponse = client.loginNotAllBody(logData);
//        check.loggedNotSuccessfully404(loginResponse);
//    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    public void editWithAuth() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(user.getEmail(), user.getPassword()));
        accessToken = String.valueOf(userApi.loggedIsSuccessfully(loginResponse));
        //accessToken = userApi.loggedIsSuccessfully(loginResponse);
        ValidatableResponse editResponse = userClient.edit(user, accessToken);
        userApi.editWithAuth(editResponse);
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    public void editWithoutAuth() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse editResponse = userClient.edit(null, null);
        userApi.editWithoutAuth(editResponse);
    }
}

