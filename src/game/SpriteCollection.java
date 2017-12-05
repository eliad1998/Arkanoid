package game;
import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;
/**.
 * SpriteCollection
 * Currently, the GameEnvironment holds a list of Collidable objects.
 * Similarly, a SpriteCollection will hold a collection of sprites.
 */
public class SpriteCollection {
    //The list of sprites.
    private List<Sprite> sprites;
    /**.
     * Creates new instance of SpriteCollection.
     * The constructor of our class SpriteCollection.
     * Instalize the list of Sprite objects.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**.
     * addSprite
     * Add the given Sprite to the environment.
     * @param  s the Sprite we wnat to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**.
     * removeSprite.
     * Remove the given Sprite from.
     * @param  s the Sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**.
     * notifyAllTimePassed.
     * call timePassed() on all sprites.
     *
     * @param dt  It specifies the amount of seconds passed since the last call.
     * As we will be dealing with speeds that show many frames per second.
     * Each invocation will result in a small value for dt.
     * For example, in case we set 60 frames per second the dt value will be 1/60
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).timePassed(dt);
        }
    }
    /**.
     * notifyAllTimePassed
     * call call drawOn(d) on all sprites.
     * @param d a drawsurface.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).drawOn(d);
        }
    }
}
