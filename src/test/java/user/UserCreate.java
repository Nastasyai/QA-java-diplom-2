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
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(user.getEmail(), user.getPassword()));
        accessToken = String.valueOf(userApi.loggedIsSuccessfully(loginResponse));
        assertNotNull(accessToken);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createdNotSuccessfully() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.createdSuccessfully(response);
        ValidatableResponse loginResponse1 = userClient.create(user);
        userApi.createdNotSuccessfully(loginResponse1);
    }

    @Test
    @DisplayName("Заполнение не всех обязательных полей")
    public void creationWithBadRequest() {
        var user = UserGeneration.getUser();
        ValidatableResponse response = userClient.create(user);
        userApi.creationWithBadRequest(response);
    }
}
