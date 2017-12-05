package game;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import biuoop.DrawSurface;

import java.awt.Color;

/**.
 * The class represents Ball.
 * Each point has radius,center and color
 * In addition we added some properties like velocity of the Ball in the window, the window height and weight
 */
public class Ball implements Sprite {
    //The center point of the ball.
    private Point center;
    //The radius of the ball.
    private int radius;
    //The color of the ball.
    private java.awt.Color color;
    //The velocity of the ball.
    private Velocity velocity;
    //The height of our window
    private int guiHeight;
    //The width of our window
    private int guiWidth;
    //Where the frame set out from
    private Point startPoint;
    //The game environment
    private GameEnvironment environment;
    //Determines if first frame.
    private boolean firstFrame;
    // constructors
    /**.
     * Creates new instance of Ball.
     * The constructor of our class Ball.
     * @param center -the center point of our ball.
     * @param r -the radius of our ball.
     * @param color - the color of our ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        //Defining all the private variables
        this.center = center;
        this.radius = r;
        this.color = color;

        //Instalizing the other private variables with a value to prevent crash.
        this.velocity = new Velocity(0, 0);
        this.guiHeight = 0;
        this.guiWidth = 0;
        this.startPoint = new Point(0, 0);
        this.environment = new GameEnvironment();
        this.firstFrame = true;
    }
    /**.
     * Creates new instance of Ball.
     * Another constructor of our class Ball.
     * Now instead of inserting point of our circle we will insert the x and y cordinates of this points
     * @param  x -the x cordinate of the center of the ball
     * @param  y -the y cordinate of the center of the ball
     * @param  r -the radius of our ball.
     * @param  color - the color of our ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        //Create new point with the x,y cordinates
        this.center = new Point(x, y);
        this.radius = r;
        this.color = color;
        //Instalizing the other private variables with a value to prevent crash.
        this.velocity = new Velocity(0, 0);
        this.guiHeight = 0;
        this.guiWidth = 0;
        this.startPoint = new Point(0, 0);
        this.environment = new GameEnvironment();
        this.firstFrame = true;
    }
    // accessors
    /**.
     * getX.
     * @return the x value of the ball's center x cordinate.
     */
    public int getX() {
        return (int) this.center.getX();
    }
    /**.
     * getY.
     * @return the y value of the ball's center x cordinate.
     */

    public int getY() {
        return (int) this.center.getY();
    }
    /**.
     * getSize.
     * @return the radius of our ball.
     */
    public int getSize() {
        return this.radius;
    }
    /**.
     * getColor.
     * @return the color of our ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
//******************************** Interface Sprite functions ****************************************************
    /**.
     * drawOn.
     * draw the ball on the given DrawSurface.
     * We will use made gui functions.
     * @param  surface our Drawsurface
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.black);
        //Drawing the frame of the ball.
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
        //Setting the color of the ball.
        surface.setColor(this.getColor());
        //Drawing the ball.
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }
    /**
     * timePassed.
     * Notify the sprite that time has passed.
     * In the case of ball we will use moveonestep function.
     *
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    public void timePassed(double dt) {

        if (this.firstFrame) {
            this.setVelocity(dt * this.velocity.getDx(), dt * this.velocity.getDy());
            this.firstFrame = false;
        }
        this.moveOneStep();
    }
    /**
     * addToGame.
     * This method will be in charge of adding the ball to the game.
     * @param g the game we want to add the ball to.
     */
    public void addToGame(GameLevel g) {
        //Ball is a sprite.
        g.addSprite(this);
    }
    /**
     * removeFromGame.
     * This method will be in charge of removing the ball from the game.
     *
     * @param g the game we want to delete the ball from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
//*******************************************************************
    //Setting functions
    /**.
     * setWidth.
     * Setting the width of the window
     * @param width the width we want the window to be
     */
    public void setWidth(int width) {
        this.guiWidth = width;
    }
    /**.
     * setStart.
     * Setting the start point of the frame where the balls at
     * @param x the start x of the frame
     * @param y the start y of the frame
     */
    public void setStart(int x, int y) {
        this.startPoint = new Point(x, y);
    }
    /**.
     * setHeight.
     * Setting the height of the window
     * @param height the width we want the window to be.
     */
    public void setHeight(int height) {
        this.guiHeight = height;
    }
    /**.
     * setVelocity.
     * Setting the velocity of the ball
     * @param v the new velocity we want the ball to be.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**.
     * setVelocity.
     * Setting the velocity of the window by getting dx and dy.
     * @param dx the distance the ball will move on x axis.
     * @param dy the distance the ball will move on y axis.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    /**.
     * SetGameEnvironment.
     * Setting the game environment where the balls at.
     * @param gameEnvironment a game environment.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.environment = gameEnvironment;
    }
    /**.
     * getVelocity.
     * @return the velocity on the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**.
     * moveOneStep.
     * Moving the ball in the draw surfece by his velocity.
     * (1)Compute the ball trajectory (the trajectory is "how the ball will move
       without any obstacles" -- its a line starting at current location, and
       ending where the velocity will take the ball if no collisions will occur).
     * (2)Check (using the game environment) if moving on this trajectory will hit anything.
     * (2.1)If no, then move the ball to the end of the trajectory.
     * (2.2)Otherwise (there is a hit):
     * (2.2.2)move the ball to "almost" the hit point, but just slightly before it.
     * (2.2.3)notify the hit object (using its hit() method) that a collision occurred.
     * (2.2.4)update the velocity to the new velocity returned by the hit() method.
     */
    public void moveOneStep() {
        //The velocity of the ball.
        Velocity v = this.getVelocity();
        //The center the ball will be.
        Point newCenter = null;
        //the trajectory is "how the ball will move without any obstacles"
        Line trajectory = new Line(this.center, v.applyToPoint(this.center));
        //No collision so the ball won't hit anything
        if (this.environment.getClosestCollision(trajectory) == null) {
            newCenter = v.applyToPoint(this.center);
        } else {         //The ball will hit a collideable.
            //The closest collidalbe the ball is about to hit.
            Point close = this.environment.getClosestCollision(trajectory).collisionPoint();
            //The collideable object the ball is about to hit.
            Collidable hit = this.environment.getClosestCollision(trajectory).collisionObject();
            //Moving the ball to a almost the margin of a block.
            newCenter = chooseNewCenter(hit.getCollisionRectangle(), close);
            //Updating the velocity by our hit method.
            this.velocity = hit.hit(this, close, this.velocity);
        }
        //Updating the center point.
        this.center = newCenter;
    }
    /**.
     * chooseNewCenter.
     * There are many options to close points to rectangle but we need a point that isn't in the ractangle
     * We will check all the options of close points and choose the point that isn't in the rectangle.
     * @param re the rectangle we check.
     * @param close the point we want to close.
     * @return where the center of the ball will be after the close.
     */
    private Point chooseNewCenter(Rectangle re, Point close) {
        //Options of close points.
        Point option1 = new Point(close.getX() - 0.01, close.getY() - 0.01);
        Point option2 = new Point(close.getX() - 0.01, close.getY() + 0.01);
        Point option3 = new Point(close.getX() + 0.01, close.getY() - 0.01);
        Point option4 = new Point(close.getX() + 0.01, close.getY() + 0.01);

        Point [] points = {option1, option2, option3, option4};
      for (int i = 0; i < points.length; i++) {
          //The point that isn't in the rectnagle.
          if (!re.ractangleContainsPoint(points[i])) {
              return points[i];
          }
      }
      return  null;
    }




}