package config;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ConfigurationApi {

    public final static String BASE_URL = "https://stellarburgers.nomoreparties.site";

    public RequestSpecification getRequestSpec () {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }
}