package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static praktikum.user.UserGenerator.*;

public class LoginUserTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private User user;
    private Credentials creds;
    private String accessToken;

    @Before
    public void setUp() {
        user = genericUserRandom();
        creds = Credentials.from(user);
    }
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
        client.createUser(user);

        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with non-existen data")
    @Description("Impossible  logged user with non-existen data")
    public void userLoggedNotExistDataTest(){

        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with incorrect email")
    @Description("Impossible  logged user with incorrect email")
    public void userLoggedIncorrectEmailTest(){

        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);

        user.setEmail(generatorRandomString()+DOMAIN);
        creds = Credentials.from(user);

        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/login with incorrect password")
    @Description("Impossible  logged user with incorrect password")
    public void userLoggedIncorrectPassTest(){

        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);

        user.setPassword(generatorRandomString());
        creds = Credentials.from(user);

        ValidatableResponse loginResponse = client.loginUser(creds);
        check.loggedIncorrectDataUnsuccessfully(loginResponse);
    }
}
