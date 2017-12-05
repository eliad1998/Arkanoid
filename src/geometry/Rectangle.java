package geometry;

import java.awt.Color;

/**.
 * Rectangle.
 * The class represents Rectangle.
 * Each rectangle has upper left point, width and height.
 */
public class Rectangle {
    //Members of the class.
    private Point upperLeft;
    private double width;
    private double height;
    /**.
     * Create a new rectangle with location and width/height.
     * The constructor of our class Rectangle.
     * @param  upperLeft -the upper left point of the rectangle.
     * @param  width -the width of the rectangle.
     * @param  height -the height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
     this.upperLeft = upperLeft;
     this.width = width;
     this.height = height;
    }

    /**.
     * intersectionPoints.
     * Return a (possibly empty) List of intersection points with the specified line.
     * First We will use other functions to get the points of the edges of the rectangle.
     * Then we will create the line of the rectangle and check the intersections.
     * @param  line -the line we want to check intersections with.
     * @return (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List intersectionPoints(Line line) {
        //The upper rib that aligns to x axis.
        Line upperX = new Line(getUpperLeft(), getUpperRight());
        //The left rib that aligns to y axis.
        Line leftY = new Line(getUpperLeft(), getLowerLeft());
        //The lower rib that aligns to x axis.
        Line lowerX = new Line(getLowerLeft(), getLowerRight());
        //The right rib that aligns to y axis.
        Line rightY = new Line(getUpperRight(), getLowerRight());
        //The ribs of the rectangle.
        Line [] rectangleLines = {upperX, leftY, lowerX, rightY};
        //Create new list of points
        java.util.List<Point> lst = new java.util.ArrayList<Point>();
        //Checking intersection between the line and the ribs of the rectangle.
        for (int i = 0; i < rectangleLines.length; i++) {
            if (rectangleLines[i].isIntersecting(line)) {
                lst.add(rectangleLines[i].intersectionWith(line));
            }
        }
        return lst;
    }

    /**.
     * getWidth.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**.
     * getHeight.
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**.
     * getUpperLeft.
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**.
     * getUpperRight.
     * We represent the it as it on gui (the axes are upside down).
     * @return the upper-right point of the rectangle.
     */
    public Point getUpperRight() {
        //Adding to x cordinate the width of the rectangle.
        return new Point(this.upperLeft.getX() + getWidth(), this.upperLeft.getY());
    }
    /**.
     * getLowerLeft.
     * We represent the it as it on gui (the axes are upside down).
     * @return the lower-left point of the rectangle.
     */
    public Point getLowerLeft() {
        //Adding to y cordinate the height.
        return new Point(this.upperLeft.getX(), this.getUpperLeft().getY() + getHeight());
    }
    /**.
     * getLowerRight.
     * We represent the it as it on gui (the axes are upside down).
     * @return the lower-right point of the rectangle.
     */
    public Point getLowerRight() {
        //Adding to x and y cordinates the height and width.
        return new Point(this.upperLeft.getX() + getWidth(), this.getUpperLeft().getY() + getHeight());
    }
    /**.
     * drawRectangle.
     * Drawing our recangle on a drawface.
     * @param color the color we want the rectangle to be.
     * @param d the drawsurface we want the rectangle to be on.
     */
    public void drawRectangle(Color color, biuoop.DrawSurface d) {
        //The upper left point of the rectangle.
        Point start = this.getUpperLeft();
        //Setting the color of the rectangle
        d.setColor(color);
        //Using the fillRectangle function.
        d.fillRectangle((int) start.getX(), (int) start.getY(), (int) this.getWidth(), (int) this.getHeight());
    }
    /**.
     * drawRectangle.
     * Drawing our recangle on a drawface.
     *
     * @param stroke the color of the stroke of the rectangle.
     * @param d the drawsurface we want the rectangle to be on.
     */
    public void drawStroke(Color stroke, biuoop.DrawSurface d) {
        //The upper left point of the rectangle.
        Point start = this.getUpperLeft();
        //Setting the color of the stroke.
        d.setColor(stroke);
        d.drawRectangle((int) start.getX(), (int) start.getY(), (int) this.getWidth(), (int) this.getHeight());
    }
    /**.
     * RactangleContainsPoint.
     * Checks if the point is in the rectangle.
     * @param p a point.
     * @return true if the point in the rectangle, false otherwise.
     */
    public boolean ractangleContainsPoint(Point p) {
        //The maximum x between the edges of the ractangle.
        double maxX = Math.max(this.getUpperLeft().getX(), this.getLowerRight().getX());
        //The minimum x between the edges of the ractangle.
        double minX = Math.min(this.getUpperLeft().getX(), this.getLowerRight().getX());
        //Checks if x between the edges x coordinates of the rectangle.
        boolean xIn = p.getX() >= minX && p.getX() <= maxX;
        //The maximum y between the edges of the ractangle.
        double maxY = Math.max(this.getUpperLeft().getY(), this.getLowerRight().getY());
        //The maximum y between the edges of the ractangle.
        double minY = Math.min(this.getUpperLeft().getY(), this.getLowerRight().getY());
        //Checks if y between the edges y coordinates of the rectangle.
        boolean yIn = p.getY() >= minY && p.getY() <= maxY;

        return  xIn && yIn;

    }
    /**.
     * getMiddleRectangle.
     * @return the middle point of the rectangle by the middle point of the slant.
     */
    public Point getMiddleRectangle() {
        //The slant is from the upper left to lower right.
        Line slant = new Line(this.getUpperLeft(), this.getLowerRight());
        //return the middle point of the slant.
        return slant.middle();

    }
}
