import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Class Leaderboard Controller is used to grab leaderboard statistics from the server
 * and display them to the user
 *
 * @author Samuel Nicklaus
 * @see WelcomeController
 * @see WordleController
 * @see InstructionsController
 */
public class AllTimeLeaderboardController extends WelcomeController {

    /**
     * client that is used to communicate with teh server
     */
    protected final Client user = client;

    /**
     * text that holds the score of the fifth place member for that day
     */
    @FXML // fx:id="fifthPlace"
    private Text fifthPlace; // Value injected by FXMLLoader

    /**
     * text that holds the score of the first place member for that day
     */
    @FXML // fx:id="firstPlace"
    private Text firstPlace; // Value injected by FXMLLoader

    /**
     * text that holds the score of the fourth place member for that day
     */
    @FXML // fx:id="fourthPlace"
    private Text fourthPlace; // Value injected by FXMLLoader

    /**
     * text that holds the score of the second place member for that day
     */
    @FXML // fx:id="secondPlace"
    private Text secondPlace; // Value injected by FXMLLoader

    /**
     * text that holds the score of the third place member for that day
     */
    @FXML // fx:id="thirdPlace"
    private Text thirdPlace; // Value injected by FXMLLoader

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert fifthPlace != null : "fx:id=\"fifthPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert firstPlace != null : "fx:id=\"firstPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert fourthPlace != null : "fx:id=\"fourthPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert secondPlace != null : "fx:id=\"secondPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert thirdPlace != null : "fx:id=\"thirdPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        Client c = new Client();
        String[] scores = c.getTopFiveAllTime();
        //if scores are null, replace with nothing
        for (int x = 0; x < scores.length; x++) {
            if (scores[x] == null) {
                scores[x] = "";
            }
        }
        c = null;
        //set scores
        firstPlace.setText("First: " + scores[0] + " " + scores[1]);
        secondPlace.setText("Second: " + scores[2] + " " + scores[3]);
        thirdPlace.setText("Third: " + scores[4] + " " + scores[5]);
        fourthPlace.setText("Fourth: " + scores[6] + " " + scores[7]);
        fifthPlace.setText("Fifth: " + scores[8] + " " + scores[9]);

    }
}