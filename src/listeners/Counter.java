package listeners;

/**.
 * Counter.
 *
 * Counter is a simple class that is used for counting things.
 * Why to create such class? Becasuse we want to pass counter reference and we can't do it with regular int for
   example.
 */
public class Counter {
    private int count;
    /**.
     * Creates new instance of Counter.
     *
     * The constructor of our class.
     * @param startValue the start value of this counter.
     */
    public Counter(int startValue) {
        this.count = startValue;
    }
    /**.
     * increase.
     * Add number to current count.
     *
     * @param number the number we want to increase our count in.
     */
    public void increase(int number) {
        this.count += number;
    }
    /**.
     * decrease.
     * Subtract number from current count.
     *
     * @param number the number we want to decrease our count in.
     */
    public void decrease(int number) {
        this.count -= number;
    }
    /**.
     * getValue.
     * @return  current count.
     */
    public int getValue() {
        return this.count;
    }
}