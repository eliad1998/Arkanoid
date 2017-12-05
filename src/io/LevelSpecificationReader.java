package io;

import creators.BlocksFromSymbolsFactory;
import game.Block;
import game.Sprite;
import game.Velocity;
import levels.Background;
import levels.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**.
 * LevelSpecificationReader.
 * This class helps us reading level definitions form file.
 */
public class LevelSpecificationReader {
    //Each level information.
    private String line = "";
    private String levelName = "";
    private List<Velocity> ballVelocities = null;
    private Background background = null;
    private int paddleSpeed = 0;
    private int paddleWidth = 0;
    private BlocksFromSymbolsFactory blocksFromSymbolsFactory = null;
    private int blocksStartX = 0;
    private int blocksStartY = 0;
    private int rowHeight = 0;
    private int numBlocks = 0;
    private List<Block> blocks = null;
    //Current x and y values of block upper point.
    private int x = 0;
    private int y = 0;
    /**.
     * fromReader.
     * @param reader a reader to file that has the definitions of the levels.
     * @return list of level informations that describes in the file.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        //Initializing the factory.
        List<LevelInformation> levelInformationList = new ArrayList<LevelInformation>();
        BufferedReader linesReader = new BufferedReader(reader);
        boolean levelReading = false;
        boolean blockReading = false;
        try {
            //Reading the first line.
            line = linesReader.readLine();
            while (line != null) { //Moving on the lines.
                //The line isn't empty or a definition.
                if (!ignoredLine(line)) {
                    //Starting reading level.
                    if (line.equals("START_LEVEL")) {
                        //Reset the level parameters.
                        resetParameters();
                        levelReading = true;
                    }
                    //Finished reading the level.
                    if (line.equals("END_LEVEL")) {
                        levelReading = false;
                        //All parameters are valid
                        if (allParametersValid()) {
                            //Adding the level to the levels list.
                            LevelInformation level = createLevel(ballVelocities, paddleSpeed, paddleWidth
                                    , levelName, background, blocks, numBlocks);
                            levelInformationList.add(level);
                        } else { //Problem with definitions.
                            throw new RuntimeException("Problem with definitions of level parameters.");
                        }
                        }
                        //Adding the new level into the list.
                    }
                    //Getting the block positions.
                    if (line.equals("START_BLOCKS")) {
                        if (!allParametersValid()) {
                        throw new RuntimeException("Problem with level's arguments.");
                       }
                        blockReading = true;
                        //If we read blocks so we finished to reading level.
                        levelReading = false;
                        //Initializing the block list.
                        blocks = new ArrayList<Block>();
                    }
                    //End of reading the block positions.
                    if (line.equals("END_BLOCKS")) {
                        blockReading = false;
                    }
                    //Reading level definitions.
                    if (levelReading) {
                    //Setting the level definitions by this line.
                        setLevelByLine(line);
                    }
                    //Adding the blocks to the list.
                    if (blockReading) {
                    //Reset the x point.
                        x = blocksStartX;
                         for (int i = 0; i < line.length(); i++) {
                             //Block from symbols factory is valid because we finished reading the level.
                             //Block symbol
                             String symbol = line.charAt(i) + "";
                             if (blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                                 Block block = blocksFromSymbolsFactory.getBlock(symbol, x, y);
                                 blocks.add(block);
                                 //Adding the width of the block.
                                 x += block.getWidth();
                             }
                             if (blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                                 //Adding the space width.
                                 x += blocksFromSymbolsFactory.getSpaceWidth(symbol);
                             }

                         }
                         //Adding row height to y.
                        y += rowHeight;
                    }
                //Reading the next line.
                line = linesReader.readLine();
                }
            }  catch (IOException e) {
                   throw new RuntimeException("Problems reading the file");
        }
        return levelInformationList;
    }
    /**.
     * createLevel.
     * Creates level information by it's variables.
     *
     * @param velocities the ball's velocities.
     * @param speed the speed of the paddle.
     * @param width the width of the paddle.
     * @param name the name of the level.
     * @param b the backgrounnd of the level.
     * @param blockList a list of the blocks in this level.
     * @param number the number of the blocks in this level.
     * @return level information object with those variables.
     */
    private LevelInformation createLevel(List<Velocity> velocities, int speed, int width
            , String name, Sprite b, List<Block> blockList, int number) {
        //An annonymous class of level information.
        LevelInformation level = new LevelInformation() {
            //Getting the number of the balls.
            @Override
            public int numberOfBalls() {
                //The ball numbers eqials to number in ball velocity list.
                return velocities.size();
            }
            //Gets a list of balls' velocities.
            @Override
            public List<Velocity> initialBallVelocities() {
                return velocities;
            }
            //Gets the speed of the paddle.
            @Override
            public int paddleSpeed() {
                return speed;
            }
            //Gets the width of the paddle.
            @Override
            public int paddleWidth() {
                return width;
            }
            //Gets the name of the level.
            @Override
            public String levelName() {
                return name;
            }
            //Gets the background of the level.
            @Override
            public Sprite getBackground() {
                return b;
            }
            //Gets a list of the blocks in this level.
            @Override
            public List<Block> blocks() {
                return blockList;
            }
            //Gets the number of blocks we want to remove.
            @Override
            public int numberOfBlocksToRemove() {
                return number;
            }
        };

        return level;
    }
    /**.
     * allParametersValid.
     * Valid parameters are for example width that bigger than 0, and ball velocities list that isn't null.
     * @return true if all the parameters of this level valid, false otherwise.
     */
    private boolean allParametersValid() {
        return levelName.length() > 0 && background != null && ballVelocities != null
                && paddleSpeed >= 0 && paddleWidth >= 0 && blocksFromSymbolsFactory != null
                && blocksStartX >= 0 && blocksStartY >= 0 && rowHeight >= 0 && numBlocks >= 0;
    }
    /**.
     * resetParameters.
     * Resets the level parameters for the next level.
     */
    private void resetParameters() {
        levelName = "";
        ballVelocities = null;
        background = null;
        paddleSpeed = 0;
        paddleWidth = 0;
        blocksFromSymbolsFactory = null;
        blocksStartX = 0;
        blocksStartY = 0;
        rowHeight = 0;
        numBlocks = 0;
        blocks = null;
    }
    /**.
     * setLevelByLine.
     * Setting the level parameters by the current line.
     * For example setting the level name by the line : level_name:Square Moon.
     * @param l a line in the text file.
     */
    private void setLevelByLine(String l) {
        Map<String, String> definitions = BlocksDefinitionReader.definitions(l, ':');
        LevelFromMap levelFromMap = new LevelFromMap(definitions);
        //Level name.
        if (levelFromMap.getLevelName(l).length() > 0) {
            levelName = levelFromMap.getLevelName(l);
        }
        //Background.
        if (levelFromMap.getBackground() != null) {
            background = levelFromMap.getBackground();
        }
        //Ball velocities
        if (levelFromMap.getVelocities(l) != null) {
            ballVelocities = levelFromMap.getVelocities(l);
        }
        //Paddle speed.
        if (levelFromMap.getPaddleSpeed() >= 0) {
            paddleSpeed = levelFromMap.getPaddleSpeed();
        }
        //Paddle width
        if (levelFromMap.getPaddleWidth() >= 0) {
            paddleWidth = levelFromMap.getPaddleWidth();
        }
        //Block symbols factory.
        if (levelFromMap.getBlockDefinitions() != null) {
                blocksFromSymbolsFactory = levelFromMap.getBlockDefinitions();
        }
        //Block start x cordinate
        if (levelFromMap.getBlockStartX() >= 0) {
            blocksStartX = levelFromMap.getBlockStartX();
            //Setting the first x to be it.
            x = blocksStartX;
        }
        //Block start y cordinate
        if (levelFromMap.getBlockStartY() >= 0) {
            blocksStartY = levelFromMap.getBlockStartY();
            //Setting the first y to be it.
            y = blocksStartY;
        }
        //The row height
        if (levelFromMap.getRowHeight() >= 0) {
            rowHeight = levelFromMap.getRowHeight();
        }
        //The number of blocks.
        if (levelFromMap.getNumBlocks() >= 0) {
            numBlocks = levelFromMap.getNumBlocks();
        }
    }
    /**.
     * ignoredLine.
     * Ignored line is an empty line or line starts with '#'.
     *
     * @param l a line in the text file.
     * @return true if this line is ignored, else otherwise.
     */
    private boolean ignoredLine(String l) {
        //Type of lines we ignore.
        if (l.equals("") || l.startsWith("#")) {
            return true;
        }
        return false;
    }
}