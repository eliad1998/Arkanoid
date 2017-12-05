package game;

import biuoop.DrawSurface;
import listeners.Counter;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;

/**.
 * ScoreIndicator.
 *
 * The score indicator will hold a reference to the scores counter.
 * And will be added to the game as a sprite positioned at the top of the screen.
 */
public class ScoreIndicator implements Sprite {
    private Counter currentScores;
    /**.
     * Creates new instance of ScoreIndicator.
     * The constructor of our class.
     *
     * @param currentScores a reference to the current scores in the game.
     */
    public ScoreIndicator(Counter currentScores) {
        this.currentScores = currentScores;
    }
    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
   public void drawOn(DrawSurface d) {
       Block scoreBlock = new Block(new Rectangle(new Point(0, 0)
               , GameLevel.GUIWIDTH, GameLevel.SCREENMARGIN - 10)
               , Color.white);

       scoreBlock.drawOn(d);

       Point middleBlock = scoreBlock.getCollisionRectangle().getMiddleRectangle();
       d.setColor(Color.black);
       d.drawText((int) middleBlock.getX(), (int)  middleBlock.getY() + 5
               , "Score:" + currentScores.getValue() + "", 12);

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
