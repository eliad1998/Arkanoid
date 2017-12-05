package game;

import geometry.Point;
import geometry.Rectangle;

/**
 * Collidable.
 * Things that can be collided with.
 * In this assignment, this means the Blocks and the Paddle.
 * (in order for the ball to bounce from the edges of the screen, we will place large blocks there).
 */
public interface Collidable {
    /**
     * getCollisionRectangle.
     * @return  the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**.
     * hit.
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter the ball hitted the collideable.
     * @param collisionPoint where the collision accured.
     * @param currentVelocity the velocity that the item collided with the collideable.
     * @return  new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}

