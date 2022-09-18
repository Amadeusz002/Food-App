package com.example.test.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SearchRecipeController {
    public JFXButton filtersResetButton;
    public JFXTreeTableView recipesTableView;
    public TreeTableColumn nameColumn;
    public TreeTableColumn mealTypeColumn;
    public TreeTableColumn descriptionColumn;
    public JFXComboBox mealTypePicker;
    public JFXButton buttonDeleteRecipe;
    public JFXButton buttonEditRecipe;
    private ApplicationContext applicationContext;
    private final Resource recipeView;
    private final Resource mainView;
    private final Resource addRecipeView;

    public SearchRecipeController(ApplicationContext applicationContext,
                                  @Value("classpath:/view/SearchRecipeView.fxml") Resource recipeView,
                                  @Value("classpath:/view/MainView.fxml") Resource mainView,
                                  @Value("classpath:/view/AddRecipeView.fxml") Resource addRecipeView ) {
        this.applicationContext = applicationContext;
        this.recipeView=recipeView;
        this.mainView=mainView;
        this.addRecipeView=addRecipeView;
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
}
