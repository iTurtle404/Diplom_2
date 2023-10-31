package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateUserNegativeTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private User user;

    @Before
    public void setUp() {
        user = genericUserRandom();
    }

    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty email")
    @Description("Impossible created user with empty email")
    public void userEmptyEmailCreatedTest(){

        user.setEmail("");

        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty password")
    @Description("Impossible created user with empty password")
    public void userEmptyPassCreatedTest(){

        user.setPassword("");

        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully post /api/auth/register with empty name")
    @Description("Impossible created user with empty name")
    public void userEmptyNameCreatedTest(){

        user.setName("");

        ValidatableResponse createEmptyFieldResponse = client.createUser(user);
        check.createEmptyFieldUnsuccessfully(createEmptyFieldResponse);
    }
}
