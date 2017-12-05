package creators;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**.
 * ColorImageParser.
 * Parse color from string.
 */
public class ColorImageParser {
    /**.
     * colorFromString.
     * Parse color definition.
     *
     * @param s a string that represents a color.
     * @return  the specified color.
     */
    public static java.awt.Color colorFromString(String s) {
        //RGB
       // if(!s.contains(","))
        //Color name.
        if (!s.contains(",")) {
            Map<String, Color> colorNames = new TreeMap<String, Color>();
            colorNames.put("black", Color.black);
            colorNames.put("blue", Color.blue);
            colorNames.put("cyan", Color.cyan);
            colorNames.put("gray", Color.gray);
            colorNames.put("lightGray", Color.lightGray);
            colorNames.put("green", Color.green);
            colorNames.put("orange", Color.orange);
            colorNames.put("pink", Color.pink);
            colorNames.put("red", Color.red);
            colorNames.put("white", Color.white);
            colorNames.put("yellow", Color.yellow);

            return colorNames.get(s);
        } else { //RGB
            int [] rgb = new int[3];
            int indexStart = s.indexOf('(') + 1;
            int indexEnd = s.indexOf(',', indexStart);
            for (int i = 0; i < rgb.length; i++) {
                rgb[i] = Integer.parseInt(s.substring(indexStart, indexEnd));
                indexStart = indexEnd + 1;
                if (i != 1) {
                    indexEnd = s.indexOf(",", indexStart);
                } else { //The end is end of the string and we after the last ,
                    indexEnd = s.length() - 1;
                }
            }
            //Return color from rgb
            return new Color(rgb[0], rgb[1], rgb[2]);

        }
    }

    /**.
     * imageFromText.
     * Return image by string of file path.
     *
     * @param path a string that represents an image directory.
     * @return  the image in the file or null if has problems.
     */
    public static Image imageFromText(String path) {
        Image img = null;
        try {
            //Return the image.
            return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
        } catch (IOException e) { //Problems reading the file.
            return null;
        }
    }

}
