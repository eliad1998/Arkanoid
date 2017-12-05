package creators;

import game.Block;

/**.
 * BlockCreator is an interface of a factory-object that is used for creating blocks.
 */
public interface BlockCreator {
    /**.
     * create.
     * Create a block at the specified location.
     *
     * @param xpos the x position of the top left point.
     * @param ypos the y position of the top left point.
     * @return the block we just created.
     */
    Block create(int xpos, int ypos);
}