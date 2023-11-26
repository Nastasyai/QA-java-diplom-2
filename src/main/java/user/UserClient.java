package user;

import config.ConfigurationApi;
import io.restassured.response.ValidatableResponse;


public class UserClient extends ConfigurationApi {
    public static final String REG_PATH = "/api/auth/register";
    public static final String LOG_PATH = "/api/auth/login";
    public static final String EDIT_PATH = "/api/auth/user";

    public ValidatableResponse create(CreateUser userRequest) {
        return getRequestSpec()
                .body(userRequest)
                .when()
                .post(REG_PATH)
                .then().log().all();
    }

    public ValidatableResponse login (LoginUser userRequest) {
        return getRequestSpec()
                .body(userRequest)
                .when()
                .post(LOG_PATH)
                .then().log().all();
    }

    public ValidatableResponse edit (CreateUser createUser, String accessToken) {
        return getRequestSpec()
                .header("Authorization", accessToken)
                .body(createUser)
                .when()
                .post(EDIT_PATH)
                .then().log().all();
    }
    }



