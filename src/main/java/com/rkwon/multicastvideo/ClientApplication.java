/*
 * In charge of running the program.
 */

// Initial draft taken from:
// http://stackoverflow.com/questions/33535607/vlcj-rtp-streaming-client-side

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
 
 public class ClientApplication {
	public static void main(String[] args) {

		NativeDiscovery nativeDiscovery = new NativeDiscovery();
		nativeDiscovery.discover();

		String mediatorIP = Constants.Network.INET_ADDRESS;
		short mediatorPort = 5554;

		String publicIP, publicServer, localIP, localServer, clientIP;
		short publicPort, localPort;

		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
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
		frame.setTitle("Capturing from Public Server 'rtp://" + publicIP + ":" + publicPort + "'");
		mediaPlayer.playMedia(publicServer);

		/*
		Client client = new Client();
		client.connect();
		*/
	}
 }