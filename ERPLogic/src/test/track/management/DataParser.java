package test.track.management;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Parser to perform input and output operations with the data.
 */
public class DataParser {

	/**
	 * Reads data from the input file.
	 * @param inputFile
	 * @return
	 */
	public HashMap<Integer, Event> readInputFile(File inputFile) {
		String inputLine;
		HashMap<Integer, Event> techTalkMap = new HashMap<Integer, Event>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFile));
			int iter = 0;
			while ((inputLine = br.readLine()) != null) {
				iter++;
				if (inputLine.toLowerCase().contains("lightning")) {
					techTalkMap.put(iter, new Event(inputLine.toLowerCase(),
							ConferenceConstraints.LightningTalkDuration,
							EventType.LightningTalk));
				} else {
					String desc = inputLine
							.substring(0, inputLine.length() - 5);
					Integer dur = Integer.parseInt(inputLine.substring(
							inputLine.length() - 5, inputLine.length() - 3));
					techTalkMap.put(iter, new Event(desc, dur,
							EventType.TechTalk));
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return techTalkMap;

	}

	/**
	 * Outputs the scheduled track information.
	 * @param sessions
	 * @param trackCount
	 */
	public void displayData(List<Session> sessions, int trackCount) {
		List<String> outputText = new ArrayList<String>();
		Collections.sort(sessions);
		String maxNetworkingTime = null;
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma");
		Date d;
		try {
			d = dateFormat.parse(ConferenceConstraints.MorningStartTime);
			cal.setTime(d);
			dateFormat.setCalendar(cal);
			String startTime;
			int trackId = 1;
			outputText.add("Track " + trackId);
			for (Session s : sessions) {
				for (Event e : s.getEvents()) {
					startTime = dateFormat.format(cal.getTime());
					if (e.getDescription().toLowerCase().contains("networking")
							&& trackId > 1) {
						outputText.add(maxNetworkingTime + " "
								+ e.getDescription() + " " + e.getDuration()
								+ "min");
					} else {
						outputText.add("" + dateFormat.format(cal.getTime())
								+ " " + e.getDescription() + " "
								+ e.getDuration() + "min");
					}

					cal.add(Calendar.MINUTE, e.getDuration());
					if (e.getDescription().toLowerCase().contains("networking")
							&& trackId < trackCount) {
						if (trackId == 1) {
							maxNetworkingTime = startTime;
						}
						trackId++;
						outputText.add("Track " + trackId);
						d = dateFormat
								.parse(ConferenceConstraints.MorningStartTime);
						cal.setTime(d);
					}
				}
			}
			for (String s : outputText) {
				System.out.println("  " + s);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
