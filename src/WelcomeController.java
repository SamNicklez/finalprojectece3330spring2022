import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class that is used to control the first page of the GUI
 *
 * @author Samuel Nicklaus
 * @see PopupController
 * @see WordleController
 * @see LeaderboardController
 */
public class WelcomeController {
    /**
     * Is the scene the GUI
     */
    protected Scene scene;
    /**
     * Is the stage of the GUI
     */
    protected Stage stage;
    /**
     * Is the root of the GUI
     */
    protected Parent root;

    /**
     * is the client that is passed to other controllers to keep track of user
     */
    protected final Client client = new Client();

    /**
     * background is a Gridpane that is the background of the scene
     */
    @FXML // fx:id="background"
    private GridPane background; // Value injected by FXMLLoader

    /**
     * is the text above the text field asking for username
     */
    @FXML
    private Text warningText;

    /**
     * Is a button that opens up a list of instructions of the program
     */
    @FXML // fx:id="instructions"
    private Button instructions; // Value injected by FXMLLoader

    /**
     * Is a button that opens up the leaderboard rankings
     */
    @FXML // fx:id="leaderboardButton"
    private Button leaderboardButton; // Value injected by FXMLLoader

    /**
     * Is a button that starts the first round of wordle
     */
    @FXML // fx:id="startButton"
    private Button startButton; // Value injected by FXMLLoader

    /**
     * Is a textfield that takes in user input that is used to make a username
     */
    @FXML // fx:id="userName"
    private TextField userName; // Value injected by FXMLLoader

    /**
     * opens the all-time leaderboard on button press
     *
     * @param event button press
     */
    @FXML
    void AllTimePress(ActionEvent event) throws IOException {
        //Sets up new window to display instructions
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AllTimeLeaderboard.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Leaderboard");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * method that opens up a separate window stating the instructions of the game
     *
     * @param event is the button press of the instructions button
     */
    @FXML
    void instructionsButtonPress(ActionEvent event) {
        try {
            //Sets up new window to display instructions
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Instructions.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Instructions");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error finding FXML file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * method that opens a separate window with the current leaderboard when pressed
     *
     * @param event button press
     * @throws IOException if fxml file cannot be found
     */
    @FXML
    void leaderboardButtonPress(ActionEvent event) throws IOException {
        Boolean check;
        if (userName.getText().equals("")) {
            check = false;
        } else {
            client.setName(userName.getText().toLowerCase(Locale.ROOT));
            check = true;
        }
        try {
            if (check) {
                //Sets up new window to display instructions
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Leaderboard.fxml")));
                Scene scene = new Scene(root);
                primaryStage.setTitle("Leaderboard");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            } else {
                warningText.setText("Please enter a Username!!! (only letters allowed)");
            }
        } catch (IOException e) {
            System.out.println("Error finding FXML file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * method that starts the first round of wordle when pressed
     *
     * @param event button press
     * @throws IOException if fxml file does not exist
     */
    @FXML
    void startButtonPress(ActionEvent event) throws IOException {
        //checks if username has already been taken and/or the user has already played today
        Client client = new Client();
        boolean check = client.checkNameAvailability(userName.getText().toLowerCase(Locale.ROOT));
        if (check) {
            client.setName(userName.getText().toLowerCase(Locale.ROOT));
            //if username
            if (!userName.getText().equals("") && userName.getText().matches("^[a-zA-Z]*$")) {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/WordleMain.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setResizable(false);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            warningText.setText("Error: username invalid, username taken, or you have already played today (only letters allowed)");
        }
    }


    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert background != null : "fx:id=\"background\" was not injected: check your FXML file 'Page1GUI.fxml'.";
        assert instructions != null : "fx:id=\"instructions\" was not injected: check your FXML file 'Page1GUI.fxml'.";
        assert leaderboardButton != null : "fx:id=\"leaderboardButton\" was not injected: check your FXML file 'Page1GUI.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'Page1GUI.fxml'.";
        assert userName != null : "fx:id=\"userName\" was not injected: check your FXML file 'Page1GUI.fxml'.";
        //THIS COMMAND CHANGES BACKGROUND COLOR
        background.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
