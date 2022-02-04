package com.example.test.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.awt.event.ActionEvent;
import java.io.IOException;

@Component
public class MainViewController {

    private ApplicationContext applicationContext;
    private final Resource addRecipeView;

    public MainViewController(ApplicationContext applicationContext,
                              @Value("classpath:/view/AddRecipeView.fxml") Resource addRecipeView){
        this.applicationContext=applicationContext;
        this.addRecipeView=addRecipeView;
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
