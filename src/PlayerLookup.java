import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * class PlayerLookup is a controller that displays stats based on a username input
 *
 * @author Samuel Nicklaus
 */
public class PlayerLookup extends LeaderboardController {

    /**
     * is the name of the player we want to look up
     */
    private static String playerNameLook;

    /**
     * is the client object
     */
    private final Client c = user;

    /**
     * is the textbox that displays the current win streak
     */
    @FXML // fx:id="currentWinStreak"
    private Text currentWinStreak; // Value injected by FXMLLoader

    /**
     * is the textbox that displays the name of the user
     */
    @FXML // fx:id="name"
    private Text name; // Value injected by FXMLLoader


    /**
     * is the textbox that displays the total rounds played by the user
     */
    @FXML // fx:id="totalRoundsPlayed"
    private Text totalRoundsPlayed; // Value injected by FXMLLoader

    /**
     * is the textbox that displays the totalscore of the user of all time
     */
    @FXML // fx:id="totalScore"
    private Text totalScore; // Value injected by FXMLLoader


    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert currentWinStreak != null : "fx:id=\"currentWinStreak\" was not injected: check your FXML file 'PlayerLookup.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'PlayerLookup.fxml'.";
        assert totalRoundsPlayed != null : "fx:id=\"totalRoundsPlayed\" was not injected: check your FXML file 'PlayerLookup.fxml'.";
        assert totalScore != null : "fx:id=\"totalScore\" was not injected: check your FXML file 'PlayerLookup.fxml'.";
        //sets name
        name.setText("Name: " + playerNameLook);
        //looks up name to get stats
        String[] arr = c.getUserTotalStats(playerNameLook);
        //if there is a user
        if (arr != null) {
            totalScore.setText("Total Score: " + arr[0]);
            totalRoundsPlayed.setText("Total Rounds Played: " + arr[1]);
            //if no win streak
            if (arr[2] == null) {
                currentWinStreak.setText("CURRENT WIN STREAK: 0");
            } else {
                currentWinStreak.setText("CURRENT WIN STREAK: " + arr[2]);
            }
            //if there is no user found
        } else {
            name.setText("NAME: USER NOT FOUND");
            currentWinStreak.setText("CURRENT WIN STREAK: N/A");
            totalScore.setText("Total Score: N/A");
            totalRoundsPlayed.setText("Total Rounds Played: N/A");
        }
    }

    /**
     * method set name sets the name of the player you want to look up
     *
     * @param s is the string of the player you want to look up
     */
    public static void setName(String s) {
        playerNameLook = s;
    }

}

