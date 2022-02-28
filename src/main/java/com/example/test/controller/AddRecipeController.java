package com.example.test.controller;

import com.example.test.util.IngredientQuantityType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AddRecipeController {

    public final ApplicationContext applicationContext;
    public final Resource mainView;
    public JFXComboBox quantityCombo;
    private JFXTextField ingredientFields[] = new JFXTextField[20];
    private JFXTextField quantityFields[] = new JFXTextField[20];
    private JFXComboBox comboFields[] = new JFXComboBox[20];
    private int i=10;
    private ObservableList<Node> children;
    private int sizePane=11;


    @FXML
    private GridPane pane_addRecipe;

    public AddRecipeController(ApplicationContext applicationContext,
                               @Value("classpath:/view/MainView.fxml") Resource mainView){
        this.applicationContext=applicationContext;
        this.mainView=mainView;
    }

    @FXML
    public void initialize() {
        this.quantityCombo.getItems().setAll(IngredientQuantityType.values());
    }
    public void showMainView(ActionEvent actionEvent) throws IOException {

        cleanTextFields();
        FXMLLoader fxmlLoader;
        fxmlLoader=new FXMLLoader(mainView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent =fxmlLoader.load();
        Stage stage =  (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }



    @FXML
    private void AddTextField(ActionEvent event)  {
        ingredientFields[i] = new JFXTextField();
        quantityFields[i]= new JFXTextField();
        comboFields[i]= new JFXComboBox();
        comboFields[i].getItems().setAll(IngredientQuantityType.values());


        pane_addRecipe.add(ingredientFields[i], 1, i);
        pane_addRecipe.add(quantityFields[i],3, i);
        pane_addRecipe.add(comboFields[i], 4, i );
        i = i + 1;

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
}
