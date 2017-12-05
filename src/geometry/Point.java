package geometry;

/**
 * The class represents Point.
 * Each point has x and y coordinates.
 */
public class Point {
    //x,y coordinates
    private double x , y;
    /**.
     * Creates new instance of Point
     * The constructor of our class Point.
     * @param  x -x cordinate.
     * @param  y -y cordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**.
     * distance.
     * Calculates distance between our point to another
     * @param  other another point
     * @return the distance of this point to the other point.
     */
    public double distance(Point other) {
        //dx=x1-x2
        double dX = this.x - other.getX();
        //dy=y1-y2
        double dY = this.y - other.getY();
        //distance=[x1-x2)^2+(y1-y2)^2
        return Math.sqrt(Math.pow(dX , 2) + Math.pow(dY , 2));
    }
    /**.
     * equals.
     * Checks if two points are equal
     * @param  other another point
     * @return return true is the points are equal, false otherwise.
     */
    public boolean equals(Point other) {
        //(x1,y1)=(x2,y2) <=> x1=x2 and y1=y2
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
    /**.
     * getX.
     * @return the x value of this point.
     */
    public double getX() {
        return  this.x;
    }
    /**.
     * getY.
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }
    /**.
     * setX.
     * Setting the x value of our point.
     * @param newX the new x value.
     * Setting our point value to newX.
     */
    public void setX(double newX) {
        this.x = newX;
    }
    /**.
     * setY.
     * Setting the y value of our point.
     * @param newY the new y value.
     * Setting our point value to newY.
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**.
     * print.
     * Prints the point in form of (x, y).
     */
    public void print() {
        System.out.println("(" + this.x + ", " + this.getY() + ")");
    }


    /**.
     * drawPoint.
     * Draws a point on a drawsurface
     * We will get the cordinates from the specific point and use a made function to draw it.
     * @param radius -the radius of the point we want to draw.
     * @param  color - the color of the point we want to draw.
     * @param  d - A drawface we want to draw the line on.
     */
    public void drawPoint(int radius, java.awt.Color color, biuoop.DrawSurface d) {
        d.setColor(color);
        d.fillCircle((int) this.getX(), (int) this.getY(), radius);
    }
}
