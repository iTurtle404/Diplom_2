package praktikum.user;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static praktikum.constant.Message.ERROR_UPDATE_DATA;
import static praktikum.constant.Message.WRONG_NOT_EQUALS_TOKEN;
import static praktikum.user.UserGenerator.*;

public class ChangeUserDataTest {
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;
    private String anotherAccessToken;

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

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Possible change all data of User by authorization ")
    public  void changeUserAllDataPositiveTest(){
        var user = genericUserRandom();
        client.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);
        String updatedEmail = generatorRandomString()+DOMAIN;
        user.setEmail(updatedEmail);
        String updatedName = generatorRandomString();
        user.setName(updatedName);
        user.setPassword(generatorRandomString());
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserDataSuccessfully(changeDataResponse);
        checkUpdatedEmail(updatedEmail, changeDataResponse);
        checkUpdateName(updatedName, changeDataResponse);
    }
    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Impossible change users Email with existing Email by authorization ")
    public  void changeUserEmailWithExistEmailTest(){
        var user = genericUserRandom();
        client.createUser(user);

        var anotherUser = genericUserRandom();
        ValidatableResponse createAnotherResponse = client.createAnotherUser(anotherUser);

        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.loginUser(creds);
        accessToken = check.loggedSuccessfully(loginResponse);

        user.setEmail(anotherUser.getEmail());
        ValidatableResponse changeDataResponse = client.changeUserData(user, accessToken);
        check.changedUserExistEmailUnsuccessfully(changeDataResponse);

        anotherAccessToken = check.createdSuccessfully(createAnotherResponse);
        Assert.assertNotEquals(WRONG_NOT_EQUALS_TOKEN, accessToken,anotherAccessToken);
        client.deleteUser(anotherAccessToken);
    }

    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Email without authorization ")
    public  void changeUserEmailUnauthTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);
        user.setEmail(generatorRandomString()+DOMAIN);
        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changedDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Name without authorization ")
    public  void changeUserNameUnauthTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);
        user.setName(generatorRandomString());
        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changedDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }
    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change users Password without authorization ")
    public  void changeUserPassUnauthTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);
        user.setPassword(generatorRandomString());
        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changedDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }

    @Test
    @DisplayName("Check unsuccessfully patch /api/auth/user")
    @Description("Impossible change all data of user without authorization ")
    public  void changeUserAllDataUnauthTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);
        user.setEmail(generatorRandomString()+DOMAIN);
        user.setPassword(generatorRandomString());
        user.setName(generatorRandomString());
        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changedDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);
    }

    @Test
    @DisplayName("Check successfully patch /api/auth/user")
    @Description("Impossible change users Email with existing Email without authorization ")
    public  void changeUserEmailWithExistEmailUnauthTest(){
        var user = genericUserRandom();
        ValidatableResponse createResponse = client.createUser(user);
        accessToken = check.createdSuccessfully(createResponse);

        var anotherUser = genericUserRandom();
        ValidatableResponse createAnotherResponse = client.createAnotherUser(anotherUser);

        user.setEmail(anotherUser.getEmail());
        ValidatableResponse changeDataUnauthUserResponse = client.changeDataUnauthUser(user);
        check.changedDataUnauthUserUnuccessfully(changeDataUnauthUserResponse);

        anotherAccessToken = check.createdSuccessfully(createAnotherResponse);
        Assert.assertNotEquals(WRONG_NOT_EQUALS_TOKEN, accessToken,anotherAccessToken);
        client.deleteUser(anotherAccessToken);
    }
}
