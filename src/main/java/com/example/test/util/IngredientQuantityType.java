package com.example.test.util;

public enum IngredientQuantityType {
    gram("g"),
    kilogram("kg"),
    liter("l"),
    milliliter("ml"),;

    private String label;

    IngredientQuantityType(String label){
        this.label=label;
    }

    public String toString(){
        return label;
    }

}
