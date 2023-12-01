package user;

import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;

public class UserApi {

    //логин под существующим пользователем,
    public String loggedIsSuccessfully(ValidatableResponse loginResponse) {
        String accessToken = loginResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("accessToken");
        return accessToken;
    }

    //логин с неверным логином и паролем.
    public void loggedNotSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("message", is("email or password are incorrect"));
    }

    //создать уникального пользователя;
    public void createdSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", is(true));
    }

    //создать пользователя, который уже зарегистрирован;
    public void createdNotSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("message", is("User already exists"));
    }

    //создать пользователя и не заполнить одно из обязательных полей.
    public void creationWithBadRequest(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("message", is("Email, password and name are required fields"));
    }

    //изменение с авторизацией,
    public void editWithAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK);
    }

    //изменение без авторизации,
    public void editWithoutAuth(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("message", is("You should be authorised"));
    }

    public void edited403(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("message", is("User with such email already exists"));
    }

    public void deleted(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED);
    }
}
