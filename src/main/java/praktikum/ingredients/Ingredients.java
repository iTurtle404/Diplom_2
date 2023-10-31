package praktikum.ingredients;

import io.restassured.response.ValidatableResponse;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;
import static praktikum.user.UserGenerator.generatorRandomString;

public class Ingredients {
    public ArrayList<Object> ingredients;
    public Ingredients(ArrayList<Object> ingredients) {
            this.ingredients = ingredients;
    }
    public static Ingredients getIngredients() {
        ArrayList<Object> ingredients = new ArrayList<>();

        final IngredientsClient client = new IngredientsClient();
        final IngredientsAssertion check = new IngredientsAssertion();

        int bunIndex = nextInt(0, 2);
        int sauceIndex = nextInt(0, 4);
        int fillingIndex = nextInt(0, 9);

        ValidatableResponse gettingResponse = client.getIngredients();
        check.getIngredientsSuccessfully(gettingResponse);

        List<Object> buns = gettingResponse.extract().jsonPath().getList("data.findAll{it.type == 'bun'}._id");
        List<Object> sauces = gettingResponse.extract().jsonPath().getList("data.findAll{it.type == 'sauce'}._id");
        List<Object> fillings = gettingResponse.extract().jsonPath().getList("data.findAll{it.type == 'main'}._id");

        ingredients.add(buns.get(bunIndex));
        ingredients.add(sauces.get(sauceIndex));
        ingredients.add(fillings.get(fillingIndex));

        return new Ingredients(ingredients);
    }
    public static Ingredients getWithoutIngredients() {
        ArrayList<Object> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }
    public static Ingredients getInvalidHash(){
        ArrayList<Object> ingredients = new ArrayList<>();
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        ingredients.add(generatorRandomString());
        return new Ingredients(ingredients);

    }
}
