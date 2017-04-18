import java.io.*;
import java.net.*;
import java.util.*;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ClientSync implements Runnable {

	public String hostName;
	public int hostPort;

	public DatagramSocket clientUDPSocket;
	public int clientUDPPort;

	public boolean running = true;
	public boolean receivedBufferLength = false;

	public EmbeddedMediaPlayer player;

	public ClientSync(EmbeddedMediaPlayer mediaPlayer, String hostName, int hostPort) {
		player = mediaPlayer;
		this.hostName = hostName;
		this.hostPort = hostPort;

		// Create the DatagramSocket for receiving pings.
		int proposedUDPPort = Constants.Network.STARTING_UDP_CLIENT_PORT;
		while(!Utils.portAvailable(proposedUDPPort) && proposedUDPPort < Constants.Network.MAX_PORT_NUMBER) {
			proposedUDPPort++;
		}

		// This is bad.
		if(proposedUDPPort == Constants.Network.MAX_PORT_NUMBER) {
			throw new RuntimeException("No port available for client to set up a listening UDP channel!");
		}
	}

	public void connectToHost() {
		try {
			Socket socket = new Socket(hostName, hostPort);

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// Print out UDP Port.

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