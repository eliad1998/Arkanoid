package game;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**.
 * LevelIndicator.
 *
 * The level indicator hold the level name.
 */
public class LevelIndicator implements Sprite {
    private String levelName;
    /**.
     * Creates new instance of LevelIndicator.
     * The constructor of our class.
     *
     * @param levelName the name of the level.
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }
    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
    public void drawOn(DrawSurface d) {
        Block levelBlock = new Block(new Rectangle(new Point(0, 0)
                , GameLevel.GUIWIDTH, GameLevel.SCREENMARGIN - 10)
                , Color.white);

        Point middleBlock = levelBlock.getCollisionRectangle().getMiddleRectangle();
        d.setColor(Color.black);
        d.drawText((int) middleBlock.getX() + GameLevel.GUIWIDTH / 4
                , (int)  middleBlock.getY() + 5 , "Level:" + levelName, 12);

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
