package com.example.test.service;

import com.example.test.model.Recipe;
import com.example.test.repository.RecipeRepository;

import java.util.List;

public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository=recipeRepository;
    }



    public Recipe findByName(String name){ return recipeRepository.findByName(name); }

    public List<Recipe> findAll(){ return recipeRepository.findAll(); }

    public void delete (Recipe recipe) { recipeRepository.delete(recipe);}

    public void deleteAll () { recipeRepository.deleteAll();}

    public void save (Recipe recipe) {
        if(recipe != null){
            recipeRepository.save(recipe);
        }
    }
}
