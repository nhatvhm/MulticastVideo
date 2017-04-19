import java.io.*;
import java.net.*;
import java.util.*;

import org.jsoup.*;

// A class to track and measure latency among clients in a session.
// Will also be used to send relevant data to clients.

public class HostSync implements Runnable {

	public boolean keepRunning = true;
	public boolean keepReceivingClients = true;

	public String hostAddress;
	public int portNumber;
	public ServerSocket serverSocket;
	public NetworkLog networkLog;

	public int averageVideoPositionAmongClients = 0;
	public HashMap<String, ClientConnection> clients;

	// Probably want a hashmap from IP Addresses to relevant client data?

	public HostSync() {

		try {
			hostAddress = Utils.getIP();
		} catch (Exception e) {
			e.printStackTrace();
			hostAddress = "ERROR"; // Good idea? Not sure. Probably not. Look into.
		}

		portNumber = Constants.Network.HOST_SYNC_PORT;
		networkLog = new NetworkLog();
		clients = new HashMap<String, ClientConnection>();
		
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////
	//
	// EXPOSED API
	//

	public void stopAcceptingClients() {
		keepReceivingClients = false;
	}


	/////////////////////////////////////////////////////
	//
	// INTERNAL METHODS
	//	

	public void receiveClients() {
		
		System.out.println("Host is now receiving clients...");

		try {
			serverSocket.setSoTimeout(2000);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		while(keepReceivingClients) {
			System.out.println("Host is waiting for client connection...");

			try {

				String clientData;
				Socket clientSocket = serverSocket.accept();

				// Tell the client that they've connected.
				// PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				// out.println("1");

				// We should receive a UDP Port Number from the client.
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				int clientUDPPort = Integer.parseInt(in.readLine());

				InetAddress addr = clientSocket.getInetAddress();
				int port = clientSocket.getPort();

				String identifier = addr.toString() + ":" + port;

				System.out.println("Host received a client with identifier: " + identifier);

				clients.put(identifier, new ClientConnection(addr, port, clientUDPPort));
				
			} catch(SocketTimeoutException e) {
				System.out.println("Host timed out without receiving a client. Trying again.");
				// We timed out without receiving a client.
			} catch(IOException e) {
				e.printStackTrace();
			} 

		}

		System.out.println("Host is no longer receiving clients.");

	}

	// Talk to all connected clients and attempt to estimate RTT (Round-Trip-Time) of a message.
	public void ping(boolean lastPing) {

		System.out.println("Host is sending a ping...");
		
		try {
			DatagramSocket pingSocket = new DatagramSocket(Constants.Network.HOST_UDP_PORT);

			for(ClientConnection cc : clients.values()) {

				InetAddress address = cc.addr;
				int port = cc.port; // Wrong port for ping?

				long currentTime = System.currentTimeMillis();

				byte[] buf = new byte[1];

				if(lastPing)
					buf[0] = Constants.Network.STOP_WAITING_FOR_PINGS;
				//byte[] buf = Utils.longToBytes(currentTime);
				
				try {
					DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
					pingSocket.send(packet);

					DatagramPacket receipt = new DatagramPacket(buf, buf.length);
					pingSocket.receive(receipt);
				} catch(IOException e) {
					e.printStackTrace();
				}

				long timeDifference = (System.currentTimeMillis() - currentTime) / 2;

				System.out.println("Host reported a time difference of " + timeDifference + " milliseconds from " + cc.addr);

				cc.addLatencyNumber((System.currentTimeMillis() - currentTime) / 2);
			}

		} catch(SocketException e) {
			e.printStackTrace();
		}

		System.out.println("Host done sending pings.");

	}

	// Continue accepting connections
	// For every accepted connection grab relevant network data.
	public void beginLogging() {
		try {
			serverSocket.setSoTimeout(0);
		} catch(SocketException e) {
			e.printStackTrace();
		}
		

		while(keepRunning) {

			try {
				String clientData;
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				while((clientData = in.readLine()) != null) {
					// Process clientData and submit it to NetworkLog and
					// use it to measure relevant data points.
				}
			} catch(IOException e) {
				networkLog.registerError();
			}

		}
	}

	// Run:
	// Want to continue accepting connections. 
	// For every accepted connection, grab relevant network data.
	// Store relevant network data if appropriate option is selected

	@Override
	public void run() {

		receiveClients();

		beginLogging();
	}

	public void stop() {
		keepRunning = false;
	}

	// Log:
	// Permanently save stored network data.

}

// A wrapper class to contain some information about an individual client connection.
class ClientConnection {
	public InetAddress addr;
	public int port;
	public ArrayList<Long> latencyEstimates;
	public int udpPort;


	public ClientConnection(InetAddress addr, int port, int udpPort) {
		this.addr = addr;
		this.port = port;
		this.udpPort = udpPort;
		latencyEstimates = new ArrayList<Long>();
	}

	public void addLatencyNumber(long latencyVal) {
		latencyEstimates.add(latencyVal);
	}
}