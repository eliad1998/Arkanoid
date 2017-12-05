package game;

import biuoop.DrawSurface;
import listeners.Counter;
import geometry.Line;
import geometry.Rectangle;
import geometry.Point;
import java.awt.Color;

/**.
 * LivesIndicator.
 * Sprite that will sit at the top of the screen and indicate the number of lives.
 */
public class LivesIndicator implements Sprite {
    private Counter currentLives;
    /**.
     * Creates new instance of LivesIndicator.
     * The constructor of our class.
     *
     * @param currentLives a reference to the current lives in the game.
     */
    public LivesIndicator(Counter currentLives) {
        this.currentLives = currentLives;
    }
    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
    public void drawOn(DrawSurface d) {
        Block scoreBlock = new Block(new Rectangle(new Point(0, 0), GameLevel.GUIWIDTH, GameLevel.SCREENMARGIN - 10)
                , Color.white);
        //We won't draw it because there is already a score block drawed.
        //Where the life block will start.
        Point middleLeft =  new Line(scoreBlock.getCollisionRectangle().getUpperLeft()
                , scoreBlock.getCollisionRectangle().getLowerLeft()).middle();
        d.setColor(Color.black);
        //Drawing the lives on the screen
        d.drawText((int) middleLeft.getX() + 10, (int)  middleLeft.getY() + 5, "Lives:" + currentLives.getValue() + ""
                , 12);
    }
    /**
     * timePassed.
     * notify the sprite that time has passed.
     *
     * @param dt do nothing on this class.
     */
    public void timePassed(double dt) {
        //Do nothing on this.
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
}
