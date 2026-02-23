package com.mycompany.cafeshopmanagementsystemtest;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auth.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Cafe Shop Management System");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.show();

        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
