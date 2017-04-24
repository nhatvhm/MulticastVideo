import java.util.Date;

import java.util.ArrayList;
import java.util.*;
import java.io.*;


// A class that logs important relevant information for our analyses.

public class NetworkLog {

	// Types
	public static final String ERROR = "ERROR";
	public static final String LATENCY_MEASUREMENT = "LATENCY";
	public static final String VIDEO_POSITION = "VIDEO_POSITION";
	public static final String CLIENT_JOIN = "CLIENT_JOIN";

	// Data points about date/time of this session.
	//public LocalDateTime sessionStart = LocalDateTime.now();
	public Date sessionStart = new Date();
	public ArrayList<NetworkDatum> data;

	public NetworkLog() {
		data = new ArrayList<NetworkDatum>();
	}

	// Register an error.
	public void registerError() {
		//addToLog(new NetworkDatum(ERROR, ERROR));
	}

	// A helper method to add a datapoint to our log.
	private void addToLog(NetworkDatum dataPoint) {
		data.add(dataPoint);
	}
}