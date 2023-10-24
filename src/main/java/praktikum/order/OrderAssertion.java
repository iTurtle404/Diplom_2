package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.constant.Message;

import java.net.HttpURLConnection;
import static org.hamcrest.Matchers.*;


public class OrderAssertion {
    @Step ("Check of successfully created order with ingredients")
    public int createdOrderSuccessfully(ValidatableResponse response) {
        int orderNumber = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success",is(true))
                .body("name", notNullValue())
                .extract()
                .path("order.number");
        return orderNumber;
    }
    @Step ("Check of unsuccessfully created order without ingredients")
    public void createdOrderWithoutIngredientsUnuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(false))
                .body("message", equalTo(Message.ERROR_INGREDIENTS_ORDER_MSG))
                .and()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST);
    }

    @Step ("Check of unsuccessfully created order without invalid hash")
    public void createdOrderWithIvalidHashUnuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
    }

    @Step ("Check of successfully getting order by user login")
    public void getAllOrderSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success",is(true))
                .body("orders", notNullValue());
    }

    @Step ("Check of unsuccessfully getting order by user without login")
    public void getAllOrderWithoutLogUnsuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED);
    }
}
