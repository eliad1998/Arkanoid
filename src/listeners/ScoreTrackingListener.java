package listeners;
import game.Ball;
import game.Block;
/**.
 * ScoreTrackingListener.
 *
 * Update this counter when blocks are being hit and removed.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    /**.
     * Creates new instance of ScoreTrackingListener.
     * The constructor of our class.
     *
     * @param scoreCounter a reference to the counter to the scores in the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * hitEvent.
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block hitted.
     * @param hitter the Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //We have one hit points until remove.
        if (beingHit.getHitPoints() == 1) {
            //Destroying a block is worth and additional 10 points
            this.currentScore.increase(10);
            //Removing the score hit listener from that block that removed.
            beingHit.removeHitListener(this);
        }
        //Hitting a block is worth 5 points
        this.currentScore.increase(5);
    }
}
