import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class wordle controller is used to control the main functions of the game wordle
 *
 * @author Samuel Nicklaus
 * @see LeaderboardController
 * @see WelcomeController
 * @see PopupController
 * @see InstructionsController
 */
public class WordleController extends WelcomeController {

    /**
     * Linked-list that contains the 5 letter characters that form the word the user guessed
     */
    private final LinkedList<String> answer = new LinkedList<String>();

    /**
     * Hashmap that is used for the wordle grid that determines where each letter goes when typed
     */
    private final HashMap<Integer, Text> textMap = new HashMap<Integer, Text>();

    /**
     * Hashmap that is used for the wordle grid that determines which circle gets changed to what color
     */
    private final HashMap<Integer, Circle> circleMap = new HashMap<Integer, Circle>();

    /**
     * Hashmap that keeps track of all the keyboard buttons
     */
    private final HashMap<Character, Button> buttonMap = new HashMap<>();

    /**
     * button that contains the letter a
     */
    @FXML
    private Button a;

    /**
     * button that contains the letter b
     */
    @FXML
    private Button b;

    /**
     * button that contains the letter c
     */
    @FXML
    private Button c;

    /**
     * button that contains the letter d
     */
    @FXML
    private Button d;

    /**
     * button that contains the letter e
     */
    @FXML
    private Button e;

    /**
     * button that contains the letter f
     */
    @FXML
    private Button f;

    /**
     * button that contains the letter g
     */
    @FXML
    private Button g;

    /**
     * button that contains the letter h
     */
    @FXML
    private Button h;

    /**
     * button that contains the letter i
     */
    @FXML
    private Button i;

    /**
     * button that contains the letter j
     */
    @FXML
    private Button j;

    /**
     * button that contains the letter k
     */
    @FXML
    private Button k;

    /**
     * button that contains the letter l
     */
    @FXML
    private Button l;

    /**
     * button that contains the letter m
     */
    @FXML
    private Button m;

    /**
     * button that contains the letter n
     */
    @FXML
    private Button n;

    /**
     * button that contains the letter o
     */
    @FXML
    private Button o;

    /**
     * button that contains the letter p
     */
    @FXML
    private Button p;

    /**
     * button that contains the letter q
     */
    @FXML
    private Button q;

    /**
     * button that contains the letter r
     */
    @FXML
    private Button r;

    /**
     * button that contains the letter s
     */
    @FXML
    private Button s;

    /**
     * button that contains the letter t
     */
    @FXML
    private Button t;

    /**
     * button that contains the letter u
     */
    @FXML
    private Button u;

    /**
     * button that contains the letter v
     */
    @FXML
    private Button v;

    /**
     * button that contains the letter w
     */
    @FXML
    private Button w;

    /**
     * button that contains the letter x
     */
    @FXML
    private Button x;

    /**
     * button that contains the letter y
     */
    @FXML
    private Button y;

    /**
     * button that contains the letter z
     */
    @FXML
    private Button z;

    /**
     * integer index keeps count of which of the 30 spots to place the user inputted letter guess
     */
    private int index = 0;

    /**
     * integer counter keeps count of what letter guess the user is on out of 5
     */
    private int counter = 0;

    /**
     * integer that represents the row to add text to
     */
    private int rowNum = 0;

    /**
     * integer that represents the round the user is on
     */
    private static int roundNum = 0;
    /**
     * Gridpane background is the background of the program
     */

    /**
     * integer that keeps track of # of attempts per round
     */
    private static int attempt = 0;

    /**
     * background of GUI, needed to change color
     */
    @FXML
    private GridPane background;

    /**
     * Text that only displays if word is not in word list
     */
    @FXML
    private Text notInWordList;

    /**
     * Space 1 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle1;

    /**
     * client that is used to communicate with teh server
     */
    private final Client user = client;

