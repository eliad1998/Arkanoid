package menu;

import animation.Animation;

/**.
 * Menu
 * Our Menu will need to be displayed on screen, so it will be an Animation.
 * Unlike the other animation loops we had, this one will need to return a value when it is done.
 * We may want to add a nice background to our menu.
 * For this, we will provide it with a method that will accept a background sprite and display it.
 *
 * The generics used to specify the return type expected from the menu.
 * By using generics, we allow the menu to be used with different return types.
 * For example, if we want the selection to result in a java.awt.Color object instead of a String.
 *
 * @param <T> type of the return value of the tasks in the menu.
 */
public interface Menu<T> extends Animation {
    /**.
     * addSelection.
     * Adding selection to the menu.
     *
     * @param key the key to press if we want to chose this selection.
     * @param message the message of this selection.
     * @param returnVal which value this selection will return.
     */
    void addSelection(String key, String message, T returnVal);
    /**.
     * getStatus.
     * @return the status of this menu.
     */
    T getStatus();
    /**.
     * addSubMenu.
     * Adding submenu to the menu.
     *
     * @param key a key to move to the submenu.
     * @param message  message to move to the submenu.
     * @param subMenu the submenu itself.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}
