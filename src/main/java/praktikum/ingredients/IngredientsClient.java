package praktikum.ingredients;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import static praktikum.constant.URLForEndpoint.INGREDIENTS_PATH;

public class IngredientsClient extends Client {
    @Step("Getting ingredients")
    public ValidatableResponse getIngredients() {
        return spec()
                .when()
                .get(INGREDIENTS_PATH)
                .then().log().all();
    }

}
