package listeners;

/**
 * HitNotifier
 *
 * Indicate that objects that implement it send notifications when they are being hit.
 */
public interface HitNotifier {
    /**
     * addHitListener
     * Add hl as a listener to hit events.
     *
     * @param hl an HitListener.
     */
    void addHitListener(HitListener hl);
    /**
     * addHitListener
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl an HitListener.
     */
    void removeHitListener(HitListener hl);
}