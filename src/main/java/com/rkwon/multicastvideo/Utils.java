/*
 * A utility class containing useful methods overall.
 */

public class Utils {


	public static String formatRtpStream(String serverAddress, short serverPort) {
		StringBuilder sb = new StringBuilder(200);

		sb.append("::sout=#transcode{vcodec=mp4v, vb=3000, fps=30, scale=1, acodec=mp4a, ab=128, channels=2, samplerate=48000, width=800, height=600}:rtp{dst=");

		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts");

		return sb.toString();
	}
}