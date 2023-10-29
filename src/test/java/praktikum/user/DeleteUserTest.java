package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static praktikum.user.UserGenerator.genericUserRandom;

public class DeleteUserTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();

    @Test
    @DisplayName("Check successfully delete api/auth/user with correct accessToken")
    @Description("Possible delete user with correct accessToken")
    public void deleteUserPositiveTest() {
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        String accessToken = check.createdSuccessfully(createResponse);
        ValidatableResponse delete = client.deleteUser(accessToken);
        check.deletedSuccessfully(delete);
    }
}
