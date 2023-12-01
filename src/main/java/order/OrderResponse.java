package order;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.is;

public class OrderResponse {
    //создание заказа
    public void assertCreatedOrder(ValidatableResponse response) {
        int number = response
                .assertThat()
                .statusCode(200)
                .extract()
                .path("order.number");

        assert number != 0;
    }

    //создание заказа без ингредиентов
    public void assertCreatedOrderWithoutIngredients(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(400)
                .body("message", is("Ingredient ids must be provided"));
    }

    //создание заказа с неверным хешем ингредиентов
    public void assertCreatedOrderWrongHash(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    //создание/получение инфо о заказе неавторизованного пользователя
    public void assertCreatedWithoutAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("message", is("You should be authorised"));
    }

    //получение инфо авторизованного пользователя
    public void assertListWitAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }
}
