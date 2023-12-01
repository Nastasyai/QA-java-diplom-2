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

public class listOrders {
    private final OrderResponse orderResponse = new OrderResponse();
    Order order;
    private final OrderClient orderClient = new OrderClient();
    private final UserClient userClient = new UserClient();
    private final UserApi userApi = new UserApi();
    private String accessToken;

    @Test
    @DisplayName("Получение заказов авторизованного пользователя:")
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
        ValidatableResponse responseList = orderClient.getOrderListWithAuth(accessToken);
        orderResponse.assertListWitAuth(responseList);
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя:")
    public void getOrderListWithoutAuth() {
        ValidatableResponse response = orderClient.getOrderListWithoutAuth();
        orderResponse.assertCreatedWithoutAuth(response);
    }
}