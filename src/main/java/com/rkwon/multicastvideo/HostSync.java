import java.io.*;
import java.net.*;
import java.util.*;

// A class to track and measure latency among clients.
// Will also be used to send relevant data to clients.

public class HostSync extends Thread {

	public int portNumber;
	public ServerSocket serverSocket;
	public HashMap<Integer, NetworkLog> networkLog;


	public HostSync() {
		portNumber = Constants.Network.HOST_SYNC_PORT;
		
		try {

			serverSocket = new ServerSocket(portNumber);
			//Socket clientSocket = serverSocket.accept();
			//out = new PrintWriter(clientSocket.getOutputStream(), true);
			//in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// Run:
	// Want to continue accepting connections. 
	// For every accepted connection, grab relevant network data.
	// Store relevant network data if appropriate option is selected

	// Log:
	// Permanently save stored network data.

}