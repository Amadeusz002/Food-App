package com.example.test.repository;

import com.example.test.model.Recipe;
import com.example.test.util.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
/*    Recipe findByName (String name);
    Recipe findByType (MealType type);*/

}
