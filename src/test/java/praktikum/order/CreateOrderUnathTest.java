package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.ingredients.Ingredients;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;

import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateOrderUnathTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertion orderCheck  = new OrderAssertion();
    private String accessToken;
    private final String emptyAccessToken = "";


    @Before
    public void setUp() {
        var user = genericUserRandom();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = userCheck.createdSuccessfully(createResponse);

    }
    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Create order with ingredients, without login")
    @Description("Possible create order with ingredients,  without login")
    public void createOrderWithIngredientsWithoutLogin() {

        Ingredients ingredients = Ingredients.getIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, emptyAccessToken);
        int orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);
        assert orderNumber != 0;
    }

    @Test
    @DisplayName("Create order without ingredients, without login")
    @Description("Impossible create order without ingredients,  without login")
    public void createOrderWithoutIngredientsWithoutLogin() {

        Ingredients withoutIngredients = Ingredients.getWithoutIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(withoutIngredients, emptyAccessToken);
        orderCheck.createdOrderWithoutIngredientsUnuccessfully(createOrderResponse);
    }
}
