package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static praktikum.constant.Message.ERROR_UPDATE_DATA;
import static praktikum.constant.Message.WRONG_NOT_EQUALS_TOKEN;
import static praktikum.user.UserGenerator.*;

public class ChangeUserDataTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;
    private User user;

    static void checkUpdatedEmail(String updatedEmail, ValidatableResponse changeDataResponse) {
        String actualEmail = changeDataResponse.extract().path("user.email");
        Assert.assertEquals(ERROR_UPDATE_DATA, updatedEmail.toLowerCase(), actualEmail);
    }

    static void checkUpdateName(String updatedName, ValidatableResponse changeDataResponse) {
        String actualName = changeDataResponse.extract().path("user.name");
        Assert.assertEquals(ERROR_UPDATE_DATA, updatedName, actualName);
    }

    @Before
    public void setUp() {
        user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.logSuccessfully(loginResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users Email by authorization ")
    public  void changeUserEmailPositiveTest(){

        String updatedEmail = generatorRandomString()+DOMAIN;
        user.setEmail(updatedEmail);

        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changeUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(updatedEmail, changeDataResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users Name by authorization ")
    public  void changeUserNamePositiveTest(){

        String updatedName = generatorRandomString();
        user.setName(updatedName);

        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changeUserDataSuccessfully(changeDataResponse);
        checkUpdateName(updatedName, changeDataResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users password by authorization ")
    public  void changeUserPassPositiveTest(){

        String updatedPass = generatorRandomString();
        user.setPassword(updatedPass);

        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changeUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(user.getEmail(), changeDataResponse);
        checkUpdateName(user.getName(), changeDataResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change all data of User by authorization ")
    public  void changeUserAllDataPositiveTest(){

        String updatedEmail = generatorRandomString()+DOMAIN;
        user.setEmail(updatedEmail);

        String updatedName = generatorRandomString();
        user.setName(updatedName);

        String updatedPass = generatorRandomString();
        user.setPassword(updatedPass);

        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changeUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(updatedEmail, changeDataResponse);
        checkUpdateName(updatedName, changeDataResponse);
    }
    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Impossible change users Email with existing Email by authorization ")
    public  void changeUserEmailWithExistEmailTest(){

        var anotherUser = genericUserRandom();
        ValidatableResponse createAnotherResponse = client.createUser(anotherUser);
        String anotherAccessToken = check.createSuccessfully(createAnotherResponse);

        user.setEmail(anotherUser.getEmail());

        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changeUserExistEmailUnsuccessfully(changeDataResponse);

        Assert.assertNotEquals(WRONG_NOT_EQUALS_TOKEN, accessToken, anotherAccessToken);
        client.deleteUser(anotherAccessToken);
    }
}