    /**
     * Space 10 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle10;

    /**
     * Space 11 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle11;

    /**
     * Space 12 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle12;

    /**
     * Space 13 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle13;

    /**
     * Space 14 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle14;

    /**
     * Space 15 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle15;

    /**
     * Space 16 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle16;

    /**
     * Space 17 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle17;

    /**
     * Space 18 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle18;

    /**
     * Space 19 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle19;

    /**
     * Space 2 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle2;

    /**
     * Space 20 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle20;

    /**
     * Space 21 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle21;

    /**
     * Space 22 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle22;

    /**
     * Space 23 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle23;

    /**
     * Space 24 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle24;

    /**
     * Space 25 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle25;

    /**
     * Space 26 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle26;

    /**
     * Space 27 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle27;

    /**
     * Space 28 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle28;

    /**
     * Space 29 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle29;

    /**
     * Space 3 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle3;

    /**
     * Space 30 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle30;

    /**
     * Space 4 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle4;

    /**
     * Space 5 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle5;

    /**
     * Space 6 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle6;

    /**
     * Space 7 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle7;

    /**
     * Space 8 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle8;

    /**
     * Space 9 of 30 of the matrix grid where the background circle is
     */
    @FXML
    private Circle circle9;

    /**
     * Space 1 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space1;

    /**
     * Space 10 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space10;

    /**
     * Space 11 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space11;

    /**
     * Space 12 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space12;

    /**
     * Space 13 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space13;

    /**
     * Space 14 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space14;

    /**
     * Space 15 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space15;

    /**
     * Space 16 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space16;

    /**
     * Space 17 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space17;

    /**
     * Space 18 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space18;

    /**
     * Space 19 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space19;

    /**
     * Space 2 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space2;

    /**
     * Space 20 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space20;

    /**
     * Space 21 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space21;

    /**
     * Space 22 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space22;

    /**
     * Space 23 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space23;

    /**
     * Space 24 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space24;

    /**
     * Space 25 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space25;

    /**
     * Space 26 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space26;

    /**
     * Space 27 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space27;

    /**
     * Space 28 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space28;

    /**
     * Space 29 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space29;

    /**
     * Space 3 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space3;

    /**
     * Space 30 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space30;

    /**
     * Space 4 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space4;

    /**
     * Space 5 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space5;

    /**
     * Space 6 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space6;

    /**
     * Space 7 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space7;

    /**
     * Space 8 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space8;

    /**
     * Space 9 of 30 of the matrix grid where the user inputted letters go
     */
    @FXML
    private Text space9;

    /**
     * is used to submit the 5 user characters when pressed
     */
    @FXML // fx:id="enter"
    private Button enter; // Value injected by FXMLLoader

    /**
     * is used to undo that last user button press
     */
    @FXML // fx:id="undo"
    private Button undo; // Value injected by FXMLLoader

    /**
     * method that takes in the user button press and adds it to the wordle selection area
     *
     * @param event button press
     */
    @FXML
    void addLetterButton(ActionEvent event) {
        notInWordList.setText("");
        //puts letter into string
        String userIn = (event.getSource().toString().substring(event.getSource().toString().length() - 2, event.getSource().toString().length() - 1)).toUpperCase(Locale.ROOT);
//      System.out.println(testText.getLayoutX() + " " + testText.getLayoutY());
        if (counter < 5) { //if the user still has letters to fill in
            textMap.get(index).setText(userIn); //sets index spot with letter in GUI
            index++;
            counter++;
            answer.add(userIn.toLowerCase(Locale.ROOT));
        }
    }

