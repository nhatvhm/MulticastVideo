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

    // Taken from:
    // http://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java/29132118#29132118
    public static byte[] longToBytes(long l) {
    	byte[] result = new byte[8];
	    for (int i = 7; i >= 0; i--) {
	        result[i] = (byte)(l & 0xFF);
	        l >>= 8;
	    }
	    return result;
    }

    public static long bytesToLong(byte[] b) {
	    long result = 0;
	    for (int i = 0; i < 8; i++) {
	        result <<= 8;
	        result |= (b[i] & 0xFF);
	    }
	    return result;
	}
}