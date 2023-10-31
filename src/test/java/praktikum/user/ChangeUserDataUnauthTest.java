package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static praktikum.constant.Message.WRONG_NOT_EQUALS_TOKEN;
import static praktikum.user.UserGenerator.*;
import static praktikum.user.UserGenerator.generatorRandomString;

public class ChangeUserDataUnauthTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;
    private User user;

    @Before
    public void setUp() {
        user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createSuccessfully(createResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Email without authorization ")
    public  void changeUserEmailUnauthTest(){

        user.setEmail(generatorRandomString()+DOMAIN);

        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changeDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Name without authorization ")
    public  void changeUserNameUnauthTest(){

        user.setName(generatorRandomString());

        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changeDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Password without authorization ")
    public  void changeUserPassUnauthTest(){

        user.setPassword(generatorRandomString());

        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changeDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change all data of user without authorization ")
    public  void changeUserAllDataUnauthTest(){

        user.setEmail(generatorRandomString()+DOMAIN);
        user.setPassword(generatorRandomString());
        user.setName(generatorRandomString());

        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changeDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Impossible change users Email with existing Email without authorization ")
    public  void changeUserEmailWithExistEmailUnauthTest(){

        var anotherUser = genericUserRandom();
        ValidatableResponse createAnotherResponse = client.createUser(anotherUser);
        String anotherAccessToken = check.createSuccessfully(createAnotherResponse);

        user.setEmail(anotherUser.getEmail());

        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changeDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);

        Assert.assertNotEquals(WRONG_NOT_EQUALS_TOKEN, accessToken, anotherAccessToken);
        client.deleteUser(anotherAccessToken);
    }
}
