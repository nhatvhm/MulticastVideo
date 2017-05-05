import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_stats_t;

public class ClientSyncTest {
	public static void main(String [] args) throws Exception {
		System.out.println("Starting Client Sync Test");
		System.out.println("Username is: " + System.getProperty("user.name"));

		NativeDiscovery nativeDiscovery = new NativeDiscovery();
		nativeDiscovery.discover();

		String mediatorIP = "192.168.1.104";
		short mediatorPort = 5554;

		String publicIP, publicServer, localIP, localServer, clientIP;
		short publicPort, localPort;

		String[] myArgs = {"--stats"};

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(myArgs);
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.black);
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

		JFrame frame = new JFrame();

		frame.add(canvas);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		publicIP = Constants.Network.INET_ADDRESS;
		publicPort = 5555;
		publicServer = Utils.formatRtpStream(publicIP, publicPort);
		System.out.println("Capturing from '" + publicServer + "'");
		frame.setTitle(publicServer);//("Capturing from Public Server 'rtp://" + publicIP + ":" + publicPort + "'");
		mediaPlayer.playMedia("rtp://230.0.0.1:5555");

		System.out.println("________________________________\n\n\n\n\n");
		System.out.println("BEGINNING CLIENT SYNC...");

		libvlc_media_stats_t stats = mediaPlayer.getMediaStatistics();

		ClientSync client = new ClientSync(mediaPlayer, "137.165.75.113", Constants.Network.HOST_SYNC_PORT);
		Thread clientThread = new Thread(client);

		clientThread.start();

		while(true) {
			Thread.sleep(1000);
			System.out.println("___________________________");
			System.out.println(stats.i_lost_pictures);
			System.out.println("___________________________");
		}
	}
}
