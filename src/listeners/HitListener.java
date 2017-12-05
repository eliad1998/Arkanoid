package listeners;

import game.Ball;
import game.Block;

/**
 * HitListener.
 * Objects that want to be notified of hit events should implement the HitListener interface.
 * and register themselves with a HitNotifier object using its addHitListener method.
 */
public interface HitListener {
    /**
     * hitEvent.
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block hitted.
     * @param hitter the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
