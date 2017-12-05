package creators;

import game.Block;
import geometry.Point;
import geometry.Rectangle;

import java.awt.Image;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * BlockFactory.
 * Creating blocks with the same background, height and width but in different positions.
 */
public class BlockFactory implements BlockCreator {
    private int height;
    private int width;
    private Color color;
    private Image image;
    private int hitPoints;
    private Color stroke;
    //Maps of background by count hits.
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;
    /**.
     * Creates new instance of BlockFactory
     * The constructor of our class.
     *
     * @param height height of the block.
     * @param width the width of the block.
     * @param color the color of the block.
     * @param hitPoints the hit points need to destroy the block.
     */
    public BlockFactory(int height, int width, int hitPoints, Color color) {
        this.height = height;
        this.width = width;
        this.color = color;
        this.hitPoints = hitPoints;
        //No image background so null.
        this.image = null;
        //Initizalizing the maps.
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
    }
    /**.
     * Creates new instance of BlockFactory
     * The constructor of our class.
     * @param height height of the block.
     * @param width the width of the block.
     * @param image the background image of the block.
     * @param hitPoints the hit points need to destroy the block.
     */
    public BlockFactory(int height, int width, int hitPoints, Image image) {
        this.height = height;
        this.width = width;
        this.image = image;
        this.hitPoints = hitPoints;
        //No color background so null.
        this.color = null;
        //Initizalizing the maps.
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
    }

    /**.
     * Creates new instance of BlockFactory
     * The constructor of our class.
     * @param height height of the block.
     * @param width the width of the block.
     * @param hitPoints the hit points need to destroy the block.
     */
    public BlockFactory(int height, int width, int hitPoints) {
        this.height = height;
        this.width = width;
        this.hitPoints = hitPoints;
        //No color background so null.
        this.color = null;
        //No image backgorund so null.
        this.image = null;
        //Initizalizing the maps.
        this.colors = new TreeMap<Integer, Color>();
        this.images = new TreeMap<Integer, Image>();
    }
    /**.
     * setDefaultColor.
     * Setting the color of the block.
     * @param c a color.
     */
    public void setDefaultColor(Color c) {
        if (c != null) {
            this.color = c;
        }
    }
    /**.
     * setDefaultImage.
     * Setting the image of the block.
     * @param img an image.
     */
    public void setDefaultImage(Image img) {
        if (img != null) {
            this.image = img;
        }
    }
    /**.
     * setStroke.
     * Setting the stroke color of the block.
     * @param c a color.
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }
    /**.
     * setColors.
     * Setting the colors map.
     * @param colorMap a color map
     */
    public void setColors(Map<Integer, Color> colorMap) {
            this.colors.putAll(colorMap);
    }
    /**.
     * setColors.
     * Setting the image map.
     * @param imageMap an image map
     */
    public void setImages(Map<Integer, Image> imageMap) {
        this.images.putAll(imageMap);
    }
    /**.
     * create.
     * Create a block at the specified location.
     *
     * @param xpos the x position of the top left point.
     * @param ypos the y position of the top left point.
     * @return the block we just created.
     */
    public Block create(int xpos, int ypos) {
        Block block = null;
        //Color background.
        if (this.image == null) {
            block = new Block(new Rectangle(new Point(xpos, ypos), this.width, this.height), this.color);
        }
        //Image background.
        if (this.color == null) {
            block = new Block(new Rectangle(new Point(xpos, ypos), this.width, this.height), this.image);
        }
        if (!this.colors.isEmpty()) {
                block.setColorMap(this.colors);
        }
        if (!this.images.isEmpty()) {
            block.setImageMap(this.images);

        }

        if (this.stroke != null) {
            block.setStrokeColor(this.stroke);
        }
        block.setCountHits(this.hitPoints);
        return block;
    }
}
