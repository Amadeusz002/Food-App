package com.example.test.model;

import com.example.test.util.MealType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Recipe extends RecursiveTreeObject<Recipe> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    public Long getId() {
        return id;
    }

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private MealType mealType;

    @ManyToMany
    private List<Ingredient> listOfIngredients;

    public Recipe(){ }
    public Recipe(String name, String description, MealType mealType, List<Ingredient> listOfIngredients){
        this.name=name;
        this.description=description;
        this.mealType=mealType;
        this.listOfIngredients=listOfIngredients;
    }



    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public MealType getMealType () { return mealType; }

    public void setMealType(MealType mealType) { this.mealType=mealType; }

    public List<Ingredient> getListOfIngredients() { return listOfIngredients;}

    public void setListOfIngredients(Ingredient ingredient) {this.listOfIngredients.add(ingredient); }

    public StringProperty getNameProperty() {
        return new SimpleStringProperty(name);
    }

    public StringProperty getMealTypeProperty() {
        return new SimpleStringProperty(mealType.toString());
    }

    public StringProperty getDescriptionProperty() {
        return new SimpleStringProperty(description);
    }
}

