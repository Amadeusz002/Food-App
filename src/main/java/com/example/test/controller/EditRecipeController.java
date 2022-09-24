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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class EditRecipeController {

    public final ApplicationContext applicationContext;
    public JFXComboBox quantityCombo;
    public Button AddIngredientButton;
    public Button DeleteIngredientButton;
    public JFXComboBox mealTypeCombo;
    public JFXTextField name, ingredient, quantity;
    public JFXTextArea description;

    private JFXTextField ingredientFields[] = new JFXTextField[20];
    private JFXTextField quantityFields[] = new JFXTextField[20];
    private JFXComboBox comboFields[] = new JFXComboBox[20];
    private int i=0;
    private ObservableList<Node> children;
    private int sizePane=11;
    private final Resource searchRecipesView;
    private Recipe recipe;


    @FXML
    private GridPane pane_editRecipe;

    public EditRecipeController(ApplicationContext applicationContext,
                               RecipeService recipeService,
                                @Value("classpath:/view/SearchRecipeView.fxml") Resource searchRecipesView){
        this.applicationContext=applicationContext;
        this.searchRecipesView=searchRecipesView;
    }

    @FXML
    public void initialize() {

        this.quantityCombo.getItems().setAll(IngredientQuantityType.values());
        this.mealTypeCombo.getItems().setAll(MealType.values());

    }

    /**
     * Metoda ustawia przepis do edycji
     * @param recipe
     */
    public void setData(Recipe recipe) {
        this.recipe=recipe;
        this.updateController();

    }

    /**
     * Metoda ustawia wartości pól przepisu do edycji.
     */
    private void updateController(){
        name.textProperty().setValue(recipe.getName());
        mealTypeCombo.setValue(recipe.getMealType());
        mealTypeCombo.setPromptText(recipe.getMealTypeProperty().getName());
        mealTypeCombo.setPlaceholder(new Text(recipe.getMealTypeProperty().getName()));
        description.textProperty().setValue(recipe.getDescription());
        for (Ingredient ingredient: recipe.getListOfIngredients()
             ) {
            ingredientFields[i] = new JFXTextField();
            quantityFields[i]= new JFXTextField();
            comboFields[i]= new JFXComboBox();
            comboFields[i].getItems().setAll(IngredientQuantityType.values());

            pane_editRecipe.add(ingredientFields[i], 1, i+10);
            pane_editRecipe.add(quantityFields[i],3, i+10);
            pane_editRecipe.add(comboFields[i], 4, i+10);
            ingredientFields[i].setPromptText(ingredient.getName());
            quantityFields[i].setPromptText(ingredient.getQuantity());
            comboFields[i].setValue(ingredient.getIngredientQuantityType());
            comboFields[i].setPromptText(ingredient.getIngredientQuantityTypeProperty().getName());
            comboFields[i].setPlaceholder(new Text(ingredient.getIngredientQuantityTypeProperty().getName()));
            i++;

        }



    }



    public void showSearchRecipesView(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader=new FXMLLoader(searchRecipesView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent =fxmlLoader.load();
        Stage stage =  (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        cleanTextFields();
    }


    @FXML
    private void AddTextField(ActionEvent event)  {
        if(i<19){
            ingredientFields[i] = new JFXTextField();
            quantityFields[i]= new JFXTextField();
            comboFields[i]= new JFXComboBox();
            comboFields[i].getItems().setAll(IngredientQuantityType.values());


            pane_editRecipe.add(ingredientFields[i], 1, i+10);
            pane_editRecipe.add(quantityFields[i],3, i+10);
            pane_editRecipe.add(comboFields[i], 4, i+10);

            i = i + 1;
        }


    }
    @FXML
    private void DeleteTextField(ActionEvent event)  {
        if(i>=0){
            pane_editRecipe.getChildren().remove(ingredientFields[i]);
            pane_editRecipe.getChildren().remove(quantityFields[i]);
            pane_editRecipe.getChildren().remove(comboFields[i]);

            ingredientFields[i] = null;
            quantityFields[i]= null;
            comboFields[i]= null;

            if(i>0) {
                i = i - 1;
            }
        }


    }

    private void cleanTextFields(){
        children=pane_editRecipe.getChildren();
        int size=children.size();

        while (size>sizePane && children.get(sizePane)!=null){
            children.remove(sizePane);
            size--;
        }
        i=0;
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) throws IOException {

//        /**
//         * stworzenie listy składników na podstawie formularzu
//         */
//        while(i>10){
//            if (this.ingredientFields[i]!=null && this.quantityFields[i]!=null ) {
//                Ingredient ingredient = new Ingredient(this.ingredientFields[i].getText(), Integer.parseInt(this.quantityFields[i].getText()), (IngredientQuantityType) this.comboFields[i].getValue());
//                listOfIngredients.add(ingredient);
//            }
//            i = i - 1;
//        }
//        /**
//         * stowrzenie nowego przepisu
//         */
//        Recipe recipe=new Recipe(name.getText(),  description.getText(), (MealType) mealTypeCombo.getValue(), listOfIngredients);
//        this.recipeService.save(recipe);
//        cleanTextFields();
//
//        showSearchRecipesView(actionEvent);
    }


}
