import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import java.io.*;


// A class that logs important relevant information for our analyses.

public class NetworkLog {

	// Data points about date/time of this session.
	public LocalDateTime = new LocalDateTime.now();

	public int identifier;
	public HashMap<Integer, ArrayList<NetworkDatum>> data;

	public NetworkLog() {
		data = new HashMap<Integer, ArrayList<NetworkDatum>>();
		
	}


	// A wrapper class around a single point of interesting/relevant network data.
	class NetworkDatum {

		public NetworkDatum() {

		}

	}
}