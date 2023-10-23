package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.ingredients.Ingredients;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static praktikum.ingredients.Ingredients.getIngredients;
import static praktikum.user.UserGenerator.genericUserRandom;

public class CreateOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertion orderCheck  = new OrderAssertion();
    private Ingredients ingredients = getIngredients();
    private String accessToken;
    private String emptyAccessToken = "";
    private int orderNumber;

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Create order without login, with ingredients")
    @Description("Impossible create order without login")
    public void createOrderWithIngredientsWithoutLogin() {
        var user = genericUserRandom();
        ValidatableResponse createResponse = userClient.createUser(user);
        accessToken = userCheck.createdSuccessfully(createResponse);

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, emptyAccessToken);
        orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);
        assert orderNumber != 0;
    }

}
