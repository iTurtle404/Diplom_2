package praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import static praktikum.constant.URLForEndpoint.*;

public class UserClient extends Client {

@Step ("Created new User from random")
    public ValidatableResponse createUser(User user) {
        return spec()
                .body(user)
                .when()
                .post(USER_REGISTER_PATH)
                .then().log().all();
    }
@Step ("Created another new User from random")
    public ValidatableResponse createAnotherUser(User anotherUser) {
        return spec()
                .body(anotherUser)
                .when()
                .post(USER_REGISTER_PATH)
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
@Step("Changed authorized Users Data")
    public ValidatableResponse changeUserData(User user, String accessToken) {
        return spec()
                .body(user)
                .header("Authorization", accessToken)
                .when()
                .patch(USER_PATH)
                .then().log().all();
    }
@Step("Changed Unauthorized Users Data")
    public ValidatableResponse changeDataUnauthUser(User user) {
        return spec()
                .body(user)
                .when()
                .patch(USER_PATH)
                .then().log().all();
    }
}
