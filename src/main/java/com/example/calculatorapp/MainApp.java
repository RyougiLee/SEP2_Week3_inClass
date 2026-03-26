package com.example.calculatorapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        setRoot("lang.MessagesBundle", Locale.getDefault());
    }

    public static void setRoot(String bundleName, Locale locale) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/example/calculatorapp/main-view.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}