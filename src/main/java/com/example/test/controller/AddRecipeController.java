package com.example.test.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private JFXTextField textField[] = new JFXTextField[15];
    private int i=1;

    @FXML
    private GridPane pane_addRecipe;

    public AddRecipeController(ApplicationContext applicationContext,
                               @Value("classpath:/view/MainView.fxml") Resource mainView){
        this.applicationContext=applicationContext;
        this.mainView=mainView;
    }

    public void showMainView(ActionEvent actionEvent) throws IOException {

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
        textField[i] = new JFXTextField();
        pane_addRecipe.add(textField[i], 5, i);
        i = i + 1;
       /* pane_addRecipe.getChildren().add(newField);*/
    }
}
