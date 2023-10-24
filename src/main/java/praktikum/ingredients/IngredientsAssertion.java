package praktikum.ingredients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.is;

public class IngredientsAssertion {
    @Step ("Check of successfully get Ingredients")
    public void gettingIngredientsSuccessfully(ValidatableResponse response) {
        response
                .assertThat()
                .body("success",is(true))
                .and()
                .statusCode(HttpURLConnection.HTTP_OK);
    }
}
