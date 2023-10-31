package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateUserTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        user = genericUserRandom();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
    @Test
    @DisplayName("Check successfully post /api/auth/register")
    @Description("Possible created user with correct data")
    public void createUserPositiveTest(){

        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createSuccessfully(createResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register by twin user")
    @Description("Impossible created twin user")
    public void userTwinsCreatedTest (){

        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createSuccessfully(createResponse);

        ValidatableResponse createTwinRespone  = client.createUser(user);
        check.createTwinUnsuccessfully(createTwinRespone);
    }
}
