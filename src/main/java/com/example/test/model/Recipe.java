package com.example.test.model;

import com.example.test.util.MealType;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    private List<Ingredient> list_of_ingredients;


    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public MealType getMealType () { return mealType; }

    public void setMealType(MealType mealType) { this.mealType=mealType; }

    public List<Ingredient> getList_of_ingredients () { return list_of_ingredients;}

    public void setList_of_ingredients (Ingredient ingredient) {this.list_of_ingredients.add(ingredient); }

}

