package game;
import biuoop.DrawSurface;
/**
 * Sprite
 * Sprite is a game object that can be drawn to the screen (and which is not just a background image).
 * Sprites can be drawn on the screen, and can be notified that time has passed.
 * (so that they know to change their position / shape / appearance / etc).
 * In our design, all of the game objects (Ball, Block, Paddle, ...) are Sprites.
 */
public interface Sprite {
    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
    void drawOn(DrawSurface d);
    /**
     * timePassed.
     * notify the sprite that time has passed.
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    void timePassed(double dt);
    /**
     * addToGame.
     * This method will be in charge of adding the ball and the block to the game, calling the appropriate game methods.
     * @param g the game we want to add the sprite to.
     */
    void addToGame(GameLevel g);
}
