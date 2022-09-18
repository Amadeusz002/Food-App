package com.example.test.util;

public enum MealType {
    DINNER("Dinner"),
    BREAKFAST("Breakfast"),
    SUPPER("Supper");

    private String label;

    MealType(String label){
        this.label=label;
    }

    public String toString(){
        return label;
    }
}
