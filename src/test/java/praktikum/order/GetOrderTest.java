package praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import praktikum.user.Credentials;
import praktikum.user.UserAssertions;
import praktikum.user.UserClient;

import static praktikum.user.UserGenerator.genericUserRandom;

public class GetOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private final UserAssertions userCheck = new UserAssertions();
    private final OrderAssertion orderCheck  = new OrderAssertion();

    @Test
    @DisplayName("Get all order with login")
    @Description("Possible get users orders with login")
    public void getOrdersUserWithLogin(){
        var user = genericUserRandom();
        userClient.createUser(user);
        var creds = Credentials.from(user);
        ValidatableResponse loginResponse = userClient.loginUser(creds);
        String accessToken = userCheck.loggedSuccessfully(loginResponse);

        ValidatableResponse getOrderResponse = orderClient.getAllOrderUser(accessToken);
        orderCheck.getAllOrderSuccessfully(getOrderResponse );
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Get all order without login")
    @Description("Impossible get users orders without login")
    public void getOrdersUserWithoutLogin(){
        ValidatableResponse getOrderResponse = orderClient.getAllOrderWithoutLogUser();
        orderCheck.getAllOrderWithoutLogUnsuccessfully(getOrderResponse );
    }
}
