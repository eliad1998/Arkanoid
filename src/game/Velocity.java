package game;

import geometry.Point;

/**.
 * Velocity.
 * The class represents Velocity.
 * That velocity is in two dimensions - x and y.
 * Each velocity has change in x and change in y - dx and dy.
 */
public class Velocity {
    //The change in x.
    private double dx;
    //The change in y.
    private double dy;
    // constructor
    /**.
     * Creates new instance of Velocity
     * The constructor of our class Velocity.
     * @param  dx - the change in x.
     * @param  dy - the change in y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**.
     * getDx.
     * @return the dx value of our velocity.
     */
    public double getDx() {
        return this.dx;
    }
    /**.
     * getDy.
     * @return the dy value of our velocity.
     */
    public double getDy() {
        return this.dy;
    }

    /**.
     * getAngle.
     * @return the angle of our velocity.
     */
    public double getAngle() {
      double angle = Math.toDegrees(Math.atan(this.dx / this.dy));

      return angle;
    }
    /**.
     * getSpeed.
     * @return the speed value of our velocity.
     */
    public double getSpeed() {
        return Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
    }

    /**.
     * applyToPoint.
     * Take a point with position (x,y) and return a new point
       with position (x+dx, y+dy).
     * @param p the point we want to move.
     * @return a new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(this.dx + p.getX(), this.dy + p.getY());
    }
    /**.
     * fromAngleAndSpeed.
     * Take an angle and speed and apply it into velocity
     * We will use trigo to find dx and dy as using in polar form with direction and angle
     * @param angle the direction of speed vector.
     * @param speed the size of the speed vector.
     * @return the velocity with that speed and angle
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // As in the example if angle=90 and speed=2 dx=2 so sin90=1
        // In java math method calculate sin and cos by radians so we will cast the angles to radians.
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }

}