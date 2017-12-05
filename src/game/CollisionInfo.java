package game;

import geometry.Point;

/**.
 * CollisionInfo.
 * Has an info about the collision object and the point where the collision occured.
 */
public class CollisionInfo {
    //The point where the collision occured.
    private Point collisionPoint;
    //The collision object that the ball collided with.
    private Collidable collisionObject;
    /**.
     * Creates new instance of CollisionInfo.
     * The constructor of our class Ball.
     * @param collisionObject the collision object that the ball collided with.
     * @param collisionPoint the point where the collision occured.
     */
    public CollisionInfo(Collidable collisionObject, Point collisionPoint) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }
    /**.
     * collisionPoint
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return  this.collisionPoint;
    }
    /**.
     * collisionPoint
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
