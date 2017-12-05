package levels;

import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
import game.SpriteCollection;

/**
 * Background.
 * Represents background of level.
 */
public class Background implements Sprite {
    private SpriteCollection spriteCollection;
    /**.
     * Create a new Background.
     * The constructor of our class.
     */
    public Background() {
        //Inizialize the sprite collection.
        this.spriteCollection = new SpriteCollection();
    }
    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
   public void drawOn(DrawSurface d) {
       this.spriteCollection.drawAllOn(d);
   }
    /**
     * timePassed.
     * notify the sprite that time has passed.
     *
     * @param dt do nothing on this class.
     */
    public void timePassed(double dt) {
        //Do nothing
        return;
    }
    /**
     * addToGame.
     * This method will be in charge of adding the ball and the block to the game, calling the appropriate game methods.
     * @param g the game we want to add the sprite to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**.
     * addSprite
     * Add the given Sprite to the collection.
     * @param  s the Sprite we wnat to add.
     */
    public void addSprite(Sprite s) {
        this.spriteCollection.addSprite(s);
    }


}
