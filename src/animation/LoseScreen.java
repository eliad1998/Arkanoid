package animation;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * LoseScreen.
 * If the game ended with the player losing all his lives, the end screen should display the message "Game Over.
 * Your score is X" (X being the final score).
 */
public class LoseScreen implements Animation {
    private boolean stop;
    private int scores;
    /**.
     * Creates new instance of LoseScreen.
     * The constructor of our class.
     *
     * @param scores the scores the player did.
     */
    public LoseScreen(int scores) {
        this.scores = scores;
        //First instalized as false becasue we dont want to stop.
        this.stop = false;
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * In this class it is draw on the screen a message of game over.
     *
     * @param d a drawsurface.
     * @param dt do nothing in this animation.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 4, d.getHeight() / 3, "GAME OVER", 32);
        d.setColor(Color.black);
        d.drawText(d.getWidth() / 4, d.getHeight() / 2, "Your score is " + scores , 32);
        d.drawText(d.getWidth() / 4, d.getHeight() * 2 / 3, "Press SPACE to continue" , 32);
    }
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     * @return true if the animation should stop, false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