    /**
     * is a method that is used to submit the 5-letter word to the server
     *
     * @param event button press
     */
    @FXML
    void enterButtonPress(ActionEvent event) throws IOException {
//        System.out.println(user.getName());
        if (counter == 5) {
//            System.out.println(roundNum);
//            System.out.println(attempt);
            int correct = 0;
            StringBuilder packetString = new StringBuilder();
            for (String x : answer.subList(0, 5)) { //loops through list and appends characters to a string
                packetString.append(x);
            }
            if (user.validWord(packetString.toString())) {
                attempt++;
                int[] update;
                update = user.checkWord(packetString.toString(), roundNum + 1);
                counter = 1;
                int letterIndex = 0;
                //loops through array returned by server to see what the user got right or wrong
                for (int x : update) {
                    //if letter is not in the word and it's the first attempt
                    if (rowNum == 0) {
                        //if
                        if (x == 0) {
                            circleMap.get(counter - 1).setFill(Color.GRAY);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: gray;");
                            letterIndex++;
                            //if letter is in the word and in the correct position
                        } else if (x == 1) {
                            circleMap.get(counter - 1).setFill(Color.GOLD);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: gold;");
                            letterIndex++;
                        } else {
                            //if letter is in the word but not in the correct position
                            correct++;
                            circleMap.get(counter - 1).setFill(Color.GREEN);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: green;");
                            letterIndex++;
                        }
                        counter++;
                        //if it's not the first attempt
                    } else {
                        //if letter is not in the word
                        if (x == 0) {
                            circleMap.get(counter + rowNum - 1).setFill(Color.GRAY);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: gray;");
                            letterIndex++;
                            //if letter is in the word and the correct position
                        } else if (x == 1) {
                            circleMap.get(counter + rowNum - 1).setFill(Color.GOLD);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: gold;");
                            letterIndex++;
                            //if letter is in the word but not the correct position
                        } else {
                            correct++;
                            circleMap.get(counter + rowNum - 1).setFill(Color.GREEN);
                            buttonMap.get(packetString.charAt(letterIndex)).setStyle("-fx-base: green;");
                            letterIndex++;
                        }
                        counter++;
                    }
                }
                rowNum += 5; //adds now number so next time enter is pressed the next row of chars are editied
                //if answer is correct
                if (correct == 5) {
                    //if it is not the last round
                    if (roundNum != 4) {
                        roundNum++; //loads next word in
                        //reset wordle board
                        PopupController.setTextFunc(attempt, false, packetString.toString(), true);
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/popup.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        user.setArray(attempt);
                        //adds the # of attempts used for that round to array
                        //this will be sent to server later
//                        System.out.println("ATTEMPT: " + attempt + "ON ROUND: " + roundNum);
                        ;
                        //if it is the last round
                    } else {
                        roundNum++; //loads next word in
                        //loads popup to send user home
                        PopupController.setTextFunc(attempt, true, packetString.toString(), true);
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/popup.fxml")));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                        //adds the # of attempts used for that round to array
                        //this will be sent to server later
//                        System.out.println("ATTEMPT: " + attempt + "ON ROUND: " + roundNum);
                        user.setArray(attempt);
                        //INSERT SCORE SENDING HERE
                        user.sendArr();
                    }
                    //resets all values used to change color of circles back to zero upon reset
                    attempt = 0;
                    rowNum = 0;
                    index = 0;
                }
                //if you are on the last round and are out of attempts or
                else if (roundNum == 4 && attempt == 6) {
                    //sets text in popup to correct word value
                    PopupController.setTextFunc(attempt, true, user.getWord(roundNum + 1), false);
                    roundNum++;
                    attempt++;
                    //Sets up new window to display instructions
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/popup.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
//                    System.out.println("ATTEMPT: " + attempt + " ON ROUND: " + roundNum);
                    user.setArray(attempt);
                    //SEND SCORE SENDING HERE
                    user.sendArr();
                    attempt = 0;
                    rowNum = 0;
                    index = 0;
                    //if you are out of attempts but not on the last round
                } else if (attempt == 6) {
                    PopupController.setTextFunc(attempt, false, user.getWord(roundNum + 1), false);
                    roundNum++;
                    attempt++;
                    //Sets up new window to display instructions
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/popup.fxml")));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
//                    System.out.println("ATTEMPT: " + attempt + " ON ROUND: " + roundNum);
                    user.setArray(attempt);
                    stage.show();
                    attempt = 0;
                    rowNum = 0;
                    index = 0;
                }
                counter = 0;
                //clears answer for next round
                answer.clear();
                //if word is not valid
            } else {
                notInWordList.setText("Not in word List");
            }
        }

    }

    /**
     * method that takes in the user keyboard input and adds it to the wordle selection area
     *
     * @param event keyboard press
     */
    @FXML
    void keyboardStroke(KeyEvent event) {
        //sets text that tells user word is not in the system to zero so they can
        //keep typing
        notInWordList.setText("");
//      System.out.println(event.getCode());
        String userIn = (event.getCode().toString()).toUpperCase(Locale.ROOT);
        //if user input is a single letter of the alphabet and the user has less than 5 letters
        if (userIn.matches("^[a-zA-Z]*$") && (counter < 5) && (userIn.length() == 1)) {
            counter++;
            textMap.get(index).setText(userIn);
            index++;
            answer.add(userIn.toLowerCase(Locale.ROOT));
            //if user backspaces, remove previous input
        } else if (userIn.equals("BACK_SPACE")) {
            if (counter > 0 && index > 0) {
                counter--;
                index--;
                textMap.get(index).setText("");
                answer.remove(counter);
            }
        }
    }

