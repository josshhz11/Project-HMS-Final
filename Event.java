import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents an event with a start time, optional end time, and a description.
 * The `Event` class is serializable to allow for persistent storage and retrieval.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;

    /**
     * Constructs an `Event` with a start time and a description.
     * The end time is left null for events without a specified duration.
     *
     * @param startTime   The start time of the event.
     * @param description A brief description of the event.
     */
    public Event(LocalDateTime startTime, String description) {
        this.startTime = startTime;
        this.endTime = null;
        this.description = description;
    }

    /**
     * Constructs an `Event` with a start time, an end time, and a description.
     *
     * @param startTime   The start time of the event.
     * @param endTime     The end time of the event.
     * @param description A brief description of the event.
     */
    public Event(LocalDateTime startTime, LocalDateTime endTime, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    /**
     * @return The start time of the event.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @return The end time of the event, or `null` if the event has no specified end time.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @return A brief description of the event.
     */
    public String getDescription() {
        return description;
    }
}
