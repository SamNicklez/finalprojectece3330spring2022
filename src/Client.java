//Client is used to send data over to server
//This class will have methods which when called by the MainGUIController will send appropriate
//data over to the server


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * The Client class is used to communicate with the server in order to get information from the data base
 * @author Sam Maschmann
 * @see Server
 * @see ClientDriver
 */
public class Client {

    private static String name;

    private static int[] array = new int[5];

    private int counter = 0;

    /**
     * The Socket to communicate with the server
     */
    private DatagramSocket connection;

    /**
     * This is the main constructor for Client. It creates the DatagramSocket to communicate with the server
     */
    Client(){
        try{
            connection = new DatagramSocket();
        }
        catch(SocketException sockexe){
            sockexe.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * method that returns true if the username is not taken OR it is a returning player that
     * hasn't played a series today
     * @param userName is the user inputted name for the user
     * @return true if the user can play or false if the user cannot
     */
    public boolean checkNameAvailability(String userName){
        //this method sends string packet to server to check if username is available and returns true/false
        //depending on availability
        char avail = ' ';
        try{
            String outString = "1," + userName;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[100];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            avail = new String(inPacket.getData()).charAt(0);
        }
        catch(IOException ioexe){
            ioexe.printStackTrace();
        }
        if(avail == '1'){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * method that checks a word to see if it matches the daily word for a given round
     * @param guess String containing the word to test against the daily word
     * @param round int containing the round number to be tested
     * @return an array of ints, with each int corresponding to the status of a letter; 0 means not in the word; 1 means in the word but not correct position; 2 means correct position
     */
    public int[] checkWord(String guess, int round){
        int[] result = new int[5];
        try{
            String outString = "2," + guess + "," + round;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[100];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String resultString = new String(inPacket.getData());

            for(int i=0; i<resultString.length(); i++){
                if(resultString.charAt(i) == '0'){
                    result[i] = 0;
                }
                else if(resultString.charAt(i) == '1'){
                    result[i] = 1;
                }
                else if(resultString.charAt(i) == '2'){
                    result[i] = 2;
                }
            }
        }
        catch(IOException ioexe){
            ioexe.printStackTrace();
        }

        return result;
    }

    /**
     * This method gets the daily word for a given round
     * @param round The round of the daily word that is being requested
     * @return the daily word of the given round
     */
    public String getWord(int round){
        String dailyWord = "";
        try{
            String outString = "3," + round;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[100];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String rawString = new String(inPacket.getData());
            for(int i=0; i<5; i++){
                dailyWord += rawString.charAt(i);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return dailyWord;
    }

    /**
     * Checks whether the given string is a five-letter word
     * @param word a string of five letters to be tested
     * @return true if word is a valid word, false otherwise
     */
    public boolean validWord(String word){
        boolean valid = false;
        try{
            String outString = "4," + word;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[50];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String rawString = new String(inPacket.getData());
            if(rawString.charAt(0) == '1'){
                valid = true;
            }
        }
        catch(IOException ioexe){
            ioexe.printStackTrace();
        }
        return valid;
    }

    /**
     * upload scores takes a username and an array of the number of guesses taken per word and uploads them to the database
     * @param username the username of the player
     * @param guesses an in array containing the number of guesses per word
     */
    public void uploadScores(String username, int[] guesses){
        try{
            String outString = "5," + username + ",";
            for(int i=0; i<guesses.length; i++){
                outString += guesses[i];
            }
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);
        }
        catch(IOException ioexe){
            ioexe.printStackTrace();
        }
    }

    /**
     * getTopFiveLeaderboard takes a string that contain a date and returns a string array containing the top 5 scores and users for that date
     * @param date the date to find the leaderboard for
     * @return a string array in the format user,score,user,score... where user is the username and score is their score
     */
    public String[] getTopFiveLeaderboard(String date){
        String[] leaderboard = new String[10];
        try{
            String outString = "6," + date;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[100];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String rawString = new String(inPacket.getData());
            String[] s = rawString.split(",");
            int index = 0;
            for(int i=0; i<s.length; i++){
                if(i%3 != 2){
                    leaderboard[index] = s[i];
                    index++;
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return leaderboard;
    }

    /**
     * getUserScore takes two strings, one for the username and one for the date, and returns the score of that user on that date
     * @param user the username of the player
     * @param date the date of the score to be fetched
     * @return a string containing the score of the user on the given date
     */
    public String getUserDailyScore(String user, String date){
        String score = "";
        try{
            String outString = "7," + user + "," + date;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[50];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String raw = new String(inPacket.getData());

            char bad = raw.charAt(raw.length()-1);
            String s = "";
            for(int i=0; i<raw.length(); i++){
                if(raw.charAt(i) != bad){
                    s += raw.charAt(i);
                }
            }

            return s;

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return score;
    }

    /**
     * getUserTotalStats takes a username as a string and checks in the database to return an array containing statistics of that user including total score, total rounds played, and winning streak
     * @param user a string containing the name of the player
     * @return null if user is not in database; otherwise: a string array in the form [totalScore,totalRoundsPlayed,winStreak] where totalScore is the user's total collective score, totalRoundsPlayed is how many rounds the user has played, and winStreak is the user's win streak
     */
    public String[] getUserTotalStats(String user){
        String[] stats = new String[3];
        try{
            String outString = "8," + user;
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[50];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String raw = new String(inPacket.getData());

            char bad = raw.charAt(raw.length()-1);
            String s = "";
            for(int i=0; i<raw.length(); i++){
                if(raw.charAt(i) != bad){
                    s += raw.charAt(i);
                }
            }
            String[] strArray = s.split(",");
            if(strArray[0].equals("0")){
                return null;
            }
            else{
                for(int i=1; i<strArray.length-1; i++){
                    stats[i-1] = strArray[i];
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return stats;
    }

    /**
     * getTopFiveAllTime returns a string array containing the top five users with their total scores
     * @return a string array in the form [user,score,user,score...] where user is a top five user and score is their total score
     */
    public String[] getTopFiveAllTime(){
        String[] leaderboard = new String[10];
        try{
            String outString = "9";
            byte[] data = outString.getBytes();
            DatagramPacket outPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 	23575);
            connection.send(outPacket);

            byte[] output = new byte[100];
            DatagramPacket inPacket = new DatagramPacket(output, output.length);
            connection.receive(inPacket);
            String raw = new String(inPacket.getData());

            char bad = raw.charAt(raw.length()-1);
            String s = "";
            for(int i=0; i<raw.length(); i++){
                if(raw.charAt(i) != bad){
                    s += raw.charAt(i);
                }
            }
            String[] strArray = s.split(",");
            System.arraycopy(strArray, 0, leaderboard, 0, strArray.length);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return leaderboard;
    }


    public void setName(String x){
        name = x;
        //System.out.println("NAME: " + name);
    }
    public void sendArr(){
       //System.out.println("UPLOADING SCORES : " + name);
        this.uploadScores(name,array);
    }
    public void setArray(int x){
        //System.out.println("SAVING ARRAY VALUE " + x);
        array[counter] = x;
    }
    public String getName(){
        return name;
    }


}
