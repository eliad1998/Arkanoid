package levels;

import game.Block;
import game.Sprite;
import game.Velocity;

import java.util.List;
/**.
 * LevelInformation.
 *
 * The LevelInformation interface specifies the information required to fully describe a level.
 */
public interface LevelInformation {
    /**.
     * numberOfBalls.
     *
     * @return the number of balls in this level.
     */
    int numberOfBalls();
    /**.
     * initialBallVelocities.
     * Note that initialBallVelocities().size() == numberOfBalls()
     * @return list with the initial velocity of each ball.
     */
    List<Velocity> initialBallVelocities();
    /**.
     * paddleSpeed.
     * @return the speed of the paddle.
     */
    int paddleSpeed();
    /**.
     * paddleWidth.
     * @return the width of the paddle.
     */
    int paddleWidth();
    /**.
     * levelName.
     * The level name will be displayed at the top of the screen.
     * @return level name.
     */
    String levelName();
    /**.
     * getBackground.
     * @return a sprite with the background of the level.
     */
    Sprite getBackground();
    /**.
     * blocks.
     * The Blocks that make up this level, each block contains its size, color and location.
     * @return a list of blocks of this level.
     */
    List<Block> blocks();
    /**.
     * numberOfBlocksToRemove.
     * This number should be <= blocks.size();
     * @return the number of blocks that should be removed
     */
    int numberOfBlocksToRemove();
}
