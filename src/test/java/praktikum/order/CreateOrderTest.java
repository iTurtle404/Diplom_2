package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    private String accessToken;

    @Before
    public void setUp() {
        var user = genericUserRandom();
        userClient.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser(creds);
        accessToken = userCheck.logSuccessfully(loginResponse);

    }
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

        ingredients = Ingredients.getIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        int orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);
        Assert.assertNotEquals(0,orderNumber);
    }

    @Test
    @DisplayName("Create order without ingredients, with login")
    @Description("Impossible create order without ingredients,  with login")
    public void createOrderWithoutIngredientsWithLogin() {

        ingredients = Ingredients.getWithoutIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        orderCheck.createdOrderWithoutIngredientsUnuccessfully(createOrderResponse);
    }

    @Test
    @DisplayName("Create order with Invalid Hash, with login")
    @Description("Impossible create with Invalid Hash, with login")
    public void createOrderWithInvalidHashWithLogin(){

        ingredients = Ingredients.getInvalidHash();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, accessToken);
        orderCheck.createdOrderWithIvalidHashUnuccessfully(createOrderResponse);
    }
}
