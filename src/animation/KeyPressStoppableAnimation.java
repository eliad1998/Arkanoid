package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation
 * We now have several Animation implementations that wait for a key press: the Game Over screen.
 * The You Win screen, the Pause screen and the High Scores table.
 * We will extract the "waiting-for-key-press" behavior away from the different screens,
 * And into a KeyPressStoppableAnimation decorator-class that will wrap an existing animation,
 * And add a "waiting-for-key" behavior to it.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    //The key is already pressed on the previous animation.
    private boolean isAlreadyPressed;
    /**.
     * Creates new instance of KeyPressStoppableAnimation.
     * The constructor of our class.
     *
     * @param sensor our keyboard sensor.
     * @param key the key string that if we press it will quit from this animation.
     * @param animation an animation. (We use it to use the decorator pattern).
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        //Initialize stop to be false at first.
        this.stop = false;
        this.isAlreadyPressed = true;
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
        animation.doOneFrame(d, dt);
        if (this.sensor.isPressed(this.key)) {
            //If the space isn't pressed from the previous animation.
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else { //Updating is already pressed.
            this.isAlreadyPressed = false;
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
