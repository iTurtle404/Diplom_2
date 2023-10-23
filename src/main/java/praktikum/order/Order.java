package praktikum.order;
import praktikum.ingredients.Ingredients;

import java.util.List;
public class Order {
    private List<Ingredients> ingredients;

    public Order(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

}
