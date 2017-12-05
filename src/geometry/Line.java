package geometry;
import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;

import java.awt.Color;
import java.util.List;

/**
 * The class represents Line.
 * Each line represented by start point and end point
 */
public class Line implements Sprite {
    //Line is determined by two points
    private Point startPoint , endPoint;
    //The color of the line.
    private Color color;
    /**.
     * Creates new instance of Point.
     * The constructor of our class Line.
     * @param  start - the start point.
     * @param  end - the end point.
     */
    public Line(Point start , Point end) {
        this.startPoint = start;
        this.endPoint = end;
        //The color instalized as black in case we don't mind about the color.
        this.color = Color.black;
    }
    /**.
     * Creates new instance of Point.
     * The constructor of our class Line.
     * @param  x1 - the x cordinate of the start point.
     * @param  y1 - the y cordinate of the start point.
     * @param  x2 - the x cordinate of the end point.
     * @param  y2 - the y cordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.startPoint = new Point(x1, y1);
        this.endPoint = new Point(x2, y2);
        //The color instalized as black in case we don't mind about the color.
        this.color = Color.black;
    }
    /**.
     * setColor.
     * Setting the color of the line.
     *
     * @param colorLine the color we want the line will be.
     */
    public void setColor(Color colorLine) {
        this.color = colorLine;
    }
    /**.
     * length.
     * Calculates the length of our line.
     * We will use the distance between the start and end points to calculate it.
     * @return the length of the line.
     */
    public double length() {
        return this.startPoint.distance(this.endPoint);
    }
    /**.
     * middle.
     * Return the middle point of the line between the start and end point.
     * The middle point x cordinate is (x1+x2)/2
     * The middle point y cordinate is (y1+y2)/2
     * @return the middle point of the line.
     */
    public Point middle() {
        //middle x is (x1+x2)/2
        double middleX = (this.start().getX() + this.end().getX()) / 2;
        //middle y is (y1+y2)/2
        double middleY = (this.start().getY() + this.end().getY()) / 2;
        Point middle = new Point(middleX , middleY);
        return middle;
    }
    /**.
     * start.
     * @return the start point of the line.
     */
    public Point start() {
        return this.startPoint;
    }
    /**.
     * end.
     * @return the end point of the line.
     */
    public Point end() {
        return  this.endPoint;
    }
    /**.
     * isOnLine
     * Return if the point is on some line.
     * We will check if the point between the start and end point of the line.
     * We will use max and min functions to check if the x and y cordinates between min and max cordinates
       of the points.
     * @param  line - the line we work on.
     * @param  point - the point we check if on the specific line
     * @return true if the point on the line, false otherwise.
     */
    private boolean isOnLine(Line line, Point point) {
        //The line aligns to x axis
        boolean alignX = false;
        //Case of line align to x axis
        if (line.getSlope() == Double.POSITIVE_INFINITY) {
            alignX = point.getX() == line.start().getX();
        }
        //Checks if the point x cordinates bigger or equals the minimum x cordinates between start and end points.
        if (alignX || point.getX() >= Math.min(line.start().getX() , line.end().getX())) {
        //Checks if the point x cordinates smaller or equals the maximum x cordinates between start and end points.
            if (alignX || point.getX() <= Math.max(line.start().getX(), line.end().getX())) {
        //Checks if the point y cordinates bigger or equals the minimum y cordinates between start and end points.
                if (point.getY() >= Math.min(line.start().getY(), line.end().getY())) {
        //Checks if the point y cordinates smaller or equals the maximum y cordinates between start and end points.
                    if (point.getY() <= Math.max(line.start().getY(), line.end().getY())) {
                        return true;
                    }
                }
            }
        }
            //Any other case when all those ifs dont true
            return  false;
    }
    /**.
     * isIntersecting
     * Return if the lines intersect of not.
     * We will check if the lines has intersection point if yes the are intersecting.
     * @param other the other line.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) != null) {
            return true;
        }
        return  false;
    }
    /**.
     *getSlope
     * Cauculates the slop of our line
     * Slope=dy/dx
     * In case of dx=0 the slope is infinity
     * @return the slope of our line
     */
    private double getSlope() {
        //dx=x1-x2
        double dX = this.startPoint.getX() - this.endPoint.getX();
        //dy=y1-y2
        double dY = this.startPoint.getY() - this.endPoint.getY();
        //Case of line in form x=n
        if (dX == 0) {
            return Double.POSITIVE_INFINITY;
        }
        //Slope= dy/dx
        return dY / dX;
    }
    /**.
     *intersectionWith
     * Cauculates the intersection point between owr line and other line
     * Return the middle point of the line between the start and end point.
     * If the lines have the same equation we will treat as they don't meet.
     * We will relate to cases one line in the form of x=ng.
     * The equation of both lines is y=mx+n so we will desolate x by simple algebra.
     * After finding the intersection point we will check if it on those specific lines with their margins
     * @param other the other line
     * @return the intersection point if the lines intersect,and null otherwise.
     */
    public Point intersectionWith(Line other) {
        //If the lines arent intersections return null
        if (this.getSlope() == other.getSlope()) {
            return null;
        }
        //Both lines are in the case of x=n
        if (this.getSlope() == Double.POSITIVE_INFINITY && other.getSlope() == Double.POSITIVE_INFINITY) {
            //The lines can be the same or dont meet anyway we return null.
            return  null;
        }
        //Same line return null
        if (this.getSlope() == other.getSlope()
                && this.intersectionYaxis().getY() == other.intersectionYaxis().getY()) {
            return null;
        }

        //The point of intersection
        Point intersection;
        double newX, newY;
        //Case of the current line in form x=n
        if (this.getSlope() == Double.POSITIVE_INFINITY) {
            //X is the same in any point so it cuts the line in that x
            newX = this.start().getX();
            //Y value exuals to mx+n - m is the slope
            newY = this.start().getX() * other.getSlope() + other.intersectionYaxis().getY();
            } else if (other.getSlope() == Double.POSITIVE_INFINITY) {
                 //Case of the other line in form x=n and the other is in case mx+n
                //X is the same in any point so it cuts the line in that x
                newX = other.start().getX();
                //Y value exuals to mx+n - m is the slope and the other is in case mx+n
                newY = other.start().getX() * this.getSlope() + this.intersectionYaxis().getY();
            } else {
                //Simple algebra to disolate x
                newX = (other.intersectionYaxis().getY() - this.intersectionYaxis().getY())
                        / (this.getSlope() - other.getSlope());
                //Placing x in the equation of the line
                newY = this.getSlope() * newX + this.intersectionYaxis().getY();

            }
        //Seting the point cordinates
        intersection = new Point(newX, newY);

        //The intersection point is on the specific lines not only the equation
        if (isOnLine(this, intersection) && isOnLine(other, intersection)) {
            return intersection;
        }
        //Any other case.
        return null;
    }
    /**.
     *intersectionYaxis.
     * Calculate the point meets with y value of the line.
     * We will desolate y value by desolate b from the equation y=ax+b.
     * @return the intersection point with y axis if the slope is infinity null.
     */
    private Point intersectionYaxis() {
        if (this.getSlope() != Double.POSITIVE_INFINITY) {
            double yValue = this.startPoint.getY() - this.getSlope() * this.startPoint.getX();
            return new Point(0, yValue);
        }
        //Case of infinity slope
        return null;
    }
    /**.
     *equals.
     * Checks if two lines are equals
     * We will check if the start and end point of the lines equals
     * @param other the other line we compare to
     * @return return is the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return this.start().equals(other.start()) && this.start().equals(other.end());
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    /**.
     * closestIntersectionToStartOfLine.
     * Checks the closest intersection point between the start point of the line and the rectangle.
     * We will check the minimum distance between the intersection points and the start point
     * @param rect the rectangle.
     * @return the closest intersection point to the start of the line. If this line does not intersect with
       the rectangle, return null.
     */
   public Point closestIntersectionToStartOfLine(Rectangle rect) {
       //The intersections between the line and rectangle.
       List<Point> intersections = rect.intersectionPoints(this);
      // System.out.println("intersections:"+intersections.size());
       //Each distance smaller than this.
        double minDistance = Double.POSITIVE_INFINITY;
        //The closest intersection point. null in case of no intersections.
        Point closestIntersection = null;
        for (int i = 0; i < intersections.size(); i++) {
            //The distance between the start point and this intersection point;
            double distance =  this.start().distance(intersections.get(i));
            //Updating if there is closer point
            if (distance < minDistance) {
                minDistance = distance;
                closestIntersection = intersections.get(i);
            }
        }
        return closestIntersection;
    }
    /**.
     * drawLine.
     * Draws a line on a drawsurface
     * We will get the cordinates from the specific line and use a made function to draw it.
     * @param  colorLine - the color of the line we want to draw.
     * @param  d - A drawface we want to draw the line on.
     */
    public void drawLine(java.awt.Color colorLine, biuoop.DrawSurface d) {
        d.setColor(colorLine);
        //Getting start point x-cordinate
        int  x1 = (int) this.start().getX();
        //Getting end point x-cordinate
        int  x2 = (int) this.end().getX();
        //Getting start point y-cordinate
        int y1 = (int) this.start().getY();
        //Getting end point y-cordinate
        int y2 = (int) this.end().getY();
        //Drawing the line
        d.drawLine(x1, y1, x2, y2);
    }

    /**.
     * drawOn.
     * Draw the sprite to the screen.
     * @param d the drawsurface we will draw the sprite on.
     */
    public void drawOn(DrawSurface d) {
        drawLine(this.color, d);
    }
    /**
     * timePassed.
     * notify the sprite that time has passed.
     *
     * @param dt do nothing on this class.
     */
    public void timePassed(double dt) {
        //Do nothing.
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

