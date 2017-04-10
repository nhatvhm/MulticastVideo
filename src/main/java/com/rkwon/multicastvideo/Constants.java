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
	}
	
	class DataSizes {
		public static final int MAX_UDP_SIZE = 65507;
		public static final int KB = 1024;
		public static final int MB = 1000 * KB;
	}
}