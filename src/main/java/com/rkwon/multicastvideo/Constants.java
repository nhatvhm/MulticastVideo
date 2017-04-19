/*
 * Constants stores any project-wide constants.
 */
 
import java.io.*;	
import java.net.*;
import java.util.*;

public class Constants {
	class Network {
		public static final int HOST_PORT = 4445;
		public static final int CLIENT_PORT = 4446;
		public static final String INET_ADDRESS = "230.0.0.1";

		public static final int HOST_SYNC_PORT = 7532;

		public static final int HOST_UDP_PORT = 7423;

		// Locally: tries this UDP port for receiving server pings.
		// If the port is occupied/throws an exception, we increment and try again.
		// We send this port to the server so it knows exactly what the client UDP port is.
		public static final int STARTING_UDP_CLIENT_PORT = 7540;

		public static final int MAX_PORT_NUMBER = 65535;

		public static final byte STOP_WAITING_FOR_PINGS = 123;
	}
	
	class DataSizes {
		public static final int MAX_UDP_SIZE = 65507;
		public static final int KB = 1024;
		public static final int MB = 1000 * KB;
	}
}