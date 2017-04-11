import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import java.io.*;


// A class that logs important relevant information for our analyses.

public class NetworkLog {

	public static final int ERROR_ID = 500;

	// Data points about date/time of this session.
	public LocalDateTime sessionStart = LocalDateTime.now();
	public HashMap<Integer, ArrayList<NetworkDatum>> data;

	public NetworkLog() {
		data = new HashMap<Integer, ArrayList<NetworkDatum>>();
	}

	// Register an error.
	public void registerError() {
		addToLog(ERROR_ID, new NetworkDatum(false, ERROR_ID));
	}

	// A helper method to add a datapoint to our log.
	private void addToLog(int id, NetworkDatum dataPoint) {
		if(data.containsKey(id)) {
			data.get(id).add(dataPoint);
		} else {
			ArrayList<NetworkDatum> datum = new ArrayList<NetworkDatum>();
			datum.add(dataPoint);
			data.put(id, datum);
		}
	}


	// A wrapper class around a single point of interesting/relevant network data.
	class NetworkDatum {

		public boolean valid;
		public LocalDateTime messageReceived = LocalDateTime.now();
		public int identifier;

		public NetworkDatum(boolean isValid, int id) {
			valid = isValid;
			identifier = id;
		}

	}
}