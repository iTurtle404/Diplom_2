package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.constant.Message;
import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;

public class UserAssertions {


    @Step("Check of successfully created new User from random")
    public String createSuccessfully(ValidatableResponse response) {
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
    public void deleteSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("success",is(true))
                .body("message", equalTo(Message.SUCCESS_REMOVED));
    }
    @Step("Check of successfully logged User")
    public String logSuccessfully(ValidatableResponse response) {
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

    @Step("Check of unsuccessfully created twins User")
    public void createTwinUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.EXIST_USER))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }
    @Step("Check of unsuccessfully created User with empty field")
    public void createEmptyFieldUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.NOT_ENOUGH_DATA_CREATED))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);

    }

    @Step("Check of unsuccessfully logged User with incorrect data")
    public void logIncorrectDataUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.LOGGED_NOT_EXIST_DATA))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }

    @Step("Check of successfully changed Users data")
    public void changeUserDataSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
    @Step("Check of unsuccessfully changed Users data by unauthorized user")
    public void changeDataUnauthUserUnuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.NEED_AUTH_MSG ))
                .and()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
    @Step("Check of unsuccessfully changed Users data by exist users email")
    public void changeUserExistEmailUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.ERROR_EMAIL_EXIST))
                .and()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN);
    }
}
