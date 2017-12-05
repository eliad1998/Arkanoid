package animation;
import biuoop.DrawSurface;
import game.SpriteCollection;
import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen,for numOfSeconds seconds.
 * And on top of them it will show a countdown from countFrom back to 1.
 * Where each number will appear on the screen for (numOfSeconds / countFrom) secods.
 * Before it is replaced with the next one.
 */

public class CountdownAnimation implements Animation {
    //The number of the seconds.
    private double numOfSeconds;
    //The first number we start for example in 3 2 1 it will be 3
    private int countFrom;
    private boolean stop;
    private long startTime;
    //The CountdownAnimation should display the counting on top of the game screen itself,
    // So that the player will know what to expect when the game starts.
    // For this reason, we pass the SpriteCollection to the CountdownAnimation constructor.
    private SpriteCollection gamescreen;
    /**.
     * Creates new instance of CountdownAnimation.
     * The constructor of our class.
     *
     * @param numOfSeconds the number of seconds to show the animation.
     * @param countFrom the first number we count from.
     * @param gameScreen the game objects we want to show.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
    this.numOfSeconds = numOfSeconds;
    this.countFrom = countFrom;
    this.gamescreen = gameScreen;
    //The current second starts with the countFrom value.
    this.stop = false;
    //Save the time of the running
    this.startTime = System.currentTimeMillis();
    }
    /**
     * doOneFrame.
     * In charge of the logic.
     * In this case on frame is showing a number of 3 or 2 or 1 or 0
     *
     * @param d a drawsurface.
     * @param dt do nothing in this animation.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        long usedTime = System.currentTimeMillis() - this.startTime;
        this.gamescreen.drawAllOn(d);
        //We want to introduce the numbers in sections of numOfSeconds / countFrom
        //The count from is the first number so we sub from it the used time in umOfSeconds / countFrom sections.
        //We dividing by 1000 because usedTime is in milliseconds.
        int number = (int) (1 + this.countFrom - (usedTime) / (1000 * this.numOfSeconds / this.countFrom));
        //Drawing the numbers
        d.setColor(Color.cyan);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, number +  "", 32);
        //1000 because it is milliseconds.
        if (usedTime > 1000 * this.numOfSeconds) {
            this.stop = true;
        }
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