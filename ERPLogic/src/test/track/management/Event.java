package test.track.management;

/**
 * Representation class for the events
 */
public class Event implements Comparable<Event> {
	private int duration; // in terms of minutes
	private EventType event;
	private String description;
	private boolean isScheduled;

	public Event(String desc, int duration, EventType e) {
		this.setDescription(desc);
		this.duration = duration;
		this.event = e;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public EventType getEvent() {
		return event;
	}

	public void setEvent(EventType event) {
		this.event = event;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isScheduled() {
		return isScheduled;
	}

	public void setScheduled(boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	@Override
	public String toString() {
		return "TechnicalEvents [duration=" + duration + ", event=" + event
				+ ", description=" + description + ", isScheduled="
				+ isScheduled + "]";
	}

	@Override
	public int compareTo(Event e) {
		if (this.getDuration() > e.getDuration())
			return -1;
		else if (this.getDuration() < e.getDuration())
			return 1;
		else
			return 0;
	}

}
