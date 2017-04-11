import java.io.*;
import java.net.*;
import java.util.*;

// A class to track and measure latency among clients in a session.
// Will also be used to send relevant data to clients.

public class HostSync implements Runnable {

	public boolean keepRunning = true;

	public int portNumber;
	public ServerSocket serverSocket;
	public NetworkLog networkLog;


	public HostSync() {
		portNumber = Constants.Network.HOST_SYNC_PORT;
		networkLog = new NetworkLog();
		
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

	@Override
	public void run() {
		// Continue accepting connections
		// For every accepted connection grab relevant network data.

		while(keepRunning) {

			try {
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch(IOException e) {
				networkLog.registerError();
			}



		}
	}

	public void stop() {
		keepRunning = false;
	}

	// Log:
	// Permanently save stored network data.

}