package animation;
import biuoop.DrawSurface;
/**
 * Animation.
 * We will take the template-methods and put them in an interface called Animation.
 */
public interface Animation {
    /**
     * doOneFrame.
     * In charge of the logic.
     * For example, drawing and removing blocks and balls.
     *
     * @param d a drawsurface.
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    void doOneFrame(DrawSurface d, double dt);
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     *
     * @return true if the animation should stop, false otherwise.
     */
    boolean shouldStop();
}
