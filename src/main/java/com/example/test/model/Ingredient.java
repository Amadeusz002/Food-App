package com.example.test.model;

import com.example.test.util.IngredientQuantityType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Ingredient extends RecursiveTreeObject<Ingredient> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private int quantity;

    private IngredientQuantityType ingredientQuantityType;

    public Ingredient() { }

    public Ingredient(String name, int quantity ) {
        this.name=name;
        this.quantity = quantity;
        this.ingredientQuantityType=null;
    }

    public Ingredient(String name, int quantity, IngredientQuantityType ingredientQuantityType){
        this.name=name;
        this.quantity = quantity;
        this.ingredientQuantityType=ingredientQuantityType;
    }



    public Long getId() {
        return id;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public IngredientQuantityType getIngredientQuantityType() { return ingredientQuantityType; }

    public void setIngredientQuantityType(IngredientQuantityType ingredientQuantityType) { this.ingredientQuantityType = ingredientQuantityType; }



}
