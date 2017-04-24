import java.awt.Dimension;

// A wrapper class around a single point of interesting/relevant network data.
// Intended to be exchanged between clients and the host for data aggregation.
public class NetworkDatum {
    
    public String username; // username of the sender
    
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
    
    
}
