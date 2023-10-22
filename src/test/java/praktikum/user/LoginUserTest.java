package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class LoginUserTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;

    @Test
    @DisplayName("Check successfully post /api/auth/login")
    @Description("Possible logged courier with correct data")
    public void userLoggedPositive() {
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        client.deleteUser(accessToken);
    }

}
