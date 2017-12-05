package io;


import creators.BlocksFromSymbolsFactory;
import creators.ColorImageParser;
import game.Block;
import game.Velocity;
import levels.Background;
import levels.Level;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * LevelkFromMap.
 * In this class we assume the input is ok so we get map of strings that contains definitions.
 * We will get the variables by this map we have.
 */
public class LevelFromMap {
    private Map<String, String> defines;
    /**
     * Create new instance of LevelFromMap.
     * The constructor of our class.
     * @param defines a map contains definitions of level.
     */
    public LevelFromMap(Map<String, String> defines) {
        this.defines = defines;
    }
    /**
     * getLevelName.
     * Gets a line because the map is sepparate the line to spaces and the level name can have spaces.
     *
     * @param line the line in the file.
     * @return the name of the leve.
     */
    public String getLevelName(String line) {
        //No exists in the map.
        if (this.defines.get("level_name") == null) {
            return "";
        }
        return line.substring(("level_name").length() + 1, line.length());
    }
    /**
     * getVelocities.
     * Gets a line because we want to sepparate the ball velocities to map with angle:speed.
     *
     * @param line the line in the file.
     * @return list of the velocities of the ball in the game.
     */
    public List<Velocity> getVelocities(String line) {
        if (this.defines.get("ball_velocities") == null) {
            return null;
        }
        //Initializing the ball velocities list.
        List<Velocity> ballVelocities = new ArrayList<Velocity>();
        String strVelocities = line.substring(("ball_velocities:").length(), line.length());
        //Splitting the string by spaces and ,.
        String[]sepparated = strVelocities.split(" |\\,");
        for (int i = 0; i < sepparated.length - 1; i += 2) {
            //Fixing angle.
            ballVelocities.add(Velocity.fromAngleAndSpeed(180 - Integer.parseInt(sepparated[i])
                    , Integer.parseInt(sepparated[i + 1])));
        }
        return ballVelocities;
    }
    /**
     * getBackground.
     * @return the background of the level by the map.
     */
    public Background getBackground() {
        //Didn't found.
        if (this.defines.get("background") == null) {
            return null;
        }
        //Initializing the background.
        Background background = new Background();
        String strFill = this.defines.get("background");
        String strType = strFill.substring(0, 5);
        //Case of color background.
        if (strType.equals("color")) {
            //The string defines color.
            String colorStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length()  - 1);
            //Adding the background color.
            Block innerScreen = new Block(Level.gameRectangle(), ColorImageParser.colorFromString(colorStr));
            background.addSprite(innerScreen);
        }
        //Case of image background.
        if (strType.equals("image")) {
            //The string defines image.
            String imageStr = strFill.substring(strFill.indexOf('(') + 1, strFill.length() - 1);
            //Adding the background image.
            Block innerScreen = new Block(Level.gameRectangle(), ColorImageParser.imageFromText(imageStr));
            background.addSprite(innerScreen);
        }
        return background;
    }
    /**
     * getPaddleSpeed.
     * @return the paddle speed of the level by the map.
     * -1 if there is a problem.
     */
    public int getPaddleSpeed() {
        //Didn't found so we return -1 can't be this.
        if (this.defines.get("paddle_speed") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("paddle_speed"));
    }
    /**
     * getPaddleWidth.
     * @return the paddle width of the level by the map.
     * -1 if there is a problem.
     */
    public int getPaddleWidth() {
        //Didn't found so we return -1 can't be this.
        if (this.defines.get("paddle_width") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("paddle_width"));
    }
    /**
     * getBlockDefinitions.
     * @return the block and spaces definitions of the leve by the map.
     */
    public BlocksFromSymbolsFactory getBlockDefinitions() {
        if (this.defines.get("block_definitions") == null) {
            return null;
        }
        try {
            //Getting the block definitinos by our file reader.
            String path = this.defines.get("block_definitions");
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
            return BlocksDefinitionReader.fromReader(new InputStreamReader(is));
        } catch (Exception e) { //Problems with reading the file.
            throw new RuntimeException("Problem reading the file");
        }
    }
    /**
     * getBlockStartX.
     * @return the start x cordinate of the blocks in that level from the map.
     * -1 if there is a problem.
     */
    public int getBlockStartX() {
        //Didn't found so we return -1 can't be this.
        if (this.defines.get("blocks_start_x") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("blocks_start_x"));
    }
    /**
     * getBlockStartY.
     * @return the start y cordinate of the blocks in that level from the map.
     * -1 if there is a problem.
     */
    public int getBlockStartY() {
        //Didn't found so we return -1 can't be this.
        if (this.defines.get("blocks_start_y") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("blocks_start_y"));
    }
    /**
     * getRowHeight.
     * @return the row height of the blocks in this level from the map.
     * -1 if there is a problem.
     */
    public int getRowHeight() {
        //Didn't found so we return -1 can't be height.
        if (this.defines.get("row_height") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("row_height"));
    }
    /**
     * getRowHeight.
     * @return the number of the blocks in this level from the map.
     * -1 if there is a problem.
     */
    public int getNumBlocks() {
        //Didn't found so we return -1 can't be this.
        if (this.defines.get("num_blocks") == null) {
            return -1;
        }
        return Integer.parseInt(this.defines.get("num_blocks"));
    }
















}
