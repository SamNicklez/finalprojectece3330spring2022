import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * The WordleModel class contains methods to access and update tables in an SQL database
 * that contains words and user information
 *
 * @author Nathaniel Narunatvanich
 */
public class WordleModel {
    // database URL
    static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/swd_db005";

    /**
     * Method that appends all the values of the 3 easy words and 2 hard words pulled from the database to one List
     * object to return to the Server object. This method also removes the pulled words from the database to prevent
     * repeated words in the Wordle Tournament.
     *
     * @return list of the 5 words pulled from the database and their difficulties
     */
    public static List<String> grab5Words() {
        List<String> threeEasyWords = grab3EasyWords(); // creates list to store 3 easy words and their difficulties
        List<String> twoHardWords = grab2HardWords(); // creates list to store 2 hard words and their difficulties
        List<String> fiveWords = new ArrayList<>(); // creates list to append both prior lists' information into one

        // loops through the list of 3 easy words to append to new list
        for (String easyWord : threeEasyWords) {
            fiveWords.add(easyWord);
        }
        // loops through the list of 2 hard words to append to new list
        for (String hardWord : twoHardWords) {
            fiveWords.add(hardWord);
        }

        // DELETE METHOD COMMENTED OUT DON'T ACTIVATE THE METHODDDDDDD D:
        // removes the words selected from the SQL table to prevent reuse of words in the future
//        for (int i = 0; i <= fiveWords.size() - 2; i += 2)
//        {
//            deleteWordsFromDatabase(fiveWords.get(i));
//        }

        // returns the list of 5 words and their difficulties
        return fiveWords;
    } // end method grab5Words

