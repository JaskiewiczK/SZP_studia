package szp.model;

/**
 * This is the State enumeration that represents the state of an assignment in the system.
 * It can be one of the following: IN_PROGRESS, DONE, WAITING, CANCELLED.
 */
public enum State {
    /**
     * Represents the IN_PROGRESS state.
     * This is when an assignment is currently being worked on.
     */
    IN_PROGRESS,

    /**
     * Represents the DONE state.
     * This is when an assignment has been completed.
     */
    DONE,

    /**
     * Represents the WAITING state.
     * This is when an assignment is waiting to be started or resumed.
     */
    WAITING,

    /**
     * Represents the CANCELLED state.
     * This is when an assignment has been cancelled and will not be completed.
     */
    CANCELLED
}