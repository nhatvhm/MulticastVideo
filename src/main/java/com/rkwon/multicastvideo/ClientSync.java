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

	public ClientSync(EmbeddedMediaPlayer mediaPlayer, String hostName, int hostPort) {
		player = mediaPlayer;
		this.hostName = hostName;
		this.hostPort = hostPort;
	}

	public void connectToHost() {
		try {
			Socket socket = new Socket(hostName, hostPort);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendLogMessage() {
		try {
			Socket socket = new Socket(hostName, hostPort);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// TODO: Send more useful information!
			float pos = player.getPosition();

			out.println(pos);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while( running ) {

		}
	}
}