package com.example.test.util;

public enum MealType {
    DINNER("dinner"),
    BREAKFAST("breakfast"),
    SUPPER("supper");

    private String label;

    MealType(String label){
        this.label=label;
    }

    public String toString(){
        return label;
    }
}
