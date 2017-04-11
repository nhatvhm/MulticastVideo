/*
 * A utility class containing useful methods overall.
 */

public class Utils {

	// :sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=rtp{dst=230.0.0.1,port=5555,mux=ts,sap,name=MyStream},dst=display} :sout-keep
	public static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(400);

	    sb.append(":sout=#transcode{vcodec=h264,acodec=mpga,ab=128,channels=2,samplerate=44100}:rtp{dst=");

	    sb.append(serverAddress);
	    sb.append(",port=");
	    sb.append(serverPort);
	    sb.append(",mux=ts,sap}");
	    return sb.toString();
    }
}