package com.example.test;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<JavafxApplication.StageReadyEvent> {

    @Override
    public void onApplicationEvent(JavafxApplication.StageReadyEvent event) {
        Stage stage = event.getStage();

    }
}
