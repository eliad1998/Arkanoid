package menu;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import geometry.Point;
import geometry.Rectangle;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Block;
import game.Sprite;
import levels.Background;
import levels.Level;

/**
 * MenuAnimation.
 * A class implementing the menu interface.
 *
 * @param <T> the type that the tasks return.
 */
public class MenuAnimation<T> implements Menu<T> {
    private String title;
    //List of menu selections.
    private List<MenuSelection<T>> selections;
    private List<MenuSelection<Menu<T>>> subMenus;
    private T status;
    private KeyboardSensor ks;
    //Determines if should stop from the menu.
    private boolean isAlreadyPressed;
    /**
     * .
     * Creates new instance of MenuAnimation.
     * The constructor of our class.
     *
     * @param title the title of the menu.
     * @param ks a keyboard sensor.
     */
    public MenuAnimation(String title, KeyboardSensor ks) {
        this.title = title;
        this.selections = new ArrayList<MenuSelection<T>>();
        this.subMenus = new ArrayList<MenuSelection<Menu<T>>>();
        this.status = null;
        this.ks = ks;
        //Initializing stop as false.
        this.isAlreadyPressed = true;
    }

    /**.
     * addSelection.
     * Adding selection to the menu.
     *
     * @param key       the key to press if we want to chose this selection.
     * @param message   the message of this selection.
     * @param returnVal which value this selection will return.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new MenuSelection<T>(key, message, returnVal));
    }
    /**
     * .
     * getStatus
     *
     * @return the status of this menu.
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * doOneFrame.
     * In charge of the logic.
     * For example, drawing and removing blocks and balls.
     *
     * @param d  a drawsurface.
     * @param dt It specifies the amount of seconds passed since the last call.
     *           As we will be dealing with speeds that show many frames per second.
     *           Each invocation will result in a small value for dt.
     *           For example, in case we set 60 frames per second the dt value will be 1/60
     */
    public void doOneFrame(DrawSurface d, double dt) {
        //Drawing the menu
        //Drawing the background.
        getBackground().drawOn(d);
        //The
        //Drawing the text.
        //Headline.
        d.setColor(Color.lightGray);
        d.drawText(d.getWidth() / 7, d.getHeight() / 7, this.title, 32);
        //Menu options.
        d.setColor(Color.GRAY);
        //Drawing each option (key)option.
        for (int i = 1; i <= this.selections.size(); i++) {
            d.drawText(d.getWidth() / 7 + 60, d.getHeight() / 7 + 50 + i * 60
                    , "(" + this.selections.get(i - 1).getKey() + ")"
                            + this.selections.get(i - 1).getMessage(), 32);
        }
        //List of keys.
        List<String> keys = new ArrayList<String>();
        //Initializing the key pressed animatinos.
        for (int i = 0; i < selections.size(); i++) {
            keys.add(selections.get(i).getKey());
        }

        for (int i = 0; i < subMenus.size(); i++) {
            keys.add(subMenus.get(i).getKey());
        }
        //doOneFrame of each object in the list.
        for (int i = 0; i < keys.size(); i++) {
            if (ks.isPressed(keys.get(i))) {
                //If the space isn't pressed from the previous animation.
                if (!this.isAlreadyPressed) {
                    //The lists are synchronized so we get the status from the parallel list.
                    //The selection list is first.
                    if (i < this.selections.size()) {
                        this.status = selections.get(i).getReturnVal();
                    }
                    //Else it is the submenu list but we will add a selection that move to submenu.
                    //Break because the program stopped.
                    break;
                }
            } else { //Updating is already pressed.
                this.isAlreadyPressed = false;
            }
        }
        }
    /**
     * shouldStop.
     * The stopping conditions are for example no more balls or blocks.
     *
     * @return true if the animation should stop, false otherwise.
     */
    public boolean shouldStop() {
        //Status is initialized as null and when a key pressed isn't null.
        return this.status != null;
    }
    /**.
     * getScreenRectangle.
     * @return rectangle with the margins of the screen.
     */
    private Rectangle getScreenRectangle() {
        return new Rectangle(new Point(0, 0), Level.GUIWIDTH, Level.GUIWIDTH);
    }
    /**.
     * getBackground.
     * @return a sprite with the background of the level.
     */
    private Sprite getBackground() {
        Background background = new Background();
        Block innerScreen = new Block(getScreenRectangle(), Color.BLUE);
        background.addSprite(innerScreen);

        return background;
    }
    /**.
     * addSubMenu.
     * Adding submenu to the menu.
     *
     * @param key a key to move to the submenu.
     * @param message  message to move to the submenu.
     * @param subMenu the submenu itself.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new MenuSelection<Menu<T>>(key, message, subMenu));
    }
}

