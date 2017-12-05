package menu;

/**.
 * MenuSelection.
 * Represent a selection on the menu.
 *
 * @param <T> the type that the tasks return.
 */
public class MenuSelection<T> {
    private String key;
    private String message;
    private T returnVal;
    /**.
     * Creates new instance of MenuSelection.
     * The constructor of our class.
     *
     * @param key the key to press if we want to chose this selection.
     * @param message the message of this selection.
     * @param returnVal which value this selection will return.
     */
    public MenuSelection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }
    //Accessors.
    /**.
     * getKey.
     * @return the key to press if we want to chose this selection.
     */
    public String  getKey() {
        return this.key;
    }
    /**.
     * getMessage.
     * @return the message of this selection.
     */
    public String  getMessage() {
        return this.message;
    }
    /**.
     * getReturnVal.
     * @return which value this selection will return.
     */
    public T getReturnVal() {
        return this.returnVal;
    }
}
