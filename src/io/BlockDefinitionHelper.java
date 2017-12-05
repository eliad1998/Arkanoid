package io;

/**.
 * BlockDefinitionHelper.
 * This class has functinons helps us with checking the strings key and value of the line.
 */
public class BlockDefinitionHelper {

    /**.
     * correctFirstWord.
     * Correct first word is the string is bdef or default or sdef.
     * Any string of definition should start with that word.
     *
     * @param word the input string.
     * @return true if it correct for first word, false otherwise.
     */
    public static boolean correctFirstWord(String word) {
        return word.equals("bdef") || word.equals("default") || word.equals("sdef");

    }
    /**.
     * numExpression.
     * @return regular expression for numbers.
     */
    public static String numExpression() {
        return "-?\\d+(\\.\\d+)?";
    }
    /**.
     * numExpression.
     * @return regular expression for single chars (not including spaces).
     */
    public static String charExpression() {
        //No spaces.
        return "[^\\s]";
    }
    /**.
     * isFillK.
     * @param str the input string.
     * @return true if the string is in the struct fill-k , k is a number.
     */
    public static boolean isFillK(String str) {
        //match a number with optional '-' and decimal.
        //In other case we check if the string s of the constant.
        return str.matches("fill-" + "-?\\d+(\\.\\d+)?");
    }
    /**.
     * fillStrokeName.
     *
     * @param fillStroke is fill or stroke.
     * @param str the input string.
     * @return true if the string in the form fill:color(color_name) or stroke:color(color_name)
     */
    public static boolean fillStrokeName(String fillStroke, String str) {
        //Not starts with fill or stroke.
        if (!(fillStroke.equals("fill") || fillStroke.equals("stroke"))) {
            return false;
        }
        //Colors string.
        String [] colors = {"black", "blue", "cyan", "gray", "lightGray", "green", "orange", "pink", "red", "white"
                , "yellow" };
        //Fill color by name.
        boolean fillName = false;
        //Check if If it one of the colors.
        for (int i = 0; i < colors.length; i++) {
            fillName |= str.equals(fillStroke + ":color(" + colors[i] + ")");
        }

        return fillName;
    }

    /**.
     * fillKName.
     *
     * @param str the input string.
     * @return true if the string in the form fill-k:color(colorName) where k is number and colorName is one of the
     * colors black, blue, cyan, gray, lightGray, green, orange, pink, red, white, yellow.
     */
    public static boolean fillKName(String str) {
        //Hasn't ':'
        if (str.indexOf(':') < 0) {
            return false;
        }
        //The section of fill-k.
        String fillK = str.substring(0, str.indexOf(':'));

        //Not start with fill-k k is number.
        if (!fillK.matches("fill-" + numExpression())) {
            return false;
        }
        //The continue of the string.
        String continueStr = str.substring(str.indexOf(':'), str.length());
        //Colors string.
        String [] colors = {"black", "blue", "cyan", "gray", "lightGray", "green", "orange", "pink", "red", "white"
                , "yellow" };
        //Fill color by name.
        //Check if If it one of the colors.
        for (int i = 0; i < colors.length; i++) {
            if (continueStr.equals(":color(" + colors[i] + ")")) {
                return true;
            }
        }
        return false;

    }
    /**.
     * fillImage.
     *
     * @param str the input string.
     * @return true if the string in the form fill:image(image_file).
     */
    public static boolean fillImage(String str) {
        //Hasn't '('
        if (str.indexOf('(') < 0) {
            return false;
        }
        //The section of fill:image(
        String start = str.substring(0, str.indexOf('(') + 1);
        //Not start with fill-k k is number.
        if (!start.equals("fill:image(")) {
            return false;
        }
        //Hasn't ')'
        if (str.indexOf('(') < 0) {
            return false;
        }
        String continueStr = str.substring(str.indexOf(')'), str.length());

        return continueStr.length() == 1;
    }

