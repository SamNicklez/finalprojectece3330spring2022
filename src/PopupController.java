import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

/**
 * class popupController is the controller that creates the window after each round is made
 *
 * @author Samuel Nicklaus
 */
public class PopupController extends WordleController {

    /**
     * the amount of attempts the user finished the round in
     */
    private static int attempt;
    /**
     * boolean that tells us if the game is over or not
     */
    private static Boolean finish;
    /**
     * string that contains the answer to the round
     */
    private static String answer;
    /**
     * boolean that tells up if the user got the answer correct or not
     */
    private static boolean correctBool;

    /**
     * text that is displayed to the user
     */
    @FXML
    private Text popupText;

    /**
     * button that is used to go to either the next round or back to the home screen
     */
    @FXML // fx:id="button"
    private Button button; // Value injected by FXMLLoader

    /**
     * is the textbox that contains the correct word
     */
    @FXML
    private Text correctWord;

    /**
     * is a method that checks if there is another round to be played. If so it will start the next round
     * else it will send user back to home screen
     *
     * @param event button press
     * @throws IOException if fxml file is not found
     */
    @FXML
    void nextRound(ActionEvent event) throws IOException {
        //If button = HOME, meaning that the 5 rounds have already been played
        if (event.getSource().toString().substring(event.getSource().toString().length() - 5, event.getSource().toString().length() - 1).equals("HOME")) {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Page1GUI.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            //if more rounds need to be played
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/WordleMain.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * helper function that takes in data from WordleController
     *
     * @param attempts   is the # of attempts it took to guess the word
     * @param finished   is a boolean that tells us if the user has played 5 rounds
     * @param correctAns is a string containing the correct answer to the round
     * @param correct    is a boolean that tells us if the user got the answer correct
     * @see WordleController
     */
    public static void setTextFunc(int attempts, boolean finished, String correctAns, boolean correct) {
        attempt = attempts;
        finish = finished;
        answer = correctAns;
        correctBool = correct;
    }

    @FXML
    /**
     * This method is called by the FXMLLoader when initialization is complete
     * sets button and text based on how the user did in the round
     */
    void initialize() {
        assert popupText != null : "fx:id=\"popupText\" was not injected: check your FXML file 'popup.fxml'.";
        //if only 1 attempt, tell him nice job
        if (attempt == 1 && !finish) {
            popupText.setText("GENIUS!" + "\n" + "YOU FOUND THE WORD IN 1 ATTEMPT");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if only 2 attempt, tell him nice job
        else if (attempt == 2 && !finish) {
            popupText.setText("MAGNIFICENT!" + "\n" + "YOU FOUND THE WORD IN 2 ATTEMPTS");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if only 3 attempt, tell him nice job
        else if (attempt == 3 && !finish) {
            popupText.setText("IMPRESSIVE!" + "\n" + "YOU FOUND THE WORD IN 3 ATTEMPTS");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if only 4 attempt, tell him nice job
        else if (attempt == 4 && !finish) {
            popupText.setText("SPLENDID!" + "\n" + "YOU FOUND THE WORD IN 4 ATTEMPTS");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if only 5 attempt, tell him nice job
        else if (attempt == 5 && !finish) {
            popupText.setText("GREAT!" + "\n" + "YOU FOUND THE WORD IN 5 ATTEMPTS");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if user has finished all 5 rounds
        else if (finish) {
            popupText.setText("THANKS FOR PLAYING");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
            button.setText("HOME");
        }
        //if user got the guess correct on the last attempt
        else if (correctBool) {
            popupText.setText("CLOSE ONE!" + "\n" + "YOU FOUND THE WORD IN 6 ATTEMPTS");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }
        //if user did not get the word
        else {
            popupText.setText("BETTER LUCK NEXT TIME");
            correctWord.setText("The Word Was: " + answer.toUpperCase(Locale.ROOT));
        }

    }
}



