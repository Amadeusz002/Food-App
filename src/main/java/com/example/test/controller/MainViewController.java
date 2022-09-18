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

import java.io.IOException;

//Recomended zrobić na podstawie kliknieć, zrobić counter w bazie, który zwiększamy za każdym razem jak wejdziemy w przepis i potem
//wyświetlamy te co mają najwięcej kliknięć
@Component
public class MainViewController {

    private ApplicationContext applicationContext;
    private final Resource addRecipeView;
    private final Resource searchRecipesView;

    public MainViewController(ApplicationContext applicationContext,
                              @Value("classpath:/view/AddRecipeView.fxml") Resource addRecipeView,
                              @Value("classpath:/view/SearchRecipeView.fxml") Resource searchRecipesView){
        this.applicationContext=applicationContext;
        this.addRecipeView=addRecipeView;
        this.searchRecipesView = searchRecipesView;
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


    public void showSearchRecipesView(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader=new FXMLLoader(searchRecipesView.getURL());
        fxmlLoader.setControllerFactory((applicationContext::getBean));
        Parent parent =fxmlLoader.load();
        Stage stage =  (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}
