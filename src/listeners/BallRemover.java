package listeners;

import game.Ball;
import game.Block;
import game.GameLevel;

/**
 * BallRemover
 *
 * A BallRemover is in charge of removing balls from the gameLevel.
 * As well as keeping count of the number of balls that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;
    /**.
     * Creates new instance of BallRemover.
     *
     * The constructor of our class.
     * @param gameLevel the gameLevel where the blocks are.
     * @param removedBalls counter of the number of balls that remain.
     */
    public BallRemover(GameLevel gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        //The blocks we want to remove.
        this.remainingBalls = removedBalls;
    }
    /**
     * hitEvent.
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block hitted.
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
    }
}
