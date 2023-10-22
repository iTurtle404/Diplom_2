package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateUserNegativeTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty email")
    @Description("Impossible created user with empty email")
    public void userEmptyEmailCreatedTest(){
        var user = genericUserRandom();
        user.setEmail("");
        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createdEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty password")
    @Description("Impossible created user with empty password")
    public void userEmptyPassCreatedTest(){
        var user = genericUserRandom();
        user.setPassword("");
        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createdEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty name")
    @Description("Impossible created user with empty name")
    public void userEmptyNameCreatedTest(){
        var user = genericUserRandom();
        user.setName("");
        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createdEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }

}
