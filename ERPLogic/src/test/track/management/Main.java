package test.track.management;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Main class to execute track management system
 *
 */
public class Main {

	public static void main(String[] args) {
		Scheduler scheduler = null;
		DataParser fp = null;
		HashMap<Integer, Event> techTalks = null;

		// for test input1.txt
		System.out.println("\n**** Testcase 1 (input1.txt)*****\n");
		scheduler = new Scheduler();
		fp = new DataParser();
		techTalks = fp.readInputFile(new File("input1.txt"));
		try {
			List<Session> sessions = scheduler.scheduleEvents(techTalks);
			fp.displayData(sessions, scheduler.getTrackCount());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// for test input2.txt
		System.out.println("\n**** Testcase 2 (input2.txt)*****\n");
		scheduler = new Scheduler();
		fp = new DataParser();
		techTalks = fp.readInputFile(new File("input2.txt"));
		try {
			List<Session> sessions = scheduler.scheduleEvents(techTalks);
			fp.displayData(sessions, scheduler.getTrackCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
