package animation;
import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
/**
 * The AnimationRunner takes an Animation object and runs it.
 * Now, we implement the task-specific information in the Animation object.
 * And run it using the loop in the AnimationRunner class.
 * (Note that now the AnimationRunner has framesPerSecond as a member
 * Which should be set in the constructor. You should use a frame rate of 60 frames per second.)
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    /**.
     * Creates new instance of AnimationRunner.
     * The constructor of our class.
     * Instalize the frame rate to rate of 60 frames per second, the sleeper and the gui.
     *
     * @param gui a gui.
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        //You should use a frame rate of 60 frames per second.
        this.framesPerSecond = 60;
        //Instalizing the sleeper
        this.sleeper = new Sleeper();
    }
    /**.
     * run.
     * Runs an animation.
     *
     * @param animation the animation we want to run.
     */
    public void run(Animation animation) {
        //Mil = 10^(-3)
        int millisecondsPerFrame = 1000 / this.framesPerSecond;;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();
            //Putting the drawsurface and dt value.
            animation.doOneFrame(d, (double) 1 / this.framesPerSecond);
            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }


}
