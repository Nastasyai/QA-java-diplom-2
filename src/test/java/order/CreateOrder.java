package order;

import helper.UserGeneration;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import user.LoginUser;
import user.UserApi;
import user.UserClient;

import java.util.List;

import static helper.IngredientGeneration.*;

public class CreateOrder {
    private final OrderResponse orderResponse = new OrderResponse();
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private final UserApi userApi = new UserApi();
    private String accessToken;

    @Test
    @DisplayName("Создание заказа с авторизацией и ингредиентами")
    public void getOrderListWithAuth() {
        var user = UserGeneration.random();
        ValidatableResponse createResponse = userClient.create(user);
        userApi.createdSuccessfully(createResponse);
        LoginUser loginUser = LoginUser.from(user);
        ValidatableResponse loginResponse = userClient.login(loginUser);
        accessToken = userApi.loggedIsSuccessfully(loginResponse);
        var order = new Order(List.of(Ingredient1, Ingredient2, Ingredient3));
        ValidatableResponse response = orderClient.createOrder(order);
        orderResponse.assertCreatedOrder(response);
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами, но без без авторизации")
    public void getOrderListWithoutAuth() {
        var order = new Order(List.of(Ingredient1, Ingredient2, Ingredient3));
        ValidatableResponse response = orderClient.createOrder(order);
        orderResponse.assertCreatedOrder(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void assertCreatedOrderWithoutIngredients() {
        var order = new Order(null);
        ValidatableResponse response = orderClient.createOrder(order);
        orderResponse.assertCreatedOrderWithoutIngredients(response);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void assertCreatedOrderWrongHash() {
        var order = new Order(List.of(Ingredient1, Ingredient2, Ingredient3, Unknown_Ingredient));
        ValidatableResponse response = orderClient.createOrder(order);
        orderResponse.assertCreatedOrderWrongHash(response);
    }
}
