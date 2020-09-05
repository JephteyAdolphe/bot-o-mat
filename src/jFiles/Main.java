package jFiles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resources.data.dataCenter;

import java.util.Objects;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {        // Landing Page (Robot List)

        Parent mainRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/mainList.fxml")));
        Scene main = new Scene(mainRoot);


        primaryStage.setTitle("Jeff's Bot-O-Mat");
        primaryStage.setScene(main);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {       // Clears text file data
        dataCenter data = new dataCenter();
        data.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
