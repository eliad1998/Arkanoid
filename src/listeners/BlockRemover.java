package listeners;

import game.Ball;
import game.Block;
import game.GameLevel;

/**.
 * BlockRemover.
 *
 * A BlockRemover is in charge of removing blocks from the gameLevel.
 * As well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;
    /**.
     * Creates new instance of BlockRemover.
     *
     * The constructor of our class.
     * @param gameLevel the gameLevel where the blocks are.
     * @param removedBlocks counter of the number of blocks that remain.
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        //The blocks we want to remove.
        this.remainingBlocks = removedBlocks;

    }
    /**.
     * hitEvent
     *  Blocks that are hit and reach 0 hit-points should be removed from the gameLevel.
     *  Remember to remove this listener from the block that is being removed from the gameLevel.
     *
     * @param beingHit the block we hitted.
     * @param hitter the ball hitted the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //We have one hit points until remove.
        if (beingHit.getHitPoints() == 1) {
            //Remove the block from the gameLevel.
            beingHit.removeFromGame(this.gameLevel);
            //Remove this listener from the block that is being removed from the gameLevel.
            beingHit.removeHitListener(this);
            //Updating the remaining blocks.
            this.remainingBlocks.decrease(1);
        }


    }
}