    /**
     * Method that creates a list to store 3 words and their difficulties pulled from the SQL table
     *
     * @return List object that alternates between the words and their difficulties
     */
    public static List<String> grab3EasyWords() {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");

            // create Statement for querying database
            statement = connection.createStatement();

            // query database
            resultSet = statement.executeQuery("SELECT word, difficulty FROM wordBank WHERE difficulty BETWEEN -1 AND 0 ORDER BY RAND() LIMIT 3");

            // create HashMap to store pulled words and their difficulties
            HashMap<String, BigDecimal> wordList = new HashMap<String, BigDecimal>();

            // create List of Strings to store each value of the HashMap
            List<String> list = new ArrayList<String>();

            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // generates 3 words of easy difficulty
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i += 2) {
                    String tempWord = "";
                    BigDecimal tempBig = BigDecimal.valueOf(0);
                    tempWord = (String) resultSet.getObject(i);
                    tempBig = (BigDecimal) resultSet.getObject(i + 1);
                    wordList.put(tempWord, tempBig);
                }
            } // end while

            // append HashMap values to List as Strings
            for (String i : wordList.keySet()) {
                list.add(i);
                list.add(wordList.get(i).toString());
            }

            // returns List of words and their difficulties
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method grab5Words

    /**
     * Method that creates a list to store 2 words and their difficulties pulled from the SQL table
     *
     * @return List object that alternates between the words and their difficulties
     */
    public static List<String> grab2HardWords() {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        // connect to database and query database
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");

            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT word, difficulty FROM wordBank WHERE difficulty BETWEEN 0 AND 2 ORDER BY RAND() LIMIT 2");

            HashMap<String, BigDecimal> wordList = new HashMap<String, BigDecimal>();
            List<String> list = new ArrayList<String>();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // generates 2 words of hard difficulty
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i += 2) {
                    String tempWord = "";
                    BigDecimal tempBig = BigDecimal.valueOf(0);

                    tempWord = (String) resultSet.getObject(i);
                    tempBig = (BigDecimal) resultSet.getObject(i + 1);
                    wordList.put(tempWord, tempBig);
                }
            } // end while

            // append the words and their difficulties to list
            for (String i : wordList.keySet()) {
                list.add(i);
                list.add(wordList.get(i).toString());
            }

            // returns List of words and their difficulties
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method grab5Words

    /**
     * Method that removes the entries of the 5 words pulled from the SQL table to ensure that words cannot be
     * reused on different days
     *
     * @param wordToDelete word to be deleted from the SQL table
     */
    public static void deleteWordsFromDatabase(String wordToDelete) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            //System.out.println("Deleting " + wordToDelete + "...");
            // execute query in database to delete the entry of specified word
            statement.executeUpdate("DELETE FROM wordBank WHERE word='" + wordToDelete + "'");
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure statement and connection are closed
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        }
    } // end method deleteWordsFromDatabase

    /**
     * Method that returns a List of entries in an SQL table with the highest dailyScore values for the specified date
     *
     * @param date a String of the current date "(MM/DD/YYYY)"
     * @return List object that alternates between userID, dailyScore, and date for the 5 entries
     * with the largest dailyScore values
     */
    public static List<String> getTopFiveLeaderboard(String date) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM dailyScores WHERE date='" + date + "' " +
                    "ORDER BY dailyScore DESC LIMIT 5");
            // create List of Strings to store each value from each SQL entry
            List<String> list = new ArrayList<String>();

            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // generates top 5 users with the highest daily scores
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i == 1 || i == 3) {
                        list.add((String) resultSet.getObject(i));
                    } else if (i == 2) {
                        list.add(Integer.toString((Integer) resultSet.getObject(i)));
                    }
                }
            } // end while

            // returns List of users, their daily scores, and the date
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method getTopFiveLeaderboard

    /**
     * Method that returns a List of entries in an SQL table with the highest totalScore values for all users
     *
     * @return List object that alternates between userID and totalScore for the 5 entries
     * with the largest totalScore values
     */
    public static List<String> getTopFiveAllTimeLeaderboard() {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT userID, totalScore FROM users ORDER BY totalScore DESC LIMIT 5");
            // create List of Strings to store information from resultSet
            List<String> list = new ArrayList<String>();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // stores top 5 user scores of all time
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i == 1 || i == 3) {
                        list.add((String) resultSet.getObject(i));
                    } else if (i == 2) {
                        list.add(Integer.toString((Integer) resultSet.getObject(i)));
                    }
                }
            } // end while
            // returns List of users and their total scores
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method getTopFiveAllTimeLeaderboard

    /**
     * Method that creates a list of the current user's daily score and the current date
     *
     * @param username String of the user's unique ID
     * @param date     String of the current day ("MM/DD/YYYY")
     * @return List object that alternates between userID, dailyScore, and date
     */
    public static List<String> getUserLeaderboard(String username, String date) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM dailyScores WHERE userID='" +
                    username + "' AND date='" + date + "'");
            // create List of Strings to store user daily score information
            List<String> list = new ArrayList<String>();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // stores specified user score for given date
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i == 1 || i == 3) {
                        list.add((String) resultSet.getObject(i));
                    } else if (i == 2) {
                        list.add(Integer.toString((Integer) resultSet.getObject(i)));
                    }
                }
            } // end while
            // returns List of the user, their daily score, and current date
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method getUserLeaderboard

    /**
     * Method that checks if a userID already exists in the SQL table
     *
     * @param username String of the specified userID
     * @return true if the specified user exists in the SQL table, false if the user does not exist
     */
    public static boolean checkForUser(String username) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations
        boolean checker = false; // return value

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM users WHERE userID='" + username + "'");
            // create List to store query information
            List<String> list = new ArrayList<String>();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            //  sets checker if the username exists in the SQL table or not
            if (!resultSet.next()) {
                checker = false;
            } else {
                // stores specified user information
                while (resultSet.next()) {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        if (i == 1) {
                            list.add((String) resultSet.getObject(i));
                        } else if (i == 2 || i == 3 || i == 4) {
                            list.add(Integer.toString((Integer) resultSet.getObject(i)));
                        }
                    }
                }
                checker = true;
            }
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return checker;
    } // end method checkForUser

    /**
     * Method that creates a new user entry in the SQL table
     *
     * @param username String that will be used as the unique userID of the user
     */
    public static void createUser(String username) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // insert query
            statement.executeUpdate("INSERT INTO users VALUES ('" + username + "',0,0,0)");
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } //end catch
        finally // ensure statement and connection are closed
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
    } // end method createUser

    /**
     * Method that checks the SQL table to see if the specified user has played on the specified day to prevent
     * multiple plays per day per user
     *
     * @param username String of specified userID to use in SQL query
     * @param date     String of date to use in SQL query
     * @return true if userID has played that day, false if the user as not played for that day
     */
    public static boolean checkForPlayed(String username, String date) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations
        boolean checker = false; // return value

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM dailyScores WHERE userID='" + username + "' " +
                    "AND date='" + date + "'");
            // create List of Strings to store query information
            List<String> list = new ArrayList<String>();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            if (!resultSet.next()) {
                checker = false;
            } else {
                // stores specified user daily information
                while (resultSet.next()) {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        if (i == 1) {
                            list.add((String) resultSet.getObject(i));
                        } else if (i == 2 || i == 3 || i == 4) {
                            list.add(Integer.toString((Integer) resultSet.getObject(i)));
                        }
                    }
                } // while end
                checker = true;
            }
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return checker;
    } // end method checkForPlayed

    /**
     * Method that checks if the specified user exists in the SQL table, and if they have played the Wordle for the
     * specified date
     *
     * @param username String for the specified userID
     * @param date     String for the specified date
     * @return true if user is able to play the Wordle, false if the user has already played the Wordle
     */
    public static boolean canPlayWordle(String username, String date) {
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations
        if (checkForUser(username)) {
            if (checkForPlayed(username, date)) {
                return false;
            } else {
                return true;
            }
        } else {
            createUser(username);
            return true;
        }
    } // end method canPlayWordle

    /**
     * Method that checks if the user inputted word is a valid word to run through the Wordle game by checking if the
     * input exists in a word table in SQL database
     *
     * @param guess String of 5 letter word user guessed in Wordle game
     * @return true if word exists in table and is a valid word, false if word is not a valid word
     */
    public static boolean checkWordValidity(String guess) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
        guess.toLowerCase(Locale.ROOT); // set guess to lowercase to prevent case sensitive situations
        boolean checker = false; // return value

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM validWords WHERE word='" + guess + "'");
            // create List to store query information
            List<String> list = new ArrayList<String>();
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // checks if user inputted word exists in word bank table
            if (!resultSet.next()) {
                checker = false;
            } else {
                // stores word information
                while (resultSet.next()) {
                    for (int i = 1; i <= numberOfColumns; i++) {
                        if (i == 1) {
                            list.add((String) resultSet.getObject(i));
                        } else if (i == 2 || i == 3 || i == 4) {
                            list.add(Integer.toString((Integer) resultSet.getObject(i)));
                        }
                    }
                }
                checker = true;
            }
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return checker;
    } // end method checkWordValidity

    /**
     * Method that sends query to SQL database to append daily score entries to a table with a specified userID,
     * dailyScore value, and the current date
     *
     * @param username String of userID
     * @param score    int of dailyScore value
     * @param date     String of current date ("MM/DD/YYYY")
     */
    public static void addDailyScore(String username, int score, String date) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            statement.executeUpdate("INSERT INTO dailyScores VALUES ('" + username + "'," + score + ",'" + date + "')");
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure statement and connection are closed
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
    } // end method addDailyScore

    /**
     * Method that creates a list to store the information of a specified user in SQL table
     *
     * @param username String of the userID used to call information from SQL table
     * @return List object that alternates between userID, totalScore, totalRoundsPlayed, and winStreak
     */
    public static List<String> accessUser(String username) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create Statement for querying database
            statement = connection.createStatement();
            // query database
            resultSet = statement.executeQuery("SELECT * FROM users WHERE userID='" + username + "'");
            // create List of Strings to store each value of user information
            List<String> list = new ArrayList<String>();

            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // stores specified user information
            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    if (i == 1) {
                        list.add((String) resultSet.getObject(i));
                    } else if (i == 2 || i == 3 || i == 4) {
                        list.add(Integer.toString((Integer) resultSet.getObject(i)));
                    }
                }
            } // end while
            // returns List of user information
            return list;
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement, and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
        return null;
    } // end method accessUser

    /**
     * Method that updates the user information via SQL query using the specified userID, totalScore, totalRoundsPlayed,
     * and winStreak
     *
     * @param username  String for userID
     * @param score     int for totalScore
     * @param rounds    int for totalRoundsPlayed
     * @param winStreak int for winStreak
     */
    public static void updateUser(String username, int score, int rounds, int winStreak) {
        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        username.toLowerCase(Locale.ROOT); // sets username to lowercase to prevent case sensitive situations

        // connect to database and query database
        try {
            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "swd_group005", "password");
            // create statement for querying database
            statement = connection.createStatement();
            // query database
            statement.executeUpdate("UPDATE users SET totalScore = " + score + ", totalRoundsPlayed = " + rounds +
                    ", winStreak = " + winStreak + " WHERE userID = '" + username + "'");
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure statement and connection are closed
        {
            try {
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        } // end finally
    } // end method updateUser

    /**
     * Method that calculates the new user information based on their performance on the current day's Wordle. The
     * totalScore, totalRoundsPlayed, and winStreak numbers will be changed in the SQL table using these calculations
     *
     * @param list      List of the current user statistics
     * @param score     int of score for the day
     * @param winStreak boolean if the user continued their win streak for the day
     */
    public static void calculateUserStats(List<String> list, int score, boolean winStreak) {
        String username = list.get(0).toLowerCase(Locale.ROOT); // set username to lowercase to prevent case sensitive situations

        // parses through list parameter for the user statistics
        int currentScore = Integer.parseInt(list.get(1));
        int currentRounds = Integer.parseInt(list.get(2));
        int currentStreak = Integer.parseInt(list.get(3));

        // updates user statistics
        currentScore += score;
        currentRounds += 5;
        if (winStreak) {
            currentStreak += 1;
        } else {
            currentRounds = 0;
        }

        // updates user statistics in SQL table
        updateUser(username, currentScore, currentRounds, currentStreak);
    } // end method calculateUserStats
} // end class WordleModel
