package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;
import praktikum.ingredients.Ingredients;

import static praktikum.constant.URLForEndpoint.GET_ORDERS_PATH;
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

    @Step("Get all orders from user with users token")
    public ValidatableResponse getAllOrderUser(String accessToken) {
        return spec()
                .header("Authorization", accessToken)
                .when()
                .get(GET_ORDERS_PATH)
                .then().log().all();
    }
    @Step("Get all orders from user without users token")
    public ValidatableResponse getAllOrderWithoutLogUser() {
        return spec()
                .when()
                .get(GET_ORDERS_PATH)
                .then().log().all();
    }
}
