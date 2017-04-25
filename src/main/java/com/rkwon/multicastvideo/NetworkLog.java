import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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
	public void add(NetworkDatum dataPoint) {
		data.add(dataPoint);
	}

	// Generates a file name
	//
	// @param fileFormat Currently we only accept "csv"
	public String generateFileName(String fileFormat) {
		StringBuilder sb = new StringBuilder();

		sb.append("VideoSessionData ");
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		sb.append(dateFormat.format(sessionStart));

		sb.append('.');
		sb.append(fileFormat);

		return sb.toString();
	}

	// Records data into the target file.
	public void toCSV(String file) {

		try {
			//File outFile = new File(file);
			StringBuilder sb = new StringBuilder();
	
			// We append the columns
			sb.append("USERNAME,TIME,DATE,ASPECT_RATIO,AUDIO_CHANNEL,AUDIO_DELAY,FPS,MEDIA_LENGTH,POSITION,TIME,VIDEO_PLAY_RATE,SCALE,WINDOW_HEIGHT,WINDOW_WIDTH");
			sb.append("\n");

			// Now we append the actual data points.
			for(NetworkDatum point : data) {
				sb.append(point.toCSVLine());
			}

			PrintWriter writer = new PrintWriter(file);
			writer.print(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
