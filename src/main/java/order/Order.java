package order;

import java.util.List;

public class Order {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/";
    private List<String> ingredients;
    private String number;
    private String name;


    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