    /**
     * is a method that undoes the previous user input
     *
     * @param event button press
     */
    @FXML
    void undoButtonPress(ActionEvent event) {
        //if user is not at beginning of word, remove a letter
        if (counter >= 0 && index > 0) {
            index--;
            counter--;
            textMap.get(index).setText("");
            answer.remove(counter);
        } else {
            counter = 0;
        }
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
        assert enter != null : "fx:id=\"enter\" was not injected: check your FXML file 'WordleMain.fxml'.";
        assert undo != null : "fx:id=\"undo\" was not injected: check your FXML file 'WordleMain.fxml'.";
        assert background != null : "fx:id=\"background\" was not injected: check your FXML file 'WordleMain.fxml'.";
        background.setBackground(new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        textMap.put(0, space1);
        textMap.put(1, space2);
        textMap.put(2, space3);
        textMap.put(3, space4);
        textMap.put(4, space5);
        textMap.put(5, space6);
        textMap.put(6, space7);
        textMap.put(7, space8);
        textMap.put(8, space9);
        textMap.put(9, space10);
        textMap.put(10, space11);
        textMap.put(11, space12);
        textMap.put(12, space13);
        textMap.put(13, space14);
        textMap.put(14, space15);
        textMap.put(15, space16);
        textMap.put(16, space17);
        textMap.put(17, space18);
        textMap.put(18, space19);
        textMap.put(19, space20);
        textMap.put(20, space21);
        textMap.put(21, space22);
        textMap.put(22, space23);
        textMap.put(23, space24);
        textMap.put(24, space25);
        textMap.put(25, space26);
        textMap.put(26, space27);
        textMap.put(27, space28);
        textMap.put(28, space29);
        textMap.put(29, space30);

        circleMap.put(0, circle1);
        circleMap.put(1, circle2);
        circleMap.put(2, circle3);
        circleMap.put(3, circle4);
        circleMap.put(4, circle5);
        circleMap.put(5, circle6);
        circleMap.put(6, circle7);
        circleMap.put(7, circle8);
        circleMap.put(8, circle9);
        circleMap.put(9, circle10);
        circleMap.put(10, circle11);
        circleMap.put(11, circle12);
        circleMap.put(12, circle13);
        circleMap.put(13, circle14);
        circleMap.put(14, circle15);
        circleMap.put(15, circle16);
        circleMap.put(16, circle17);
        circleMap.put(17, circle18);
        circleMap.put(18, circle19);
        circleMap.put(19, circle20);
        circleMap.put(20, circle21);
        circleMap.put(21, circle22);
        circleMap.put(22, circle23);
        circleMap.put(23, circle24);
        circleMap.put(24, circle25);
        circleMap.put(25, circle26);
        circleMap.put(26, circle27);
        circleMap.put(27, circle28);
        circleMap.put(28, circle29);
        circleMap.put(29, circle30);

        notInWordList.setText("");

        buttonMap.put('a', a);
        buttonMap.put('b', b);
        buttonMap.put('c', c);
        buttonMap.put('d', d);
        buttonMap.put('e', e);
        buttonMap.put('f', f);
        buttonMap.put('g', g);
        buttonMap.put('h', h);
        buttonMap.put('i', i);
        buttonMap.put('j', j);
        buttonMap.put('k', k);
        buttonMap.put('l', l);
        buttonMap.put('m', m);
        buttonMap.put('n', n);
        buttonMap.put('o', o);
        buttonMap.put('p', p);
        buttonMap.put('q', q);
        buttonMap.put('r', r);
        buttonMap.put('s', s);
        buttonMap.put('t', t);
        buttonMap.put('u', u);
        buttonMap.put('v', v);
        buttonMap.put('w', w);
        buttonMap.put('x', x);
        buttonMap.put('y', y);
        buttonMap.put('z', z);
    }

}
