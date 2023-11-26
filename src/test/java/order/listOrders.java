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
        var user = UserGeneration.getUser();
        ValidatableResponse createResponse = userClient.create(user);
        userApi.createdSuccessfully(createResponse);
        ValidatableResponse loginResponse = userClient.login(new LoginUser(user.getEmail(), user.getPassword()));
        accessToken = String.valueOf(userApi.loggedIsSuccessfully(loginResponse));
        order = new Order(List.of(Ingredient1, Ingredient2, Ingredient3, Ingredient4));
        ValidatableResponse createOrderResponse = orderClient.createOrder(order);
        orderResponse.assertCreatedOrder(createOrderResponse);
        ValidatableResponse listResponse = orderClient.getOrderListWithAuth(accessToken);
        orderResponse.assertListWitAuth(listResponse);
    }

    @Test
    @DisplayName("Получение заказов неавторизованного пользователя:")
    public void getOrderListWithoutAuth() {
        ValidatableResponse listResponse = orderClient.getOrderListWithoutAuth();
        orderResponse.assertCreatedWithoutAuth(listResponse);
    }
}