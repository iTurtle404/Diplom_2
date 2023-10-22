package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

import static praktikum.constant.URLForEndpoint.*;

public class UserClient extends Client {

    @Step ("Created new User from random")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_REGISTER)
                .then().log().all();
    }
@Step ("Deleted User with accessToken")
    public ValidatableResponse deleteUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then().log().all();
    }

@Step("Logged User")
    public ValidatableResponse loginUser(Credentials cred) {
        return spec()
                .body(cred)
                .when()
                .post(USER_LOGIN_PATH)
                .then().log().all();
    }
}
