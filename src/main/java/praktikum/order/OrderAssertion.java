package praktikum.order;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.constant.Message;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;
import static praktikum.constant.Message.ERROR_CREATE_ORDER_MSG;

public class OrderAssertion {
    @Step ("Check of successfully created order")
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
}
