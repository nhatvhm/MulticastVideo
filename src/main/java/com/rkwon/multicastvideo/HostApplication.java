/*
 * In charge of running the program.
 */

// Shamelessly ripped from here at the beginning:
// http://stackoverflow.com/questions/33535607/vlcj-rtp-streaming-client-side

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
 
 public class HostApplication {
	public static void main(String[] args) {

		NativeDiscovery nativeDiscovery = new NativeDiscovery();
		nativeDiscovery.discover();

		String media = "src/main/resources/FFbyMitski.mp4";

		String publicIP = "230.0.0.1";
		short publicPort = 5555;

		String options = formatRtpStream(publicIP, publicPort);

		System.out.println("Streaming '" + media + "' to '" + options + "'");

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

		//mediaPlayer.playMedia(media, options, ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep");

		try {
			Thread.currentThread().join(); // Don't exit.
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Host host = new Host("MulticastTest");
		//host.run();
	}

	private static String formatRtpStream(String serverAddress, short serverPort) {
		StringBuilder sb = new StringBuilder(200);

		sb.append("::sout=#transcode{vcodec=mp4v, vb=3000, fps=30, scale=1, acodec=mp4a, ab=128, channels=2, samplerate=48000, width=800, height=600}:rtp{dst=");

		sb.append(serverAddress);
		sb.append(",port=");
		sb.append(serverPort);
		sb.append(",mux=ts");

		return sb.toString();
	}
 }