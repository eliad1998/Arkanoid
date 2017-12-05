package io;

import creators.BlockFactory;
import creators.BlocksFromSymbolsFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;

import java.awt.Color;
import java.awt.Image;

/**
 * BlocksDefinitionReader.
 * In charge of reading a block-definitions file and returning a BlocksFromSymbolsFactory object.
 */
public class BlocksDefinitionReader {
    /**.
     * BlocksFromSymbolsFactory.
     * These symbols are then used in the level specification files to define the blocks that need to be created.
     * You will thus need a mechanism (object) with a method that will get a symbol and create the desired block.
     *
     * @param reader a reader (for the file of the block defines).
     * @return block symbols factory by the file definitions.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        //Initializing the factory.
        BlocksFromSymbolsFactory bfsFactory = new BlocksFromSymbolsFactory();
        BufferedReader linesReader = new BufferedReader(reader);
        String line = "";
        try {
            //All the block's parameters.
            int height = 0;
            int width = 0;
            int hitPoints = 0;
            Color color = null;
            Color stroke = null;
            Image image = null;
            String symbol = "";
            Map<Integer, Color> colorMap;
            Map<Integer, Image> imageMap;
            //Default values.
            int defHeight = 0;
            int defWidth = 0;
            int defHitPoints = 0;
            Color defColor = null;
            Color defStroke = null;
            Image defImage = null;
            Map<Integer, Color> defColorMap = new TreeMap<Integer, Color>();
            Map<Integer, Image> defImageMap = new TreeMap<Integer, Image>();
            line = linesReader.readLine();
            while (line != null) { //Moving on the lines.
                //The line isn't empty or a definition.
                if (!ignoredLine(line)) {
                    BlockFromMap parser = new BlockFromMap(definitions(line, ':'));
                    if (correctLine(line)) {
                        //Setting default values.
                        if (words(line).get(0).equals("default")) {
                            defHeight = parser.getHeight();
                            defWidth = parser.getWidth();
                            defHitPoints = parser.getHitPoints();
                            defColor = parser.getColor();
                            defImage = parser.getImage();
                            defImageMap.putAll(parser.getImages());
                            defColorMap .putAll(parser.getColors());
                            defStroke = parser.getStroke();
                        }
                        //Symbol.
                        //BDEF and SDEF has symbol and width..
                        symbol = parser.getSymbol();

                        //Width.
                        width = parser.getWidth();
                        //No width in the line.
                        if (width < 0) {
                            width = defWidth;
                        }
                        //Block definitions.
                        if (words(line).get(0).equals("bdef")) {
                            //Height.
                            height = parser.getHeight();
                            //No height in the line.
                            if (height < 0) {
                                height = defHeight;
                            }
                            //Hit points.
                            hitPoints = parser.getHitPoints();
                            //No hit points in the line.
                            if (hitPoints < 0) {
                                hitPoints = defHitPoints;
                            }
                            //Image.
                            image = parser.getImage();
                            //No image in the line.
                            if (image == null) {
                                image = defImage;
                            }
                            //Stroke.
                            stroke = parser.getStroke();
                            //No stroke in the line.
                            if (stroke == null) {
                                stroke = defStroke;
                            }
                            //Color.
                            color = parser.getColor();
                            //No color in the line
                            if (color == null) {
                                color = defColor;
                            }
                            //Images fill-k
                            imageMap = parser.getImages();
                            //No images in the map.
                            if (imageMap.isEmpty()) {
                                imageMap.putAll(defImageMap);
                            }
                            //Colors fill - k
                            colorMap = parser.getColors();
                            //No colors in the map.
                            if (colorMap.isEmpty()) {
                                colorMap.putAll(defColorMap);
                            }
                            //No null or empty values.
                            if ((height < 0 || hitPoints < 0 || width < 0 || symbol.length() == 0)
                                    && (image == null || color == null
                                    || imageMap.isEmpty() || colorMap.isEmpty())) {
                                throw new RuntimeException("Block definition problem.");
                            } else {
                                //New block factory.
                                BlockFactory blockFactory = new BlockFactory(height, width, hitPoints);
                                //Setting the image and color.
                                blockFactory.setDefaultColor(color);
                                blockFactory.setDefaultImage(image);
                                //Setting the maps.
                                blockFactory.setColors(colorMap);
                                blockFactory.setImages(imageMap);
                                //Has stroke.
                                if (stroke != null) {
                                    blockFactory.setStroke(stroke);
                                }
                                //Adding the block creator.
                                bfsFactory.addBlockCreator(symbol, blockFactory);
                            }
                        }
                        //Space definitions.
                        if (words(line).get(0).equals("sdef")) {
                            //Add the spacer to the factory.
                            bfsFactory.addSpacer(symbol, width);
                        }
                    }
                }
                line = linesReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Problems reading the file");
        }
        return bfsFactory;
    }
    /**.
     * correctLine.
     * @param line a line in the file.
     * @return true if the line is correct by the instructions, false otherwise.
     */
    private static boolean correctLine(String line) {
        //Checking it if the line isnt an ignored line.
        if (!ignoredLine(line)) {
            //Has unwanted keys.
            if (unwantedKeys(line)) {
                return false;
            }
            List<String> list = words(line);
            //Each key and value are ok.
            for (String word : list) {
                if (!BlockDefinitionHelper.correctFirstWord(word)) {
                    if (!BlockDefinitionHelper.keyValue(word)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    /**.
     * ignoredLine.
     * Line we have to ignore is line starts with '#' or empty line.
     *
     * @param line our line in the text.
     * @return true if we have to ignore the line, false otherwise.
     */
    private static boolean ignoredLine(String line) {
        int x = line.length();

        if (x == 0) {
            return true;
        } else { //There is a char in the first position.
            //A definition.
            if (line.charAt(0) == '#') {
                return true;
            }
        }
        return false;
    }


    /**.
     * unwantedKeys.
     * Checks if the line contains unwanted keys.
     * We will check if we have key that isn't in thel ist.
     *
     * @param line our line in the text.
     * @return true if there are unwanted keys, false otherwise.
     */
    private static boolean unwantedKeys(String line) {
        //True because we ignore this line.
        if (ignoredLine(line)) {
            return true;
        }
        //Cases we don't ignore the line.
        List<String> list = words(line);
        String word = list.get(0);
        Map<String, String> map = definitions(line, ':');
        List<String> keys = new ArrayList<String>(map.keySet());
        //First word isn't bdef or default or sdef
        if (!BlockDefinitionHelper.correctFirstWord(word)) {
            return true;
        }
        String [] arr = {"symbol", "height", "width", "hit_points", "fill", "stroke", "symbol", "width"};
        //Showing the keys as list.
        List wantedKeys = new ArrayList<>(Arrays.asList(arr));

        for (String key : keys) {
            if (!wantedKeys.contains(key) && !BlockDefinitionHelper.isFillK(key)) {
                return true;
            }
        }

        return false;
    }
    /**.
     * words.
     * Separates string to list of words by spaces.
     *
     * @param line a line of words.
     * @return list of the words separated by spaces.
     */
    public static List<String> words(String line) {
        List<String> words = new ArrayList<String>();
        //A string builder.
        StringBuilder stringBuilder = new StringBuilder();
        //Moving on the characters.
        for (int i = 0; i < line.length(); i++) {
            //Spaces.
            if (line.charAt(i) == ' ') {
                words.add(stringBuilder.toString());
                //Reset string builder.
                stringBuilder = new StringBuilder();
            } else { //Regular characters.
                //Adding the char to the string.
                stringBuilder.append(line.charAt(i));
            }
        }
        //Adding the last word.
        if (stringBuilder.toString().length() != 0) {
            words.add(stringBuilder.toString());
        }
        return words;
    }
    /**
     * definitions.
     * Separates a list of string of words of each line in struct of block definition file.
     * We wil seperate it to map with key and value of block properties.
     *
     * @param line a line of words.
     * @param delimeter the char we separate the strings.
     * @return map with key and value of block's properties.
     */
    public static Map<String, String> definitions(String line, Character delimeter) {
        List<String> words = words(line);
        Map<String, String> definitions = new TreeMap<String, String>();
        String key = "";
        String value = "";
        StringBuilder stringBuilder = new StringBuilder();
        //Moving on the characters.
        for (String word: words) {
            key = "";
            value = "";
            stringBuilder = new StringBuilder();
            //Not block or spacer definitions or default definition.
            if (!BlockDefinitionHelper.correctFirstWord(word)) {
                for (int i = 0; i < word.length(); i++) {
                    //Spaces.
                    if (word.charAt(i) == delimeter) {
                        key = stringBuilder.toString();
                        //Reset string builder.
                        stringBuilder = new StringBuilder();
                    } else { //Regular characters.
                        //Adding the char to the string.
                        stringBuilder.append(word.charAt(i));
                    }
                }
                //Adding the last word.
                if (stringBuilder.toString().length() != 0) {
                    value = stringBuilder.toString();
                }
                //Putting the key and value to the map.
                definitions.put(key, value);
            }
        }
        return  definitions;
    }

}
