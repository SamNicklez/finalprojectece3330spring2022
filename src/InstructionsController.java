import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class that controls the instructions window in the GUI
 *
 * @author Samuel Nicklaus
 * @see LeaderboardController
 * @see WelcomeController
 * @see WordleController
 */
public class InstructionsController {

    /**
     * is the image that contains the instructions
     */
    @FXML // fx:id="image"
    private ImageView image; // Value injected by FXMLLoader

    /**
     * method that sets the image variable to the location of the instructions
     *
     * @throws FileNotFoundException if the image is not found
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws FileNotFoundException {
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'Instructions.fxml'.";
        image.setImage(new Image("instructions.png"));
    }

}