package praktikum.user;

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
    public void createUserPositiveTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);

    }

}
