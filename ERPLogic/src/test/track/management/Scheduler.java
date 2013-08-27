package test.track.management;

import java.util.*;

/**
 * Scheduler to perform bin packing best fit(descending order of event duration)
 * algorithm. Based on the constraints defined, morning sessions are scheduled
 * as completely packed events, in contrst with evening events which are
 * flexible to end between 4:00PM to 5:00 PM. Also calculates the required
 * number of tracks
 * 
 * 
 */
public class Scheduler {
	private int totalNumberOfTracks;
	int evenSessionStartIndex = -1;
	List<Event> talks = new ArrayList<Event>();
	boolean morningSessionFlag = false;

	/**
	 * Schedules events.
	 * @param techTalks
	 * @return
	 */
	public List<Session> scheduleEvents(HashMap<Integer, Event> techTalks){
		totalNumberOfTracks = (getTotalTechTalkTime(techTalks) / ConferenceConstraints.maxTalkDuration);
		parseTechnicalTalks(techTalks); // create talks ArrayList
		morningSessionFlag = true;
		Collections.sort(talks);
		List<List<Event>> morningSessions = scheduleTalks(morningSessionFlag);
		morningSessionFlag = false;
		removeTechEvents(morningSessions);
		List<List<Event>> afterNoonSessions = scheduleTalks(morningSessionFlag);
		removeTechEvents(afterNoonSessions);
		List<Event> scheduledList = new ArrayList<Event>();
		if (talks.size() > 0) {
			for (List<Event> tempASession : afterNoonSessions) {
				int totalTime = getTotalTechTalkTime(tempASession);
				for (Event e : talks) {
					int dur = e.getDuration();
					if (totalTime + dur < (ConferenceConstraints.EveningSessionDur + ConferenceConstraints.DeltaEveningSession)) {
						e.setScheduled(true);
						tempASession.add(e);
						scheduledList.add(e);
					}
				}
				talks.removeAll(scheduledList);
				if (talks.isEmpty())
					break;
			}
		}

		return (createSessions(morningSessions, afterNoonSessions));

	}

	private void removeTechEvents(List<List<Event>> sessions) {
		for (List<Event> e : sessions) {
			talks.removeAll(e);
		}
	}

	private List<List<Event>> scheduleTalks(boolean mS) {
		List<List<Event>> allCombinations = new ArrayList<List<Event>>();
		int combCount = 0;
		int minSessionTime = ConferenceConstraints.MorningSessionDur, maxSessionTime = ConferenceConstraints.MorningSessionDur
				+ ConferenceConstraints.DeltaEveningSession;
		if (mS)
			maxSessionTime = minSessionTime;
		for (int i = 0; i < talks.size(); i++) {
			List<Event> combination = new ArrayList<Event>();
			int startPoint = i;
			int totalTime = 0;
			while (startPoint != talks.size()) {
				Event tempJob = talks.get(startPoint);
				startPoint++;
				if (tempJob.isScheduled())
					continue;
				if (tempJob.getDuration() > maxSessionTime
						|| tempJob.getDuration() + totalTime > maxSessionTime)
					continue;
				combination.add(tempJob);
				totalTime += tempJob.getDuration();

				if (mS) {
					if (totalTime == maxSessionTime)
						break;
					else if (totalTime >= minSessionTime)
						break;
				}
			}
			boolean validSession = false;
			if (mS)
				validSession = (totalTime == maxSessionTime);
			else {
				validSession = (totalTime > minSessionTime && totalTime <= maxSessionTime);
				if (!validSession && combCount >= 1) {
					if (totalTime < minSessionTime)
						validSession = true;
				}
			}
			if (validSession) {
				for (Event temp : combination) {
					temp.setScheduled(true);
				}
				allCombinations.add(combination);
				combCount++;
				if (combCount == totalNumberOfTracks)
					break;
			}

		}
		return allCombinations;
	}

	private List<Session> createSessions(List<List<Event>> morningSessions,
			List<List<Event>> afterNoonSessions) {
		List<Session> listOfSessions = new ArrayList<Session>();
		Session session = null;
		int index = 0;
		for (List<Event> e : morningSessions) {
			session = new Session();
			session.setTrackID(++index);
			session.setType(SessionType.Morning);
			Event lunch = new Event("Lunch", 60, EventType.Lunch);
			lunch.setScheduled(true);
			e.add(lunch);
			session.setEvents(e);
			listOfSessions.add(session);
		}
		index = 0;
		for (List<Event> e : afterNoonSessions) {
			session = new Session();
			session.setTrackID(++index);
			session.setType(SessionType.Afternoon);
			Event networking = new Event("Networking", 60, EventType.Networking);
			networking.setScheduled(true);
			e.add(networking);
			session.setEvents(e);
			listOfSessions.add(session);
		}
		return listOfSessions;
	}

	private void parseTechnicalTalks(HashMap<Integer, Event> techTalks) {
		Iterator<Integer> iter = techTalks.keySet().iterator();
		while (iter.hasNext()) {
			talks.add(techTalks.get(iter.next()));
		}

	}

	private int getTotalTechTalkTime(HashMap<Integer, Event> techTalks) {
		Iterator<Integer> iter = techTalks.keySet().iterator();
		int totalDuration = 0;
		while (iter.hasNext()) {
			Event temp = techTalks.get(iter.next());
			totalDuration += temp.getDuration();
		}
		return totalDuration;
	}

	private int getTotalTechTalkTime(List<Event> tempASession) {
		int totalDuration = 0;
		for (Event e : tempASession) {
			totalDuration += e.getDuration();
		}
		return totalDuration;
	}

	public int getTrackCount() {
		return totalNumberOfTracks;
	}
}
