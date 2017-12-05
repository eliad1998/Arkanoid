package geometry;
import game.GameLevel;
import game.Sprite;
import biuoop.DrawSurface;
/**.
 * The class represents Circle.
 * Each point has radius,center and color of inside the ball and color of out the ball.
 */
public class Circle implements Sprite {
    //The center point of the ball.
    private Point center;
    //The radius of the ball.
    private int radius;
    //The color of the ball.
    private java.awt.Color color;
    //The color of the the frame of the ball.
    private java.awt.Color colorFrame;
    // constructors
    /**.
     * Creates new instance of Circle.
     * The constructor of our class.
     * @param center -the center point of our Circle.
     * @param r -the radius of our Circle.
     * @param colorCircle - the color of our Circle.
     */
    public Circle(Point center, int r, java.awt.Color colorCircle) {
        //Defining all the private variables
        this.center = center;
        this.radius = r;
        this.color = colorCircle;
        //In regular cases it will be the color of the inside
        this.colorFrame = colorCircle;
    }
    /**.
     * Creates new instance of Circle.
     * Another constructor of our class.
     * Now instead of inserting point of our circle we will insert the x and y cordinates of this points.
     *
     * @param  x -the x cordinate of the center of the Circle
     * @param  y -the y cordinate of the center of the Circle
     * @param  r -the radius of our Circle.
     * @param  colorCircle - the color of our Circle.
     */
    public Circle(int x, int y, int r, java.awt.Color colorCircle) {
        //Create new point with the x,y cordinates
        this.center = new Point(x, y);
        this.radius = r;
        this.color = colorCircle;
        //In regular cases it will be the color of the inside
        this.colorFrame = colorCircle;
    }
    /**.
     * setFrameColor.
     * Setting the color of the frame of the ball.
     *
     * @param frameColor a color.
     */
    public void setFrameColor(java.awt.Color frameColor) {
        this.colorFrame = frameColor;
    }
        /**.
         * drawOn.
         * Draw the sprite to the screen.
         * @param d the drawsurface we will draw the sprite on.
         */
     public void drawOn(DrawSurface d) {
         //Setting the color of the circle.
         d.setColor(this.color);
         //Drawing the ball.
         d.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);

         //Setting the color of frame of the circle.
         d.setColor(this.colorFrame);
         //Drawing the ball.
         d.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
     }
        /**
         * timePassed.
         * notify the sprite that time has passed.
         *
         * @param dt do nothing on this class.
         */
      public void timePassed(double dt) {
          //Do nothing on circle.
          return;
      }
        /**
         * addToGame.
         * This method will be in charge of adding the ball and the block to the game,
         * calling the appropriate game methods.
         * @param g the game we want to add the sprite to.
         */
      public void addToGame(GameLevel g) {
          g.addSprite(this);
        }

    /**.
     * getX.
     * @return the x value of the center of the circle.
     */
    public double getX() {
        return  this.center.getX();
    }
    /**.
     * getY.
     * @return the y value of the center of the circle.
     */
    public double getY() {
        return this.center.getY();
    }
    /**.
     * getCenter
     * @return the point of the center of the circle.
     */
    public Point getCenter() {
        return this.center;
    }
}
