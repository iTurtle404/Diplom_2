package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static praktikum.user.UserGenerator.*;

public class LoginUserTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;
    @After
    public void tearDown() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Check successfully post /api/auth/login")
    @Description("Possible logged user with correct data")
    public void userLoggedPositiveTest() {
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with non-existen data")
    @Description("Impossible  logged user with non-existen data")
    public void userLoggedNotExistDataTest(){
        var user = genericUserRandom();
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with incorrect email")
    @Description("Impossible  logged user with incorrect email")
    public void userLoggedIncorrectEmailTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        user.setEmail(generatorRandomString()+DOMAIN);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
        accessToken = check.createdSuccessfully(createResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with incorrect password")
    @Description("Impossible  logged user with incorrect password")
    public void userLoggedIncorrectPassTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        user.setPassword(generatorRandomString());
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
        accessToken = check.createdSuccessfully(createResponse);
    }
}
