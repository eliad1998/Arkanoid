package game;
import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;
/**
 * GameEnvironment.
 * collection of such things. The ball will know the game environment.
 * And will use it to check for collisions and direct its movement.
 */

public class GameEnvironment {
    //The collideable objects list.
    private List<Collidable> collidables;
    /**.
     * Creates new instance of GameEnvironment.
     * The constructor of our class GameEnvironment.
     * Instalize the list of collidalbe objects.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**.
     * addCollidable
     * Add the given collidable to the environment.
     * @param  c the collideable we want to add.
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**.
     * removeCollidable.
     * Remove the given collidable from the environment.
     * @param  c the collideable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
    /**.
     * getClosestCollision.
     * Add the given collidable to the environment.
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param  trajectory the trajectory of the ball.
     * @return null If this object will not collide with any of the collidables in this collection.
       else, return the information about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<CollisionInfo> collisions = new ArrayList<CollisionInfo>();
        //The closest collision about to occur.
        CollisionInfo closest = null;
        //Moving on all the collideables.
        for (int i = 0; i < this.collidables.size(); i++) {
            //Get the closest intersection point to the rectangle of the collideable.
            Point closestPoint = trajectory.closestIntersectionToStartOfLine(
                    this.collidables.get(i).getCollisionRectangle());

            if (closestPoint != null) {
                //Add this as a new optional collision.
                collisions.add(new CollisionInfo(this.collidables.get(i), closestPoint));
            }
        }
        //Now we will check which collidable is closest to the ball.
        double minDistance = Double.POSITIVE_INFINITY;
        //The index of collideable.
        int indexClosest = 0;
        for (int i = 0; i < collisions.size(); i++) {
            //This collision is closer to the ball than the others.
            if (collisions.get(i).collisionPoint().distance(trajectory.start()) < minDistance) {
                //Updating the min value.
                minDistance = collisions.get(i).collisionPoint().distance(trajectory.start());
                indexClosest = i;
            }
        }
        //No collisions.
        if (collisions.size() == 0) {
            return null;
        }
        return collisions.get(indexClosest);
    }
}
