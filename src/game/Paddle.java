package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;

/**
 * Paddle.
 * Is the player in the game.
 * It is a rectangle that is controlled by the arrow keys, and moves according to the player key presses.
 * It should implement the Sprite and the Collidable interfaces.
 * It should also know how to move to the left and to the right.
 */
public class Paddle implements Sprite, Collidable {
    //The key pressed
    private biuoop.KeyboardSensor keyboard;
    //The rectangle represents paddle.
    private Rectangle rectangle;
    //The color of the paddle.
    private java.awt.Color color;
    //The speed of the paddle - the steps he move left and right.
    private int speed;
    //Determines if first frame.
    private boolean firstFrame;
    /**.
     * Creates new instance of Paddle.
     * The constructor of our class Paddle.
     * @param  rectangle the rectangle that represents paddle.
     * @param keyboard the key pressed (or not) in the keyboard.
     * @param color the color of the paddle.
     */
    public Paddle(Rectangle rectangle, biuoop.KeyboardSensor keyboard, java.awt.Color color) {
        this.rectangle = rectangle;
        this.keyboard = keyboard;
        this.color = color;
        //Setting a value to speed.
        this.speed = 5;
        //Setting first frame to be true
        this.firstFrame = true;
    }
    /**.
     * setSpeed.
     * Setting a the paddle's speed.
     *
     * @param speedPaddle the speed we want the paddle will be.
     */
    public void setSpeed(int speedPaddle) {
        this.speed = speedPaddle;
    }
    /**.
     * moveLeft.
     * Moving the paddle left.
     * We will change the upper left point's x- cordinate to move it left.
     */
    public void moveLeft() {
        //Where the new rectangle should be
        double newX = this.rectangle.getUpperLeft().getX() - this.speed;
        //Check if it is passing the margins of the screen.
        if (newX <= GameLevel.SCREENMARGIN) {
            newX = GameLevel.SCREENMARGIN;
        }
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY())
                , this.rectangle.getWidth(), this.rectangle.getHeight());

    }
    /**.
     * moveRight.
     * Moving the paddle right.
     * We will change the upper left point's x- cordinate to move it right.
     */
    public void moveRight() {
        //Where the new rectangle should be
        double newX = this.rectangle.getUpperLeft().getX() + this.speed;
        double newRightX = this.rectangle.getUpperRight().getX() + this.speed;

        if (newRightX >= GameLevel.GUIWIDTH - GameLevel.SCREENMARGIN) {
            newX = GameLevel.GUIWIDTH - GameLevel.SCREENMARGIN - this.rectangle.getWidth();
        }
        //Check if it is passing the margins of the screen.
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY())
                , this.rectangle.getWidth(), this.rectangle.getHeight());
    }
    // Sprite
    /**.
     * timePassed.
     * Check if the "left" or "right" keys are pressed, and if so move it accordingly.
     *
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    public void timePassed(double dt) {
        //updating speed by dt
        if (this.firstFrame) {
            double updated = (double) dt * this.speed;
            this.speed = (int) updated;
            this.firstFrame = false;
        }
        //Left key pressed
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        //Right key pressed
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    /**.
     * drawOn.
     * Draw the paddle to the screen.
     * We will use drawrectangle function that we already created.
     * @param d the drawsurfce.
     */
    public void drawOn(DrawSurface d) {
        this.getCollisionRectangle().drawRectangle(this.color, d);
    }

    // Collidable
    /**.
     * getCollisionRectangle.
     * @return the rectangle represents the paddle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;

    }
    /**.
     * hit.
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     * For region 3, the middle region, it should keep its horizontal direction and only change its vertical one
     (like when hitting a block).
     * For region 1, the ball should bounce back with an angle of 300 degrees (-60), regardless of where it came from.
     * For region 2 is should bounce back 330 degrees (a little to the left).
     * For region 4 it should bounce in 30 degrees.
     * For region 5 in 60 degrees.
     *
     * @param hitter the ball hitted the paddle.
     * @param  collisionPoint the point that the ball collided with the paddle.
     * @param currentVelocity the velocity that the ball collided with the paddle.
     * @return the velocity after the collide.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //Case of hitting under the paddle it means go to "death" region.
        if (collisionPoint.getY() == this.getCollisionRectangle().getLowerLeft().getY()) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //Check which region the collision point was.
        int region = getRegion(collisionPoint);
        //region 5 is 60 degreees
        //Because of the moveonestep function we count it as 120 (180-120=60).
        if (region == 5) {
            return Velocity.fromAngleAndSpeed(120, currentVelocity.getSpeed());
        }
        //region 4 is 30 degreees
        //Because of the moveonestep function we count it as 120 (180-150=30).
        if (region == 4) {
            return Velocity.fromAngleAndSpeed(150, currentVelocity.getSpeed());
        }
        //region 3 is 0 degreees
        //Because of the moveonestep function we count it as 180 (180-0=180).
        if (region == 3) {
            return Velocity.fromAngleAndSpeed(180, currentVelocity.getSpeed());
        }
        //region 2 is 330 degreees = -30
        //Because of the moveonestep function we count it as 120 (180-210=-30).
        if (region == 2) {
            return Velocity.fromAngleAndSpeed(210, currentVelocity.getSpeed());
        }

        //region 1 is 300 degreees
        //Because of the moveonestep function we count it as -120 (180--120=300).
        return Velocity.fromAngleAndSpeed(-120, currentVelocity.getSpeed());
    }
    /**.
     * getRegion.
     * The paddle is divided to 5 regions.
     * We determine the region by parts of 1 (0.2 is first, 0.4 second...).
     * @param collisionPoint the point that the ball collided with the paddle.
     * @return the region that the hit point was.
     */
    private int getRegion(Point collisionPoint) {
        //The length of the paddle.
        double lengthPaddle = Math.abs(this.rectangle.getUpperLeft().getX() - this.rectangle.getUpperRight().getX());
        //The distance of x- cordinates between the collision point and the start of the paddle.
        double lengthFromStart = Math.abs(this.rectangle.getUpperLeft().getX() - collisionPoint.getX());
        //The dose between the length.
        double dose = lengthFromStart / lengthPaddle;
        //region 1
        if (dose <= 0.2) {
            return 1;
        }
        //region 2
        if (dose <= 0.4) {
            return 2;
        }
       //region 3
        if (dose <= 0.5) {
            return 3;
        }
        //region 4
        if (dose <= 0.8) {
            return 4;
        }
        //region 5
        return 5;
    }
    /**.
     * addToGame.
     * Add this paddle to the game.
     * We will do it by adding it as a colidable and as a sprite to the game.
     * @param g the game we want to add the paddle to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);

    }

    /**
     * removeFromGame.
     * This method will be in charge of removing the paddle from the gameLevel.
     *
     * @param gameLevel the gameLevel we want to remove the paddle from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        //Removing it from the collidables collection.
        gameLevel.removeCollidable(this);
        //Removing it from the sprites collection.
        gameLevel.removeSprite(this);
    }
}