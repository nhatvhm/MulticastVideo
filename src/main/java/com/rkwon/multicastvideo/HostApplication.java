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

		String[] fakeArgs = {media};

		String publicIP = Constants.Network.INET_ADDRESS;
		short publicPort = 5555;

		String options = Utils.formatRtpStream(publicIP, publicPort);

		System.out.println("Streaming '" + media + "' to '" + options + "'");

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(fakeArgs);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

		mediaPlayer.playMedia(media, options, ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep");

		try {
			Thread.currentThread().join(); // Don't exit.
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Host host = new Host("MulticastTest");
		//host.run();
	}

	
 }