package io;
import java.io.Serializable;
/**
 * ScoreInfo.
 * Stores information about the score - name of the one who did the score and the score itself.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;
    /**.
     * Creates new instance of ScoreInfo
     * The constructor of our class.
     * @param  name the name of the player did the score.
     * @param score the score value of the player.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    //Accessors
    /**.
     * getName.
     * @return the name of who did the score.
     */
    public String getName() {
        return this.name;
    }
    /**.
     * getScore.
     * @return the score value of who did the score.
     */
    public int getScore() {
        return this.score;
    }
    /**.
     * toString.
     * Creating a string representation which we will can deserialize the file.
     *
     * @return a string representation of this class.
     */
    @Override
    public String toString() {
        return "ScoreInfo{"
                + "name='" + this.name + '\''
                +  ", score=" + this.score
                + '}';

    }
}