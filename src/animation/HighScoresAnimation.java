package animation;

import biuoop.DrawSurface;
import io.HighScoresTable;
import io.ScoreInfo;
import java.awt.Color;
import java.util.List;

/**
 * HighScoresAnimation.
 * We now move to create a graphical representation of the scores.
 * It will display the scores in the high-scores table, until a specified key is pressed.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scoresTable;
    private boolean stop;
    //The screen size.
    static final int GUIWIDTH = 800;
    static final int GUIHEIGHT = 600;
    /**.
     * Creates new instance of HighScoresAnimation.
     * The constructor of our class.
     *
     * @param scores an high score table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scoresTable = scores;
        //First instalized as false becasue we dont want to stop.
        this.stop = false;
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * For example, drawing and removing blocks and balls.
     *
     * @param d a drawsurface.
     * @param dt do nothing on this class.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Filind the window color.
        d.setColor(Color.lightGray);
        d.fillRectangle(0, 0 , GUIWIDTH, GUIHEIGHT);
        //Drawing the headline.
        d.setColor(Color.yellow);
        d.drawText(d.getWidth() / 16, d.getHeight() / 16, "High scores", 32);
        //Drawing the other headlines.
        int playerNameWidth = d.getWidth() / 10;
        int playerScoreWidth = d.getWidth() / 2 + 10;
        int firstLineHeight = d.getHeight() / 7;
        //The player name headline.
        d.setColor(Color.orange);
        d.drawText(playerNameWidth, firstLineHeight, "Player Name", 25);
        //The score headline.
        d.setColor(Color.orange);
        d.drawText(playerScoreWidth, firstLineHeight, "Score", 25);
        //Drawing a line under the headline.
        d.setColor(Color.white);
        d.drawLine(playerNameWidth, firstLineHeight + 3, d.getWidth() / 2 + 100, firstLineHeight);
        //Drawing the players info.
        List<ScoreInfo> scores = this.scoresTable.getHighScores();
        for (int i = 1; i <= scores.size(); i++) {
            //Drawing the players names.
            d.setColor(Color.orange);
            d.drawText(playerNameWidth, firstLineHeight + i * 30, scores.get(i - 1).getName(), 25);
            //Drawing the players scores.
            d.setColor(Color.orange);
            d.drawText(playerScoreWidth, firstLineHeight + i * 30, scores.get(i - 1).getScore() + "", 25);
        }
        //The text to press space to continue.
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 6, d.getHeight() - 60, "Press space to continue", 32);
    }
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     *
     * @return true if the animation should stop, false otherwise.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
