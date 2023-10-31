package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;
import praktikum.ingredients.Ingredients;

public class CreateOrderUnathTest {
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertion orderCheck  = new OrderAssertion();
    private Ingredients ingredients;
    private final String emptyAccessToken = "";

    @Test
    @DisplayName("Create order with ingredients, without login")
    @Description("Possible create order with ingredients,  without login")
    public void createOrderWithIngredientsWithoutLogin() {

        ingredients = Ingredients.getIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, emptyAccessToken);
        int orderNumber = orderCheck.createdOrderSuccessfully(createOrderResponse);

        Assert.assertNotEquals(0,orderNumber);
    }

    @Test
    @DisplayName("Create order without ingredients, without login")
    @Description("Impossible create order without ingredients,  without login")
    public void createOrderWithoutIngredientsWithoutLogin() {

        ingredients = Ingredients.getWithoutIngredients();

        ValidatableResponse createOrderResponse = orderClient.createOrder(ingredients, emptyAccessToken);
        orderCheck.createdOrderWithoutIngredientsUnuccessfully(createOrderResponse);
    }
}
