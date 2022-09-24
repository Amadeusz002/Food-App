package com.example.test.controller;

import com.example.test.model.Recipe;
import com.example.test.service.RecipeService;
import com.example.test.util.GenericFilter;
import com.example.test.util.MealType;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Component
public class SearchRecipeController {
    public JFXButton filtersResetButton;
    public JFXTreeTableView<Recipe> recipesTableView;
    @FXML
    private TreeTableColumn<Recipe, String> nameColumn;
    @FXML
    private TreeTableColumn<Recipe, String> mealTypeColumn;
    @FXML
    private TreeTableColumn<Recipe, String> descriptionColumn;
    public JFXComboBox<MealType> mealTypePicker;
    public JFXButton buttonDeleteRecipe;
    public JFXButton buttonEditRecipe;
    private ApplicationContext applicationContext;
    private final Resource recipeView;
    private final Resource mainView;
    private final Resource addRecipeView;
    private final RecipeService recipeService;
    private final Resource editRecipeView;

    private GenericFilter<Recipe> recipeFilter;

    public SearchRecipeController(ApplicationContext applicationContext, RecipeService recipeService,
                                  @Value("classpath:/view/SearchRecipeView.fxml") Resource recipeView,
                                  @Value("classpath:/view/MainView.fxml") Resource mainView,
                                  @Value("classpath:/view/AddRecipeView.fxml") Resource addRecipeView,
                                  @Value("classpath:/view/EditRecipeView.fxml") Resource editRecipeView) {
        this.applicationContext = applicationContext;
        this.recipeView=recipeView;
        this.mainView=mainView;
        this.addRecipeView=addRecipeView;
        this.recipeService=recipeService;
        this.editRecipeView=editRecipeView;
    }
    public void setModel(){
        //Ustawienie kolumn
        nameColumn.setCellValueFactory(data -> data.getValue().getValue().getNameProperty());
        mealTypeColumn.setCellValueFactory(data -> data.getValue().getValue().getMealTypeProperty());
        descriptionColumn.setCellValueFactory(data -> data.getValue().getValue().getDescriptionProperty());

        //Ustawienie zawijania tekstu w kolumnie 'description'
        descriptionColumn.setCellFactory(data -> {
            TreeTableCell<Recipe, String> cell = new TreeTableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.wrappingWidthProperty().setValue(descriptionColumn.widthProperty().getValue() - 10);
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        //pobranie wartości z bazy danych
        ObservableList<Recipe> recipes;
        recipes= FXCollections.observableList(recipeService.findAll());

        //przekazanie danych do tabeli
        final TreeItem<Recipe> root= new RecursiveTreeItem<>(recipes, RecursiveTreeObject::getChildren);
        recipesTableView.setRoot(root);
        recipesTableView.setShowRoot(false);

    }

    private void setPredicates(){
        //Generyczna klasa filtrów dla danego modelu
        recipeFilter= new GenericFilter<>(recipesTableView);
        //filtrowanie na podstawie wartości comboBox
        recipeFilter.addPredicate(testedValue ->
                testedValue.getMealType().equals(mealTypePicker.getValue())||
                mealTypePicker.getValue()== null);
        //dodanie obserwatora zmiany wartości
        recipeFilter.setListener(mealTypePicker.valueProperty());
    }
    @FXML
    private void resetFilter(){
        mealTypePicker.setValue(null);
    }

    @FXML
    void initialize(){
        this.setModel();
        this.setMealTypePicker();
        this.setPredicates();
    }


    private void setMealTypePicker(){
        mealTypePicker.getItems().setAll(MealType.values());
    }

    @FXML
    private void deleteRecipe(javafx.event.ActionEvent actionEvent){
        var recipes=recipesTableView.getSelectionModel().getSelectedItem().getValue();
        recipeService.delete(recipes);
        this.setModel();
    }

    @FXML
    private void handleUpdateAction(ActionEvent actionEvent) throws IOException {
        var recipe=recipesTableView.getSelectionModel().getSelectedItem();
        if(recipe!=null){
            this.showEditRecipeView(actionEvent,recipe.getValue());
        }
    }

    public void showMainView(ActionEvent actionEvent) throws IOException {
        
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(mainView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void showAddRecipeView(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader=new FXMLLoader(addRecipeView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent =fxmlLoader.load();
        Stage stage =  (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void showEditRecipeView(javafx.event.ActionEvent actionEvent, Recipe recipe) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(editRecipeView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent =fxmlLoader.load();
        Stage stage =  (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        if (recipe!=null){
            EditRecipeController editRecipeController= fxmlLoader.getController();
            editRecipeController.setData(recipe);
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
