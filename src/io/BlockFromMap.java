package io;

import creators.ColorImageParser;

import java.awt.Image;
import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * BlockFromMap.
 * In this class we assume the input is ok so we get map of strings that contains definitions.
 * We will get the variables by this map we have.
 */
public class BlockFromMap {
    private Map<String, String> defines;
    /**
     * Create new instance of BlockFromMap.
     * The constructor of our class.
     * @param defines a map contains definitions of block.
     */
    public BlockFromMap(Map<String, String> defines) {
        this.defines = defines;
    }
    /**
     * getHeight.
     * @return the height of the block by the map.
     * -1 if there is a problem.
     */
    public int getHeight() {
        //Didn't found so we return -1 can't be height.
        if (this.defines.get("height") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("height"));

    }
    /**
     * getWidth.
     * @return the width of the block by the map.
     * -1 if there is a problem.
     */
    public int getWidth() {
        //Didn't found so we return -1 can't be width.
        if (this.defines.get("width") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("width"));
    }
    /**
     * getHitPoints.
     * @return the hit points number of the block by the map.
     * -1 if there is a problem.
     */
    public int getHitPoints() {
        //Didn't found so we return -1 can't be hit points.
        if (this.defines.get("hit_points") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("hit_points"));
    }
    /**
     * getSymbol.
     * @return the symbol of the block by the map.
     */
    public String getSymbol() {
        //No exists in the map.
        if (this.defines.get("symbol") == null) {
            return "";
        }
        return this.defines.get("symbol");
    }
    /**
     * getColor.
     * @return the Color of the block by the map.
     */
    public Color getColor() {
        //Didn't found.
        if (this.defines.get("fill") == null) {
            return null;
        }
        //String from fill to the color.
        String strFill = this.defines.get("fill");
        String strType = strFill.substring(0, 5);
        //Check if contains color.
        if (strType.equals("color")) {
            //Getting the color by the string.
            String colorStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
            return ColorImageParser.colorFromString(colorStr);
        }
        //Not a color
        return null;
    }
    /**
     * getStroke.
     * @return the Color of the block's stroke by the map.
     */
    public Color getStroke() {
        //Didn't found.
        if (this.defines.get("stroke") == null) {
            return null;
        }
        //String from stroke to the color.
        String strFill = this.defines.get("stroke");
        String strType = strFill.substring(0, 5);
        //Check if color.
        if (strType.equals("color")) {
            //Return the color by the string.
            String colorStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
            return ColorImageParser.colorFromString(colorStr);
        }
        //Not a color
        return null;
    }
    /**
     * getImage.
     * @return the image of the block by the map.
     */
    public Image getImage() {
        //Didn't found.
        if (this.defines.get("fill") == null) {
            return null;
        }
        //String from fill to the image.
        String strFill = this.defines.get("fill");
        String strType = strFill.substring(0, 5);
        //Check if has image.
        if (strType.equals("image")) {
            //Getting the image from the string.
            String imageStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
            return ColorImageParser.imageFromText(imageStr);
        }
        //Not an image.
        return null;
    }
    /**
     * getColors.
     * @return the map of the colors by hit points of this block.
     */
    public Map<Integer, Color> getColors() {
        Map<Integer, Color> colors = new TreeMap<Integer, Color>();
        //Getting the hit points of the block.
        int hitPoints = getHitPoints();
        //Moving all of the hit points checking if there is color to them.
        for (int i = 1; i <= hitPoints; i++) {
            if (this.defines.get("fill-" + i) != null) {
                String strFill = this.defines.get("fill-" + i);
                String strType = strFill.substring(0, 5);
                //Color type.
                if (strType.equals("color")) {
                    String colorStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
                    //Putting the color into the map.
                    colors.put(i, ColorImageParser.colorFromString(colorStr));
                }
            }
        }
        return colors;
    }
    /**
     * getImages.
     * @return the map of the images by hit points of this block.
     */
    public Map<Integer, Image> getImages() {
        Map<Integer, Image> images = new TreeMap<Integer, Image>();
        //Getting the hit points of the block.
        int hitPoints = getHitPoints();
         //Moving all of the hit points checking if there is color to them.
        for (int i = 1; i <= hitPoints; i++) {
            //Didn't found.
            if (this.defines.get("fill-" + i) != null) {
                String strFill = this.defines.get("fill-" + i);
                String strType = strFill.substring(0, 5);
                //Image type.
                if (strType.equals("image")) {
                    //Putting the image into the map and converting the string to image.
                    String imageStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
                    images.put(i, ColorImageParser.imageFromText(imageStr));
                }
            }
        }
        return images;
    }






}
