import java.io.*;
import java.net.*;
import java.util.*;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class ClientSync implements Runnable {

	public String hostName;
	public int hostPort;

	public DatagramSocket clientUDPSocket;
	public int clientUDPPort;

	public boolean waitForPings = true;
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

		clientUDPPort = proposedUDPPort;

		try {
			clientUDPSocket = new DatagramSocket(proposedUDPPort);
		} catch(SocketException e) {
			e.printStackTrace();
		}

	}

	/////////////////////////////////////////////////////
	//
	// INTERNAL METHODS
	//

	public void connectToHost() {

		System.out.println("Trying to connect to host...");

		try {
			Socket socket = new Socket(hostName, hostPort);

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// Print out UDP Port.
			out.println(clientUDPPort);

			socket.close(); // Unsure if necessary/desirable.

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Host connected!");
	}

	public void pingable() {
		while(waitForPings) {
			System.out.println("Client is waiting for pings...");

			byte[] buf = new byte[9];

			try {

				DatagramPacket packet = new DatagramPacket(buf, buf.length);

				clientUDPSocket.receive(packet);

				System.out.println("Ping received!");

				// We immediately send a packet back. Then figure out if we should
				// keep waiting for more pings.

				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				clientUDPSocket.send(packet);

				System.out.println("Response sent back to server!");

				// Check to see if the last byte is equal to a specific flag.
				if(buf[9] == Constants.Network.STOP_WAITING_FOR_PINGS) {
					System.out.println("That was the last ping. No longer waiting!");
					waitForPings = false;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
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

		connectToHost();

		pingable();

		while( running ) {

		}
	}
}