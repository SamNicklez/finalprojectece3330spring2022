//Server class takes in user data from client and sends it off to get processed depending on what
//is received
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

/**
 * the Server class is used to retrieve data from the database through WordleModel and send it to clients that need it
 * @author Sam Maschmann
 * @see Client
 * @see ServerDriver
 * @see WordleModel
 */
public class Server extends JFrame {

    /**
     * The Socket to communicate with the clients
     */
    private static DatagramSocket connection;

    /**
     * The area to display messages on the server window
     */
    private final JTextArea displayArea;

    /**
     * An array of Strings containing the words to be used in the day's tournament
     */
    private static ArrayList<String> dailyWords;

    /**
     * The date the server is run
     */
    private static String today;

    /**
     * This is the main constructor for the server. It initializes the instance variables
     */
    public Server(){
        super("Wordle Server");
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(275,100);
        setVisible(true);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date data = new Date();
        today = formatter.format(data);
        //System.out.println(today);

        dailyWords = new ArrayList<>();
        dailyWords.addAll(WordleModel.grab5Words());


        //dailyWords is in the format word,difficulty,word,difficulty...

        try{
            connection = new DatagramSocket(	23575);
            displayMessage("Server is Running...\n\n");
        }
        catch(SocketException sockexe){
            sockexe.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * This method is constantly being run by the server to wait to receive packets from clients
     */
    public void waitForPackets(){
        while(true){
            try{
                byte[] data = new byte[100];
                DatagramPacket inPacket = new DatagramPacket(data, data.length);
                connection.receive(inPacket);
                displayMessage("Received message:\t\"" + new String(inPacket.getData()) + "\"\n");
                sendPacket(inPacket);
            }
            catch(IOException ioexe){
                ioexe.printStackTrace();
            }
        }
    }

    // Received packets should be in this format:   #,input
    // where # is a number telling which type of data is needed
    // List of each number with its corresponding data:
    // 1: Username Availability
    // 2: Checking Word
    // 3: Getting Daily Word
    // 4: Checking Word Validity
    // 5: Upload Score
    // 6: Get Top 5 Leaderboard
    // 7: Get User Score
    // 8: Get User's Stats
    // 9: Get Top 5 All Time Total Scores
    /**
     * This method takes a packet from a client, reads what is being requested, and sends the requested data to client from whence it came
     * @param inPacket A packet from a client in this format: "#,input" where # is a number telling which type of data is needed, and input is the given data from the client
     * @throws IOException
     */
    public void sendPacket(DatagramPacket inPacket) throws IOException {
        //Identify what the packet is for (ie: sending a guess, asking for scores, etc.) and send a packet back with required data
        String raw = new String(inPacket.getData());
        char bad = raw.charAt(raw.length()-1);
        String s = "";
        for(int i=0; i<raw.length(); i++){
            if(raw.charAt(i) != bad){
                s += raw.charAt(i);
            }
        }
        String[] dataArray = s.split(",");

        //1,username
        if(dataArray[0].equals("1")){
            checkUserAvail(dataArray, inPacket);
        }

        //2,guess,round
        else if(dataArray[0].equals("2")){
            checkWord(dataArray, inPacket);
        }

        //3,round
        else if(dataArray[0].equals("3")){
            getDailyWord(dataArray, inPacket);
        }

        //4,word
        else if(dataArray[0].equals("4")){
            checkWordValid(dataArray, inPacket);
        }

        //5,username,guesses
        else if(dataArray[0].equals("5")){
            uploadScore(dataArray);
        }

        //6,date
        else if(dataArray[0].equals("6")){
            getTopFiveLeaderboard(dataArray,inPacket);
        }

        //7,user,date
        else if(dataArray[0].equals("7")){
            getUserScore(dataArray, inPacket);
        }

        //8, user
        else if(dataArray[0].equals("8")){
            getUserStats(dataArray, inPacket);
        }

        //9
        else if(dataArray[0].equals("9")){
            getTopFiveAllTime(dataArray, inPacket);
        }
    }

    /**
     * checkUserAvail takes a String array that contains a username and check with the database to see if the player has played in the tournament today.
     * It sends back a packet with "1" if the user is eligible, and "0" if not
     * @param dataArray a String array in the form of [1,username] where username is the username
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void checkUserAvail(String[] dataArray, DatagramPacket inPacket) throws IOException {
        String user = "";
        for(int i=1; i<dataArray.length; i++){
            user += dataArray[i];
        }
        String avail = "0";

        if(WordleModel.canPlayWordle(user, today)){
            avail = "1";
        }

        byte[] outData = avail.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }

    /**
     * checkWord takes a string array that contains a guess and a round number and checks how many letters are in the correct position, how many are in the incorrect position, and how many are not in the word at all
     * It sends back a packet with 5 ints varying between 0 and 2, where 2 is correct position, 1 exists in the word, but incorrect position, and 0 is not in the word
     * @param dataArray a String array in the form of [2,guess,round] where guess is a string to be tested against a daily word, and round is a number telling which word to test it against
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void checkWord(String[] dataArray, DatagramPacket inPacket) throws IOException {
        TreeMap<Character, Integer> map = new TreeMap<>();
        String[] resultArray = new String[5];
        String guess = dataArray[1];
        String round = dataArray[2];
        int[] greenLetters = new int[5];
        for(int i=0; i<5; i++){
            if(guess.charAt(i) == dailyWords.get(Integer.parseInt(round)*2-2).charAt(i)){
                resultArray[i] = "2";
                greenLetters[i] = 1;
            }
            if(!map.containsKey(dailyWords.get(Integer.parseInt(round)*2-2).charAt(i))){
                map.put(dailyWords.get(Integer.parseInt(round)*2-2).charAt(i), 1);
            }
            else{
                map.put(dailyWords.get(Integer.parseInt(round)*2-2).charAt(i), map.get(dailyWords.get(Integer.parseInt(round)*2-2).charAt(i))+1);
            }
        }
        char[] chars = dailyWords.get(Integer.parseInt(round)*2-2).toCharArray();

        for(int i=0; i<10; i++){
            boolean contains = false;
            for (char aChar : chars) {
                if (aChar == guess.charAt(i%5)) {
                    contains = true;
                }
            }
            if(contains && greenLetters[i%5] == 0 && map.get(guess.charAt(i%5))>0){
                resultArray[i%5] = "1";
            }
            else if(greenLetters[i%5] == 1){
                map.put(chars[i%5], map.get(chars[i%5])-1);
            }
            else if (greenLetters[i%5] == 0){
                resultArray[i%5] = "0";
            }
        }
        String resultString = "";
        for (String value : resultArray) {
            resultString += value;
        }
        byte[] outData = resultString.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }

    /**
     * getDailyWord sends a packet containing a requested daily word
     * @param dataArray a string array in the form [3,round] where round tells which daily word is being requested
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void getDailyWord(String[] dataArray, DatagramPacket inPacket) throws IOException {
        String outString = dailyWords.get(Integer.parseInt(dataArray[1])*2-2);
        byte[] outData = outString.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }

    /**
     * checkWordValid takes a string array containing a 5-length string and checks with the database to make sure that it is a valid word
     * @param dataArray a string array in the form of [4,word] where word is a string to be tested to be a word
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void checkWordValid(String[] dataArray, DatagramPacket inPacket) throws IOException {
        String outString;
        if(WordleModel.checkWordValidity(dataArray[1])){
            outString = "1";
        }
        else{
            outString = "0";
        }
        byte[] outData = outString.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }

    /**
     * uploadScore takes a string array containing a username and a series of numbers corresponding to how many guesses each daily word took
     * It then calculates a score and uploads it to the database
     * @param dataArray a string array in the form [5,username,guesses] where username is the username and guesses is a string of five ints ranging from 1-7
     */
    public static void uploadScore(String[] dataArray){
        int score = 0;
        String user = dataArray[1];
        String[] guesses = dataArray[2].split("");
        boolean winStreak;

        for(int i=1; i<guesses.length+1; i++){
            int base = 7 - Integer.parseInt(guesses[i-1]);
            if(Double.parseDouble(dailyWords.get(2*i-1)) < 0){
                score += Math.round(Math.abs((Double.parseDouble(dailyWords.get(2*i-1))+1))*base);
            }
            else if(Double.parseDouble(dailyWords.get(2*i-1)) > 0){
                score += Math.round(Math.abs((Double.parseDouble(dailyWords.get(2*i-1))+2))*base);
            }
            if(score==0){
                winStreak = false;
            }
            else{
                winStreak = true;
            }

            WordleModel.calculateUserStats(WordleModel.accessUser(user), score, winStreak);
        }
        WordleModel.addDailyScore(user, score, today);
    }

    /**
     * getTopFiveLeaderboard takes a string array containing a date and checks with the database to send back a packet containing the top 5 users with their scores
     * @param dataArray a string array in the format [6,date] where date is the date that the leaderboard is being requested
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void getTopFiveLeaderboard(String[] dataArray, DatagramPacket inPacket) throws IOException {
        ArrayList<String> leaderboard = new ArrayList<>();
        leaderboard.addAll(WordleModel.getTopFiveLeaderboard(dataArray[1]));
        String outString = "";
        for(int i=0; i<leaderboard.size(); i++){
            outString += leaderboard.get(i) + ",";
        }
        byte[] outData = outString.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }

    /**
     * getUserScore takes a String array containing a username and a date and sends a packet with the score for that user on that date
     * @param dataArray a string array in the format [7,username,date], where username is the user and date is the date
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void getUserScore(String[] dataArray, DatagramPacket inPacket) throws  IOException {
        ArrayList<String> scoreArray = new ArrayList<>();
        String user = dataArray[1];
        String date = dataArray[2];
        scoreArray.addAll(WordleModel.getUserLeaderboard(user,date));
        try{
            String outString = scoreArray.get(1);
            byte[] outData = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
            connection.send(outPacket);
        }
        catch(Exception e){
            String outString = "";
            byte[] outData = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
            connection.send(outPacket);
        }
    }

    /**
     * getUserStats takes a string array containing a username and sends in a packet some statistics, including total score, total rounds played, and winning streak
     * @param dataArray a string array in the form [8,user] where user is a username
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void getUserStats(String[] dataArray, DatagramPacket inPacket) throws IOException {
        if(WordleModel.checkForUser(dataArray[1])){
            ArrayList<String> stats = new ArrayList<>();
            stats.addAll(WordleModel.accessUser(dataArray[1]));
            String outString = "1,";
            for(int i=1; i<stats.size(); i++){
                outString += stats.get(i) + ",";
            }
            byte[] outData = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
            connection.send(outPacket);
        }
        else{
            String outString = "0";
            byte[] outData = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
            connection.send(outPacket);
        }
    }

    /**
     * getTopFiveAllTime sends in a packet the top five all time scores and the players that earned them from the database
     * @param dataArray a string array in the form [9]
     * @param inPacket the packet that came from the place where the data will be sent
     * @throws IOException
     */
    public static void getTopFiveAllTime(String[] dataArray, DatagramPacket inPacket) throws IOException{
        ArrayList<String> leaderboard = new ArrayList<>(WordleModel.getTopFiveAllTimeLeaderboard());
        String outString = "";
        for(int i=0; i<leaderboard.size(); i++){
            outString += leaderboard.get(i) + ",";
        }
        byte[] outData = outString.getBytes();
        DatagramPacket outPacket = new DatagramPacket(outData, outData.length, inPacket.getAddress(), inPacket.getPort());
        connection.send(outPacket);
    }
    /**
     * This method displays a given message to the display area on the server window
     * @param message a String to be displayed on the window
     */
    private void displayMessage(final String message){
        SwingUtilities.invokeLater(new Runnable() {
                    public void run()
                    {
                        displayArea.append(message);
                    }
                }
        );
    }
}
