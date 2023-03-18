package ru.peremetova.diplom_2.api.data.response;

import java.util.List;

public class IngredientResponse {
    private boolean success;
    private List<IngredientData> data;

    public IngredientResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<IngredientData> getData() {
        return data;
    }

    public void setData(List<IngredientData> data) {
        this.data = data;
    }
}
