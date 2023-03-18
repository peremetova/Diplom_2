package ru.peremetova.diplom_2.api.data.request;

import java.util.List;

public class IngredientsData {
    private List<String> ingredients;

    public IngredientsData() {
    }

    public IngredientsData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
