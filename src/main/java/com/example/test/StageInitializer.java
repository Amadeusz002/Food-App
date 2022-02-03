package com.example.test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    private final String applicationTitle;
    private final Resource mainView;

    private final ApplicationContext applicationContext;

    public StageInitializer(@Value("FoodApp" ) String applicationTitle,
                            @Value("classpath:/view/MainView.fxml") Resource mainView,
                            ApplicationContext applicationContext){
        this.applicationTitle=applicationTitle;
        this.mainView=mainView;
        this.applicationContext=applicationContext;
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent event) {

        //ładujemy główny widok z pliku .fxml
        FXMLLoader fxmlloader = new FXMLLoader(mainView.getURL());

        //Spring wstrzykuje odpowiedni kontroler obsługujący dany plik .fxml na podstawie kontekstu aplikacji
        fxmlloader.setControllerFactory(applicationContext::getBean);

        //wczytanie sceny
        Parent parent = fxmlloader.load();

        Stage stage = event.getStage();

        //utworzenie i wyświetlenie sceny
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle(applicationTitle);
        stage.show();



    }
}
