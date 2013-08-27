package test.track.management;

import java.util.List;

/**
 * Representation class defining different sessions 
 * 
 */

public class Session implements Comparable<Session> {

	private SessionType type;
	private List<Event> events;
	private int trackID;

	public SessionType getType() {
		return type;
	}

	public void setType(SessionType type) {
		this.type = type;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public int getTrackID() {
		return trackID;
	}

	public void setTrackID(int trackID) {
		this.trackID = trackID;
	}

	@Override
	public String toString() {
		return "Session [type=" + type + ", events=" + events.toString()
				+ ", trackID=" + trackID + "]";
	}

	@Override
	public int compareTo(Session s) {
		if (this.getTrackID() < s.trackID)
			return -1;
		else if (this.getTrackID() > s.trackID)
			return 1;
		else
			return 0;
	}

}
