package com.example.test.model;

import com.example.test.util.IngredientQuantityType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Ingredient extends RecursiveTreeObject<Ingredient> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String quantity;


    private IngredientQuantityType ingredientQuantityType;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipeID")
    private Recipe recipe;

    public Ingredient() { }

    public Ingredient(String name, String quantity, Recipe recipe ) {
        this.name=name;
        this.quantity = quantity;
        this.ingredientQuantityType=null;
        this.recipe=recipe;
    }

    public Ingredient(String name, String quantity, IngredientQuantityType ingredientQuantityType, Recipe recipe){
        this.name=name;
        this.quantity = quantity;
        this.ingredientQuantityType=ingredientQuantityType;
        this.recipe=recipe;
    }



    public Long getId() {
        return id;
    }

    public String getQuantity() { return quantity; }

    public void setQuantity(String quantity) { this.quantity = quantity; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public IngredientQuantityType getIngredientQuantityType() { return ingredientQuantityType; }

    public void setIngredientQuantityType(IngredientQuantityType ingredientQuantityType) { this.ingredientQuantityType = ingredientQuantityType; }

    public StringProperty getIngredientQuantityTypeProperty() { return new SimpleStringProperty(ingredientQuantityType.toString()); }

    public Recipe getRecipe() { return recipe; }

    public void setRecipe(Recipe recipe) { this.recipe = recipe; }


}
