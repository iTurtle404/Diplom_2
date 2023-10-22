package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static praktikum.constant.Message.ERROR_UPDATE_DATA;
import static praktikum.user.UserGenerator.*;

public class ChangeUserDataTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;

    @After
    public void tearDown() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }

    static void checkUpdatedEmail(String updatedEmail, ValidatableResponse changeDataResponse) {
        String actualEmail = changeDataResponse.extract().path("user.email");
        Assert.assertEquals(ERROR_UPDATE_DATA, updatedEmail.toLowerCase(), actualEmail);
    }

    static void checkUpdateName(String updatedName, ValidatableResponse changeDataResponse) {
        String actualName = changeDataResponse.extract().path("user.name");
        Assert.assertEquals(ERROR_UPDATE_DATA, updatedName, actualName);
    }
    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users Email by authorization ")
    public  void changeUserEmailPositiveTest(){
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        String updatedEmail = generatorRandomString()+DOMAIN;
        user.setEmail(updatedEmail);
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(updatedEmail, changeDataResponse);

    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users Name by authorization ")
    public  void changeUserNamePositiveTest(){
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        String updatedName = generatorRandomString();
        user.setName(updatedName);
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserDataSuccessfully(changeDataResponse);
        checkUpdateName(updatedName, changeDataResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users Name and email by authorization ")
    public  void changeUserNameEmailPositiveTest(){
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        String updatedEmail = generatorRandomString()+DOMAIN;
        user.setEmail(updatedEmail);
        String updatedName = generatorRandomString();
        user.setName(updatedName);
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(updatedEmail, changeDataResponse);
        checkUpdateName(updatedName, changeDataResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change users password by authorization ")
    public  void changeUserPassPositiveTest(){
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        user.setPassword(generatorRandomString());
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(user.getEmail(), changeDataResponse);
        checkUpdateName(user.getName(), changeDataResponse);
    }
}
