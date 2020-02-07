package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.logic.Game;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("graphics/sample.fxml"));
        primaryStage.setTitle("Hello World");
        Game game = new Game();


        primaryStage.setScene(new Scene(game.getBorder(), 900, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
