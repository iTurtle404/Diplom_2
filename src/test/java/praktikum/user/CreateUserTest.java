package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateUserTest {
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
    @DisplayName("Check successfully post /api/auth/register")
    @Description("Possible created user with correct data")
    public void createUserPositiveTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);

    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register by twin user")
    @Description("Impossible created twin user")
    public void userTwinsCreatedTest (){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);
        ValidatableResponse createTwinRespone  = client.createUser(user);
        check.createdTwinUnsuccessfully(createTwinRespone);

    }

}
