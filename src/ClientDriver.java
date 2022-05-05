import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class ClientDriver extends Application and is used to start the first page of the GUI
 * @auther Samuel Nicklaus
 * @see Client
 */
public class ClientDriver extends Application {

    /**
     * Main method is used to call launch method
     * @param args which is passed onto method launch
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * method start finds the fxml file and loads it into the scene along with displaying it
     * @param primaryStage which is the stage the GUI will be displayed on
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            //Sets up starting screen for wordle
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Page1GUI.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Wordle");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            //add server/client start methods here

        } catch (IOException e) {
            System.out.println("Error finding FXML file");
            System.out.println(e.getMessage());
        }
    }
}
