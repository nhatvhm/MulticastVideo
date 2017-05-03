import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class HostSyncTest {

	public static void main(String[] args) throws Exception {

		NativeDiscovery nativeDiscovery = new NativeDiscovery();
		nativeDiscovery.discover();

		String media = "src/main/resources/FFbyMitski.mp4";
		//String media = "screen://";

		String[] fakeArgs = {media};

		String publicIP = Constants.Network.INET_ADDRESS;
		short publicPort = 5555;

		String options = Utils.formatRtpStream(publicIP, publicPort);

		System.out.println("Streaming '" + media + "' to '" + options + "'");

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(fakeArgs);
		HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();

		System.out.println("________________________________\n\n\n\n\n");
		System.out.println("BEGINNING HOST SYNC...");

		HostSync host = new HostSync(Constants.Network.HOST_SYNC_PORT, 3000);
		//Thread hostThread = new Thread(host);
		HostInterface hostInterface = new HostInterface(host);

		System.out.println("HOST IP ADDRESS: " + host.hostAddress);

		//hostThread.start();
		//Thread.sleep(5000);
		//System.out.println("Telling host to stop receiving clients.");
		//host.stopAcceptingClients();

		//host.ping(false);
		//host.ping(false);
		//host.ping(false);
		//host.ping(true);

		mediaPlayer.playMedia(media, options, ":no-sout-rtp-sap", ":no-sout-standard-sap", ":sout-all", ":sout-keep");

		//Thread.sleep(20000);

		//host.logToCSV("logs");

		/*
		try {
			Thread.currentThread().join(); // Don't exit.
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/


	}
}