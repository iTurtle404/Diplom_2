package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.aspectj.lang.annotation.After;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserAssertions {

    public String createdSuccessfully(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success",is(true))
                .body("user",notNullValue())
                .body("accessToken",notNullValue())
                .body("refreshToken",notNullValue())
                .extract().path("accessToken");
        return accessToken;
    }
    @Step("Check of successfully deleted Courier")
    public void deletedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("success",is(true));
    }
}
