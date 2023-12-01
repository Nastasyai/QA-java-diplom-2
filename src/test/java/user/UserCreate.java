package user;

import helper.UserGeneration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UserCreate {
    private final UserClient userClient = new UserClient();
    private final UserApi userApi = new UserApi();
    private String accessToken;

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createdSuccessfully() {
        var user = UserGeneration.random();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        LoginUser loginUser = LoginUser.from(user);
        ValidatableResponse loginResponse = userClient.login(loginUser);
        accessToken = userApi.loggedIsSuccessfully(loginResponse);
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createdNotSuccessfully() {
        var user = UserGeneration.random();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse response1 = userClient.create(user);
        userApi.createdNotSuccessfully(response1);
    }

    @Test
    @DisplayName("Заполнение не всех обязательных полей")
    public void creationWithBadRequest() {
        var user = UserGeneration.generic();
        ValidatableResponse response = userClient.create(user);
        userApi.creationWithBadRequest(response);
    }
}
