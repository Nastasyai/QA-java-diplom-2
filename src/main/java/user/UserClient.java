package user;

import config.ConfigurationApi;
import io.restassured.response.ValidatableResponse;

import java.util.Map;


public class UserClient extends ConfigurationApi {
    public static final String REG_PATH = "/api/auth/register";
    public static final String LOG_PATH = "/api/auth/login";
    public static final String EDIT_PATH = "/api/auth/user";

    public ValidatableResponse create(User userRequest) {
        return getRequestSpec()
                .body(userRequest)
                .when()
                .post(REG_PATH)
                .then().log().all();
    }

    public ValidatableResponse login(LoginUser userRequest) {
        return getRequestSpec()
                .body(userRequest)
                .when()
                .post(LOG_PATH)
                .then().log().all();
    }

    public ValidatableResponse loginWithoutBody(Map userRequest) {
        return getRequestSpec()
                .body(userRequest)
                .when()
                .post(LOG_PATH)
                .then().log().all();
    }

    public ValidatableResponse edit(String accessToken) {
        return getRequestSpec()
                .headers(Map.of("Authorization", accessToken))
                .body(Map.of("name", "editedName"))
                .when()
                .patch(EDIT_PATH)
                .then().log().all();
    }

    public ValidatableResponse editNoAuth() {
        return getRequestSpec()
                .body(Map.of("name", "editedName"))
                .when()
                .patch(EDIT_PATH)
                .then().log().all();
    }

    public ValidatableResponse delete(String accessToken) {
        return getRequestSpec()
                .headers(Map.of("Authorization", accessToken))
                .when()
                .delete(EDIT_PATH)
                .then().log().all();
    }
}



