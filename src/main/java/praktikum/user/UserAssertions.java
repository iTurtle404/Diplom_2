package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.constant.Message;

import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static praktikum.constant.Message.ERROR_UPDATE_DATA;

public class UserAssertions {


    @Step("Check of successfully created new User from random")
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
    @Step("Check of successfully deleted User")
    public void deletedSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("success",is(true))
                .body("message", equalTo(Message.SUCCESS_REMOVED));
    }
    @Step("Check of successfully logged User")
    public String loggedSuccessfully(ValidatableResponse response) {
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

    public void createdTwinUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.EXIST_USER))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }

    public void createdEmptyFieldUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.NOT_ENOUGH_DATA_CREATED))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);

    }

    public void loggedIncorrectDataUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.LOGGED_NOT_EXIST_DATA))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    public void changedUserDataSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
}
