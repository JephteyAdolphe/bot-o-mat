package jFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {        // Landing Page (Robot Creation)

        Parent createRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/creation.fxml")));
        Scene creation = new Scene(createRoot);


        primaryStage.setTitle("Jeff's Bot-O-Mat");
        primaryStage.setScene(creation);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
