package order;

import config.ConfigurationApi;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderClient extends ConfigurationApi {
    public static final String ORDER_PATH = "/api/orders";

    public ValidatableResponse createOrder(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ConfigurationApi.BASE_URL)
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    public ValidatableResponse getOrderListWithAuth (String accessToken) {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .baseUri(ConfigurationApi.BASE_URL)
                .headers(Map.of("Authorization", accessToken))
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }

    public ValidatableResponse getOrderListWithoutAuth () {
        return getRequestSpec()
                .contentType(ContentType.JSON)
                .baseUri(ConfigurationApi.BASE_URL)
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}
