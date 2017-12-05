package game;

import biuoop.DrawSurface;
import listeners.HitListener;
import listeners.HitNotifier;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.awt.Image;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
/**
 * Block
 * The block is represented by rectengle and color of it.
 * The block is going to be something we collide into, so let's start there.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private  List<HitListener> hitListeners;
    private Rectangle rectBlock;
    private Color color;
    //The color of the stroke.
    private Color strokeColor;
    private Image image;
    //Each block should be initialized with a positive number of hit-points.
    // The hit point will be indicated visually on the block
    private int countHits;
    //Maps of background by count hits.
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;

        /**.
     * Creates new instance of Block
     * The constructor of our class Block.
     * @param  block the rectangle that represents block.
     * @param color the color of the block.
     */
    public Block(Rectangle block, Color color) {
        this.rectBlock = block;
        this.color = color;
        //This constructor has color so it will be the block's background.
        this.image = null;
        //Instalizing the hit listeners list.
        this.hitListeners = new ArrayList<HitListener>();
        //Usually the count hits will be 1 and we change it in another options.
        this.countHits = 1;
        //No defined yet stroke color.
        this.strokeColor = null;
        //Initializing the maps.
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
    }

    /**.
     * Creates new instance of Block
     * The constructor of our class Block.
     * @param  block the rectangle that represents block.
     * @param image the background image of the block.
     */
    public Block(Rectangle block, Image image) {
        this.rectBlock = block;
        //This constructor has image so it will be the block's background.
        this.color = null;
        this.image = image;
        //Instalizing the hit listeners list.
        this.hitListeners = new ArrayList<HitListener>();
        //Usually the count hits will be 1 and we change it in another options.
        this.countHits = 1;
        //No defined yet stroke color.
        this.strokeColor = null;
        //Initializing the maps.
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
    }
    /**.
     * setColorMap.
     * Setting the color map
     *
     * @param colorMap a map of colors
     */
    public void setColorMap(Map<Integer, Color> colorMap) {
        if (colorMap != null) {
            this.colors.putAll(colorMap);
        }
    }
    /**.
     * setImageMap.
     * Setting the images map
     *
     * @param imageMap a map of images
     */
    public void setImageMap(Map<Integer, Image> imageMap) {
        if (imageMap != null) {
            this.images.putAll(imageMap);
        }
    }
    /**.
     * setStrokeColor.
     * Setting the color map
     *
     * @param c the color of the stroke.
     */
    public void setStrokeColor(Color c) {
        this.strokeColor = c;
    }
    /**.
     * getWidth.
     * @return the width of the block.
     */
    public double getWidth() {
        return this.rectBlock.getWidth();
    }
    /**.
     * getHeight.
     * @return the height of the block.
     */
    public double getHeight() {
        return this.rectBlock.getHeight();
    }
    /**.
     * setCountHits.
     * Setting the counter of hits of the block.
     * @param counter the number of the new countHits.
     */
    public void setCountHits(int counter) {
        this.countHits = counter;
    }
    //Interface Collidable
    /**.
     * getCollisionRectangle.
     * @return the "collision shape" of the block.
     */
   public Rectangle getCollisionRectangle() {
        return this.rectBlock;
    }

    /**
     * addHitListener
     * Add hl as a listener to hit events.
     *
     * @param hl an HitListener.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * removeHitListener
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl an HitListener.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * notifyHit.
     * Will be called whenever a hit() occurs.
     * And notifiers all of the registered HitListener objects by calling their hitEvent method.
     *
     * @param hitter the ball hitted the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**.
     * hit.
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter the ball hitted the block.
     * @param collisionPoint the point that the ball collided with the block.
     * @param currentVelocity the velocity that the ball collided with the block
     * @return the velocity after the collide.
     */
    public  Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //Current velocity variables.
        Velocity v = currentVelocity;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        //Check if the ball collided on the left rib of the block.
        boolean leftSide = collisionPoint.getX() == this.rectBlock.getUpperLeft().getX();
       //Check if the ball collided on the right rib of the block.
        boolean rightSide = collisionPoint.getX() == this.rectBlock.getUpperRight().getX();
       //Check if the ball collided on the top rib of the block.
        boolean top = collisionPoint.getY() == this.rectBlock.getUpperLeft().getY();
       //Check if the ball collided on the bottom rib of the block.
        boolean down = collisionPoint.getY() == this.rectBlock.getLowerRight().getY();
        //The ball collided on the left or right rib so it changes his x direction.
        if (leftSide || rightSide) {
            dx = -dx;
            //v = new Velocity(dx, dy);
        }
       //The ball collided on the top or bottom rib so it changes his y direction.
        if (top || down) {
            dy = -dy;
           // v = new Velocity(dx, dy);
        }
        // update the Collidable interface accordingly.
        this.notifyHit(hitter);

        //Each time the ball hits the block the counter of hits will be decreased.
        if (this.countHits > 0) {
            this.countHits--;
        }
        //Return the new velocity.
        return new Velocity(dx, dy);
    }

    //Interface sprite
    /**.
     * drawOn.
     * Draw the block to the screen with the counter of hits.
     * We will use drawrectangle function that we already created.
     * @param d the drawsurfce.
     */
    public void drawOn(DrawSurface d) {
        Rectangle rectangle = this.getCollisionRectangle();
        Color colorBlock = null;
        Image imageBlock = null;
        //Get the color from the map if exists from the map.
        if (this.colors.containsKey(this.countHits)) {
            colorBlock = this.colors.get(this.countHits);
        } else if (this.images.containsKey(this.countHits)) { //Get the image from the map if exists from the map.
            imageBlock = this.images.get(this.countHits);
        } else { //Not from the map
             colorBlock = this.color;
             imageBlock = this.image;
        }

        //Default values.
        if (colorBlock != null) {
            rectangle.drawRectangle(colorBlock, d);
        }
        if (imageBlock != null) {
            d.drawImage((int) rectangle.getUpperLeft().getX(), (int) rectangle.getUpperLeft().getY(), imageBlock);
        }

        if (strokeColor != null) {
            rectangle.drawStroke(strokeColor, d);
        }

    }


    /**.
     * timePassed.
     * Notify the sprite that time has passed.
     * For the block, currently we do nothing.
     *
     * @param dt do nothing on this class.
     */
    public void timePassed(double dt) {
        //Do nothing on block.
        return;
    }
    /**
     * addToGame.
     * This method will be in charge of adding the block to the game.
     * @param g the game we want to add the block to.
     */
    public void addToGame(GameLevel g) {
        //Block is a Sprite and Collideable.
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * removeFromGame.
     * This method will be in charge of removing the block from the gameLevel.
     *
     * @param gameLevel the gameLevel we want to remove the block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        //Removing it from the collidables collection.
        gameLevel.removeCollidable(this);
        //Removing it from the sprites collection.
        gameLevel.removeSprite(this);
    }
    /**
     * getHitPoints.
     * @return the hit points remained to hit the block.
     */
    public int getHitPoints() {
        return this.countHits;
    }
    /**
     * copy.
     * Copy the block.
     * @return block same as our block.
     */
    public Block copy() {
        Block block = null;
        //Has image.
        if (this.color == null) {
            block = new Block(this.rectBlock, this.image);
        }
        //Has color
        if (this.image == null) {
            block = new Block(this.rectBlock, this.color);
        }
        //Has colors.
        if (!this.colors.isEmpty()) {
            block.setColorMap(this.colors);
        }
        //Has images.
        if (!this.images.isEmpty()) {
            block.setImageMap(this.images);
        }
        //Has stroke color.
        if (this.strokeColor != null) {
            block.setStrokeColor(this.strokeColor);
        }
        block.setCountHits(this.getHitPoints());

        return block;
    }






}
