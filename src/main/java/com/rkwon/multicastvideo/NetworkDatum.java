import java.awt.Dimension;

// A wrapper class around a single point of interesting/relevant network data.
// Intended to be exchanged between clients and the host for data aggregation.
public class NetworkDatum {

	public String username; // username of the sender
	
	// Data components.
	public String aspectRation;
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


}