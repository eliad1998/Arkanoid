package menu;

/**.
 * Task.
 * Instead, we prefer to tell the menu in advances what we would like to happen when the selection is made,
 * And then, when a user makes a selection, the menu will return to us the action we wanted to happen.
 * To do this, we will define a Task interface.
 * A task is something that needs to happen, or something that we can run() and return a value.
 *
 * @param <T> the type of the value that the task return.
 */
public interface Task<T> {
    /**.
     * run.
     * Running the task.
     * @return a value.
     */
    T run();
}
