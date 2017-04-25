import java.util.Date;
import java.util.Calendar;
import java.io.Serializable;
import java.awt.Dimension;

// A wrapper class around a single point of interesting/relevant network data.
// Intended to be exchanged between clients and the host for data aggregation.
public class NetworkDatum implements Serializable {
  
  public String username; // username of the sender

	public Date creationDate;
  
  // Data components.
  public String aspectRatio;
  public int audioChannel;
  public long audioDelay;
  
  public float fps;
  public float mediaLength;
  
  // Position: what percentage of the video you're through
  public float position;
  
  // Time: Current play-back time.
  public long time;
  
  // 1.0 is normal speed, .5 is half speed, 2.0 is double speed.
  public float videoPlayRate; 
  public float scale;
  
  Dimension videoDimension;

  public NetworkDatum(String username,

											String aspectRatio,
											int audioChannel,
											long audioDelay,

											float fps,
											float mediaLength,
		
											float position,
		
											long time,

											float videoPlayRate,
											float scale,
		
											Dimension videoDimension			
											) {

		creationDate = new Date();

		this.username = username;

		this.aspectRatio = aspectRatio;
		this.audioChannel = audioChannel;
		this.audioDelay = audioDelay;

		this.fps = fps;
		this.mediaLength = mediaLength;

		this.position = position;

		this.time = time;

		this.videoPlayRate = videoPlayRate;
		this.scale = scale;

		this.videoDimension = videoDimension;

  }

	// Convert data to how it should appear if the object were a line in a CSV file.
	public String toCSVLine() {
		StringBuilder sb = new StringBuilder();

		sb.append(username + ",");

		// Now we append the time and date
		// Man. The Java Calendar class is REALLY weird.
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(creationDate);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);

		sb.append(hours + ":" + minutes + ":" + seconds + ",");

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1; // Starts at 0
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		sb.append(month + ":" + day + ":" + year + ",");

		// Go back to appending data items.
		sb.append(aspectRatio + ",");
		sb.append(audioChannel + ",");
		sb.append(audioDelay + ",");

		sb.append(fps + ",");
		sb.append(mediaLength + ",");

		sb.append(position + ",");

		sb.append(time + ",");

		sb.append(videoPlayRate + ",");
		sb.append(scale + ",");

		sb.append(videoDimension.getHeight() + ",");
		sb.append(videoDimension.getWidth() + ",");

		return sb.toString();
	}

}
