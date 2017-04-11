import java.io.*;
import java.net.*;
import java.util.*;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ClientSync implements Runnable {

	public String hostName;
	public int hostPort;

	public boolean running = true;
	public boolean receivedBufferLength = false;

	public EmbeddedMediaPlayer player;

	public ClientSync(EmbeddedMediaPlayer mediaPlayer) {
		player = mediaPlayer;
	}

	public void sendLogMessage() {
		try {
			Socket socket = new Socket(hostName, hostPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			float pos = mediaPlayer.getPosition();

			out.println(pos);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while( running ) {

		}
	}
}