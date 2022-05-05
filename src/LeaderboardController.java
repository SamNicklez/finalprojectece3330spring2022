import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class Leaderboard Controller is used to grab leaderboard statistics from the server
 * and display them to the user
 *
 * @author Samuel Nicklaus
 * @see WelcomeController
 * @see WordleController
 * @see InstructionsController
 */
public class LeaderboardController extends WelcomeController {

    /**
     * GUI button that goes back one day on the leaderboard
     */
    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    /**
     * text userscore stores text of user
     */
    @FXML
    private Text userScore;

    /**
     * is the box that the user enters in to search stats of user
     */
    @FXML
    private TextField userSearchBox;

    /**
     * client that is used to communicate with teh server
     */
    protected final Client user = client;

    /**
     * text that represents the date in MM/DD/YYYY format
     */
    @FXML // fx:id="date"
    private Text date; // Value injected by FXMLLoader

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
     * GUI button that goes to the next day for leaderboard
     */
    @FXML // fx:id="forwardButton"
    private Button forwardButton; // Value injected by FXMLLoader

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
     * button that when pressed searches the desired user
     */
    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    /**
     * object that contains the date
     */
    private LocalDate dateObj = LocalDate.now();

    /**
     * formatter that tells the date to display in MM/DD/YYYY format
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    /**
     * changes the leaderboard to the previous days results
     *
     * @param event button press
     */
    @FXML
    void backButtonPress(ActionEvent event) {
        //sets time
        String dateSet = dateObj.minusDays(1).format(formatter);
        dateObj = dateObj.minusDays(1);
        date.setText(dateSet);
        String[] scores = user.getTopFiveLeaderboard(dateSet);
        //If a value is null, replace it with nothing
        for (int x = 0; x < scores.length; x++) {
            if (scores[x] == null) {
                scores[x] = "";
            }
        }
        //set scores
        firstPlace.setText("First: " + scores[0] + " " + scores[1]);
        secondPlace.setText("Second: " + scores[2] + " " + scores[3]);
        thirdPlace.setText("Third: " + scores[4] + " " + scores[5]);
        fourthPlace.setText("Fourth: " + scores[6] + " " + scores[7]);
        fifthPlace.setText("Fifth: " + scores[8] + " " + scores[9]);
        userScore.setText("Your Daily Score: " + user.getUserDailyScore(user.getName(), dateSet));

    }

    /**
     * changes the leaderboard to the next days results
     *
     * @param event button press
     */
    @FXML
    void forwardButtonPress(ActionEvent event) {
        //sets time
        if (!dateObj.format(formatter).equals(LocalDate.now().format(formatter))) {
            String dateSet = dateObj.plusDays(1).format(formatter);
            dateObj = dateObj.plusDays(1);
            date.setText(dateSet);
            String[] scores = user.getTopFiveLeaderboard(dateSet);
            //if score is null, replace with nothing
            for (int x = 0; x < scores.length; x++) {
                if (scores[x] == null) {
                    scores[x] = "";
                }
            }
            //sets scores
            firstPlace.setText("First: " + scores[0] + " " + scores[1]);
            secondPlace.setText("Second: " + scores[2] + " " + scores[3]);
            thirdPlace.setText("Third: " + scores[4] + " " + scores[5]);
            fourthPlace.setText("Fourth: " + scores[6] + " " + scores[7]);
            fifthPlace.setText("Fifth: " + scores[8] + " " + scores[9]);
            userScore.setText("Your Daily Score: " + user.getUserDailyScore(user.getName(), dateSet));
        }
    }

    /**
     * search takes in a username and finds the extended stats of that player
     *
     * @param event button press
     * @throws IOException if javafx file cannot be found
     */
    @FXML
    void search(ActionEvent event) throws IOException {
        //Sets up new window
        PlayerLookup.setName(userSearchBox.getText().toLowerCase(Locale.ROOT));
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/PlayerLookup.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Player Lookup");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert fifthPlace != null : "fx:id=\"fifthPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert firstPlace != null : "fx:id=\"firstPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert forwardButton != null : "fx:id=\"forwardButton\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert fourthPlace != null : "fx:id=\"fourthPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert secondPlace != null : "fx:id=\"secondPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert thirdPlace != null : "fx:id=\"thirdPlace\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        assert userScore != null : "fx:id=\"userScore\" was not injected: check your FXML file 'Leaderboard.fxml'.";
        //set date
        String dateSet = dateObj.format(formatter);
        userScore.setText("Your Daily Score: " + user.getUserDailyScore(user.getName(), dateSet));
        date.setText(dateSet);
        Client c = new Client();
        String[] scores = c.getTopFiveLeaderboard(dateSet);
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


