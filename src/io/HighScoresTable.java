package io;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * HighScoresTable.
 * We would like to present players with the scores-history of previous games.
 * For this, we need a table to store the historic high scores.
 * This will be represented in a class called HighScoresTable, which will manage a table of size
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scores;
    private int size;
    public static final int DEFAULTSIZE = 5;


    /**.
     * Creates new instance of HighScoresTable
     * The constructor of our class.
     * Create an empty high-scores table with the specified size.
     *
     * @param size means that the table holds up to size top scores.
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<ScoreInfo>();
        this.size = size;

    }
    /**.
     * add.
     * Add a high-score in a way that the list will be sorted by the scores values.
     * We will relate to the rank to determine where will the score will be on the list.
     *
     * @param score a scoreInfo about the current score we want to add.
     */
    public void add(ScoreInfo score) {
        //Determines if the score added.
        int rank = getRank(score.getScore());
        // Rank > `size` means the score is too low and will not be added to the list.
        if (rank > this.size) {
            return;
        } else if (rank == size) {  // Rank `size` means the score will be lowest.
            this.scores.add(size - 1, score);
        } else { //Adding in a specific place in the list.
            //Place starts from 0 so we sub 1.
                this.scores.add(rank - 1, score);
        }
        //If we added the score before another score the list will be bigger than size.
        if (this.scores.size() > this.size) {
            for (int i = this.size; i < this.scores.size(); i++) {
                this.scores.remove(this.scores.get(i));
            }
        }
    }

    //Accessors.
    /**.
     * size.
     * @return  table size.
     */
    public int size() {
        return this.size;
    }
    /**.
     * size.
     * @return  the current high scores.
     * The list is sorted such that the highest scores come first.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }
    /**.
     * size.
     * @return the rank of the current score: where will it be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not be added to the list.
     *
     * @param score a score's value.
     */
    public int getRank(int score) {
        //Determines if the score added.
        boolean added = false;
        //Rank 1 is the first place.
        int rank = 1;
        for (int i = 0; i < this.scores.size(); i++) {
            if (this.scores.get(i).getScore() < score) {
                return rank;
            }
            rank++;
        }
        //Case of didn't found place to put the current score.
        return rank;
    }
    /**.
     * clear.
     * Clears the table
     */
    public void clear() {
        //Clearing the socres.
        this.scores.clear();
        //this.scores.removeAll(this.scores);
    }
    /**.
     * load.
     * load table data from file.
     * Current table data is cleared.
     *
     * @param filename the file we are trying to load.
     * @throws IOException if we have problems with reading the file.
     */
    public void load(File filename) throws IOException {
        //The input stream.
        ObjectInputStream inputStream = null;
        try {
            //Reading the file.
            inputStream = new ObjectInputStream(new FileInputStream(filename));
            //Creating the highscoresTable.
            HighScoresTable highScoresTable = (HighScoresTable) inputStream.readObject();
            //Clearing all the scores.
            this.scores.clear();
            //Updating the scores from the file.
            //Adding all the scores from the file.
            this.scores.addAll(highScoresTable.getHighScores());
        } catch (ClassNotFoundException eClass) { //Problem with downcasting from the file the highscores table
            throw new RuntimeException("Downcasting problems.");
        } catch (FileNotFoundException eNotFound) {         //Problems with finding the file.
            throw new RuntimeException("File didn't found.");
        } catch (IOException eIO) {         //Another input output problems.
            throw new IOException("Failed reading object");
        }
    }
    /**.
     * save.
     * Save table data to the specified file.
     *
     * @param fileName the file we want to save.
     * @throws IOException if there are problems with saving the file.
     */
    public void save(File fileName) throws IOException {
        //The output stream
        ObjectOutputStream outputStream = null;
        //
        try {
            //Writing the object.
            outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
            //Writing this object to the file.
            outputStream.writeObject(this);
            //Input output problems.
        } catch (IOException e) {
            throw new IOException("Failed saving object.");
        } finally { //Closing the file.
            try {
                //We opened a file.
                if (outputStream != null) {
                    outputStream.close();
                }
                //We had problems with closing the file.
            } catch (IOException e) {
                throw new IOException("Failed closing file: " + fileName);
            }
        }
    }
    /**.
     * HighScoresTable.
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     *
     * @param filename the file we want to load.
     * @return  an HighscoreTable by the conditions i said.
     */
    public static HighScoresTable loadFromFile(File filename) {
        //Default size of empty table.
        int defaultSize = 5;
        HighScoresTable highScoresTable = null;
        //The input stream.
        ObjectInputStream inputStream = null;
        try {
            //Reading the file.
            inputStream = new ObjectInputStream(new FileInputStream(filename));
            //Creating the highscoresTable.
             highScoresTable = (HighScoresTable) inputStream.readObject();
        } catch (IOException eIO) { //If the file does not exist, or there is a problem with reading it
            // an empty table is returned.
            highScoresTable = new HighScoresTable(defaultSize);
        } finally {
            //Return the talble.
            return highScoresTable;
        }
    }
    /**.
     * toString.
     * Creating a string representation which we will can deserialize the file.
     *
     * @return a string representation of this class.
     */
    @Override
    public String toString() {
        String str = "HighScoresTable{scores=";
        str += this.scores.toString();
        str += ", size=" + this.size;
        str += '}';

        return str;

    }
}