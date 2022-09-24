package com.example.test.controller;

import com.example.test.model.Ingredient;
import com.example.test.model.Recipe;
import com.example.test.service.IngredientService;
import com.example.test.service.RecipeService;
import com.example.test.util.IngredientQuantityType;
import com.example.test.util.MealType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
public class AddRecipeController {

    public final ApplicationContext applicationContext;
    public final Resource mainView;
    public JFXComboBox quantityCombo;
    public Button AddIngredientButton;
    public Button DeleteIngredientButton;
    public JFXComboBox mealTypeCombo;
    public JFXTextField name, ingredient, quantity;
    public JFXTextArea description;

    private List<Ingredient> listOfIngredients= new LinkedList<>();
    public JFXTextField[] ingredientFields = new JFXTextField[20];
    public JFXTextField[] quantityFields = new JFXTextField[20];
    public JFXComboBox[] comboFields = new JFXComboBox[20];
    private int i=0;
    private ObservableList<Node> children;
    private int sizePane=11;
    private RecipeService recipeService;
    private IngredientService ingredientService;

// Do edycji przepisów zrobi się nowy widok i kontroler
    @FXML
    private GridPane pane_addRecipe;

    public AddRecipeController(ApplicationContext applicationContext,
                               @Value("classpath:/view/MainView.fxml") Resource mainView,
                               RecipeService recipeService, IngredientService ingredientService){
        this.applicationContext=applicationContext;
        this.mainView=mainView;
        this.recipeService=recipeService;
        this.ingredientService=ingredientService;
    }

    @FXML
    public void initialize() {

        this.quantityCombo.getItems().setAll(IngredientQuantityType.values());
        this.mealTypeCombo.getItems().setAll(MealType.values());

    }
    public void showMainView(ActionEvent actionEvent) throws IOException {

        cleanTextFields();
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(mainView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void AddTextField(ActionEvent event)  {
        if(i<19){
            ingredientFields[i] = new JFXTextField();
            quantityFields[i]= new JFXTextField();
            comboFields[i]= new JFXComboBox();
            comboFields[i].getItems().setAll(IngredientQuantityType.values());


            pane_addRecipe.add(ingredientFields[i], 1, i+10);
            pane_addRecipe.add(quantityFields[i],3, i+10);
            pane_addRecipe.add(comboFields[i], 4, i +10);

            i = i + 1;
        }


    }
    @FXML
    private void DeleteTextField(ActionEvent event)  {
        if(i>=0){
            pane_addRecipe.getChildren().remove(ingredientFields[i]);
            pane_addRecipe.getChildren().remove(quantityFields[i]);
            pane_addRecipe.getChildren().remove(comboFields[i]);

            ingredientFields[i] = null;
            quantityFields[i]= null;
            comboFields[i]= null;

            if(i>0) {
                i = i - 1;
            }
        }


    }

    private void cleanTextFields(){
        children=pane_addRecipe.getChildren();
        int size=children.size();

        while (size>sizePane && children.get(sizePane)!=null){
            children.remove(sizePane);
            size--;
        }
        i=0;
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws IOException {

        /**
         * stworzenie listy składników na podstawie formularzu
         */
        Recipe recipe=new Recipe(name.getText(),  description.getText(), (MealType) mealTypeCombo.getValue());
        recipeService.save(recipe);
        Ingredient ingredient1 = new Ingredient((this.ingredient.getText()),this.quantity.getText(),(IngredientQuantityType) this.quantityCombo.getValue(), recipe );
        this.ingredientService.save(ingredient1);
        this.listOfIngredients.add(ingredient1);
        while(i>=0){
            if (ingredientFields[i]!=null && quantityFields[i]!=null ) {
                ingredient1 = new Ingredient(ingredientFields[i].getText(), quantityFields[i].getText(), (IngredientQuantityType) comboFields[i].getValue(), recipe);
                this.ingredientService.save(ingredient1);
                this.listOfIngredients.add(ingredient1);

            }
            i = i - 1;

        }
        /**
         * stowrzenie nowego przepisu
         */
        cleanTextFields();

        showMainView(actionEvent);
    }
}
