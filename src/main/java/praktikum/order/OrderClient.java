package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;
import praktikum.ingredients.Ingredients;
import static praktikum.constant.URLForEndpoint.USER_ORDERS;

public class OrderClient extends Client {
    @Step ("Created new Order from random")
    public ValidatableResponse createOrder(Ingredients ingredients, String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .body(ingredients)
                .when()
                .post(USER_ORDERS)
                .then().log().all();
    }
}
