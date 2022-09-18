package com.example.test.controller;

import com.example.test.model.Ingredient;
import com.example.test.model.Recipe;
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

    private List<Ingredient> listOfIngredients;
    private JFXTextField ingredientFields[] = new JFXTextField[20];
    private JFXTextField quantityFields[] = new JFXTextField[20];
    private JFXComboBox comboFields[] = new JFXComboBox[20];
    private int i=10;
    private ObservableList<Node> children;
    private int sizePane=11;
    private RecipeService recipeService;

// Do edycji przepisów zrobi się nowy widok i kontroler
    @FXML
    private GridPane pane_addRecipe;

    public AddRecipeController(ApplicationContext applicationContext,
                               @Value("classpath:/view/MainView.fxml") Resource mainView,
                               RecipeService recipeService){
        this.applicationContext=applicationContext;
        this.mainView=mainView;
        this.recipeService=recipeService;
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


            pane_addRecipe.add(ingredientFields[i], 1, i);
            pane_addRecipe.add(quantityFields[i],3, i);
            pane_addRecipe.add(comboFields[i], 4, i );

            i = i + 1;
        }


    }
    @FXML
    private void DeleteTextField(ActionEvent event)  {
        if(i>9){
            pane_addRecipe.getChildren().remove(ingredientFields[i]);
            pane_addRecipe.getChildren().remove(quantityFields[i]);
            pane_addRecipe.getChildren().remove(comboFields[i]);

            ingredientFields[i] = null;
            quantityFields[i]= null;
            comboFields[i]= null;

            i = i - 1;
        }


    }

    private void cleanTextFields(){
        children=pane_addRecipe.getChildren();
        int size=children.size();

        while (size>sizePane && children.get(sizePane)!=null){
            children.remove(sizePane);
            size--;
        }
        i=10;
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws IOException {

        /**
         * stworzenie listy składników na podstawie formularzu
         */
        while(i>10){
            if (this.ingredientFields[i]!=null && this.quantityFields[i]!=null ) {
                Ingredient ingredient = new Ingredient(this.ingredientFields[i].getText(), Integer.parseInt(this.quantityFields[i].getText()), (IngredientQuantityType) this.comboFields[i].getValue());
                listOfIngredients.add(ingredient);
            }
            i = i - 1;
        }
        /**
         * stowrzenie nowego przepisu
         */
        Recipe recipe=new Recipe(name.getText(),  description.getText(), (MealType) mealTypeCombo.getValue(), listOfIngredients);
        this.recipeService.save(recipe);
        cleanTextFields();

        showMainView(actionEvent);
    }
}
