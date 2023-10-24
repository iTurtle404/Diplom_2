package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.ingredients.Ingredients;
import praktikum.user.Credentials;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertion orderCheck  = new OrderAssertion();
    private Ingredients ingredients;
    private Ingredients withoutIngredients;
    private String accessToken;
    private final String emptyAccessToken = "";
    private int orderNumber;

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Create order with ingredients, with login")
    @Description("Possible create order with ingredients,  with login")
    public void createOrderWithIngredientsWithLogin() {
        var user = genericUserRandom();
        userClient.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser(creds);
        accessToken = userCheck.loggedSuccessfully(loginResponse);

        ingredients = Ingredients.getIngredients();
        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);
        assert orderNumber != 0;
    }
    @Test
    @DisplayName("Create order with ingredients, without login")
    @Description("Possible create order with ingredients,  without login")
    public void createOrderWithIngredientsWithoutLogin() {
        var user = genericUserRandom();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = userCheck.createdSuccessfully(createResponse);

        ingredients = Ingredients.getIngredients();
        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, emptyAccessToken);
        orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);
        assert orderNumber != 0;
    }

    @Test
    @DisplayName("Create order without ingredients, with login")
    @Description("Impossible create order without ingredients,  with login")
    public void createOrderWithoutIngredientsWithLogin() {
        var user = genericUserRandom();
        userClient.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser(creds);
        accessToken = userCheck.loggedSuccessfully(loginResponse);

        withoutIngredients = Ingredients.getWithoutIngredients();
        ValidatableResponse createOrderResponse = orderClient.createOrder(withoutIngredients, accessToken);
        orderCheck.createdOrderWithoutIngredientsUnuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Create order without ingredients, without login")
    @Description("Impossible create order without ingredients,  without login")
    public void createOrderWithoutIngredientsWithoutLogin() {
        var user = genericUserRandom();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = userCheck.createdSuccessfully(createResponse);

        withoutIngredients = Ingredients.getWithoutIngredients();
        ValidatableResponse createOrderResponse = orderClient.createOrder(withoutIngredients, emptyAccessToken);
        orderCheck.createdOrderWithoutIngredientsUnuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Create order with Invalid Hash, with login")
    @Description("Impossible create with Invalid Hash, with login")
    public void createOrderWithInvalidHashWithLogin(){
        var user = genericUserRandom();
        userClient.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser(creds);
        accessToken = userCheck.loggedSuccessfully(loginResponse);

        ingredients = Ingredients.getInvalidHash();
        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        orderCheck.createdOrderWithIvalidHashUnuccessfully(createOrderResponse);
    }
}
