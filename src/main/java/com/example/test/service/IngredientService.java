package com.example.test.service;

import com.example.test.model.Ingredient;
import com.example.test.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
    }

    public Ingredient findByName(String name){ return ingredientRepository.findByName(name); }

    public List<Ingredient> findAll(){ return ingredientRepository.findAll(); }

    public void delete (Ingredient ingredient) { ingredientRepository.delete(ingredient);}

    public void deleteAll () { ingredientRepository.deleteAll();}

    public void save (Ingredient ingredient) {
        if(ingredient != null){
            ingredientRepository.save(ingredient);
        }
    }
}
