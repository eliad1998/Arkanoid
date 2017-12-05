package animation;
import biuoop.DrawSurface;
import java.awt.Color;
/**
 * WinScreen.
 *  If the game ended by clearing all the levels, the screen should display "You Win! Your score is X".
 */
public class WinScreen implements Animation {
    private boolean stop;
    private int scores;
    /**.
     * Creates new instance of WinScreen.
     * The constructor of our class.
     *
     * @param scores the scores the player did.
     */
    public WinScreen(int scores) {
        this.scores = scores;
        //First instalized as false becasue we dont want to stop.
        this.stop = false;
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * In this class it is draw on the screen a message of winning.
     *
     * @param d a drawsurface.
     * @param dt do nothing on this class.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 4, d.getHeight() / 3, "You win!", 32);
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
