import java.util.HashSet;
import java.util.Arrays;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class Test {

	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	private final JButton pauseButton;
	private final JButton rewindButton;
	private final JButton skipButton;

	public static void main(String[] args) {

		System.out.println("Test started!");
        
		NativeDiscovery nativeDiscovery = new NativeDiscovery();

		boolean found = false;

		found = nativeDiscovery.discover();
		
		System.out.println(found);
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Test();
			}
		});
	}

	public Test() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

		JPanel controlsPane = new JPanel();
		
		pauseButton = new JButton("Pause");
		rewindButton = new JButton("Rewind");
		skipButton = new JButton("Skip");

		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().pause();
			}
		});

		rewindButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(-10000);
			}
		});

		skipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mediaPlayerComponent.getMediaPlayer().skip(10000);
			}
		});

		controlsPane.add(pauseButton);
		controlsPane.add(rewindButton);
		controlsPane.add(skipButton);

		contentPane.add(controlsPane, BorderLayout.SOUTH);


		frame = new JFrame("My First Media Player");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);

		mediaPlayerComponent.getMediaPlayer().playMedia("src/main/resources/FFbyMitski.mp4");
	}
}