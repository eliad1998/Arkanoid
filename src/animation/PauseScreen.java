package animation;

import biuoop.DrawSurface;
/**
 * PauseScreen.
 * We will begin by adding an option to pause the game when pressing the p key
 */
public class PauseScreen implements Animation {
    private boolean stop;
    /**.
     * Creates new instance of PauseScreen.
     * The constructor of our class.
     */
    public PauseScreen() {
        //First instalized as false becasue we dont want to stop.
        this.stop = false;
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * In this class it is draw on the screen a message and "pause" the gmae.
     *
     * @param d a drawsurface.
     * @param dt do nothing on this animation.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "Paused -- press space to continue", 32);
    }
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     * @return true if the animation should stop, false otherwise.
     */
    public boolean shouldStop() { return this.stop; }
}