    /**.
     * fillKImage.
     *
     * @param str the input string.
     * @return true if the string in the form fill-k:image(image_file), k is a number.
     */
    public static boolean fillKImage(String str) {
        //Hasn't '('
        if (str.indexOf('(') < 0) {
            return false;
        }
        //The section of fill-k:image(
        String start = str.substring(0, str.indexOf('('));

        //Not start with fill-k k is number.
        if (!start.matches("fill-" + numExpression() + ":image")) {
            return false;
        }

        //Hasn't ')'
        if (str.indexOf('(') < 0) {
            return false;
        }
        String continueStr = str.substring(str.indexOf(')'), str.length());

        return continueStr.length() == 1;
    }
    //We did it because regular expression has problems with breakets.
    /**.
     * fillByRGB.
     * The string should be in the form fill:color(RGB(x,y,z)) where x,y,z are numbers.
     * We did it because regular expression has problems with breakets.
     *
     * @param str the input string.
     * @return true if the string in the form it should be, false otherwise.
     */
    public static boolean fillByRGB(String str) {
        //Check the first section.
        int lengthFillRGB = "fill:color(RGB(".length();
        //Isn't in the right size.
        if (str.length() <= lengthFillRGB) {
            return false;
        }
        boolean fillRGB = str.substring(0, lengthFillRGB).equals("fill:color(RGB(");
        //The numbers section.
        //Indexes problems
        if (str.indexOf(')', lengthFillRGB) < 0 || str.indexOf(')', lengthFillRGB) > str.length()) {
            return false;
        }
        if (str.indexOf(')', lengthFillRGB) - 1 - lengthFillRGB <= 0) {
            return  false;
        }
        String numbers = str.substring(lengthFillRGB , str.indexOf(')', lengthFillRGB) - 1);
        fillRGB &= numbers.matches(numExpression() + "," + numExpression() + "," + numExpression());
        //The breakets.
        String breakets = str.substring(str.indexOf(')', lengthFillRGB), str.length());
        fillRGB &= breakets.equals("))");
        return fillRGB;
    }

    /**.
     * fillKByRGB.
     * The string should be in the form fill-k:color(RGB(x,y,z)) where k,x,y,z are numbers.
     * We did it because regular expression has problems with breakets.
     *
     * @param str the input string.
     * @return true if the string in the form it should be, false otherwise.
     */
    public static boolean fillKByRGB(String str) {
        //Hasn't ':'.
        if (str.indexOf(':') < 0) {
            return false;
        }
        //The fill-k part.
        String fillK = str.substring(0, str.indexOf(':'));
        //Not start with fill-k k is number.
        if (!fillK.matches("fill-" + numExpression())) {
            return false;
        }
        //Check the first section.
        int lengthFillRGB = ":color(RGB(".length();
        String colorSection = str.substring(fillK.length(), lengthFillRGB + fillK.length());
        boolean fillRGB = colorSection.equals(":color(RGB(");
        //The numbers section.
        String numbers = str.substring(colorSection.length() + fillK.length() , str.indexOf(')', lengthFillRGB) - 1);
        fillRGB &= numbers.matches(numExpression() + "," + numExpression() + "," + numExpression());
        //The breakets.
        String breakets = str.substring(str.indexOf(')', lengthFillRGB), str.length());
        fillRGB &= breakets.equals("))");
        return fillRGB;
    }
    /**.
     * keyValue.
     * Gets a string that should be on form key:value.
     * We will check if the string is ok by the instructions (for example height:x x is number).
     *
     * @param str the input string.
     * @return true if the string is valid by the instructions, false otherwise.
     */
    public static boolean keyValue(String str) {
        if (str.equals("sdef")) {
            return true;
        }
        //The end to check if not negative and not 0.
        //height:x
        boolean height = str.matches("height:" + numExpression())
                && !str.matches("height:-" + numExpression()) && !str.matches("height:0");
        //width:x
        boolean width = str.matches("width:" + numExpression())
                && !str.matches("width:-" + numExpression()) && !str.matches("width:0");
        //hit_points:x.
        boolean hitPoints = str.matches("hit_points:" + numExpression())
                && !str.matches("hit_points:-" + numExpression()) && !str.matches("hit_points:0");

        // symbol:char
        boolean symbol = str.matches("symbol:" + charExpression());

        //Checks if one of the options.
        boolean ret =  height || width || symbol || hitPoints || fillStrokeName("fill", str)
                || fillByRGB(str) || fillKName(str) || fillKByRGB(str)
                || fillStrokeName("stroke", str) || fillImage(str) || fillKImage(str);

        return ret;
    }
}
