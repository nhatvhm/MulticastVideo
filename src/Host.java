
/*
 * Host actually delivers some specified video/audio data
 * to clients.
 */
 
 import java.io.*;
 import java.net.*;
 import java.util.*;
 
 // Initial version strongly influenced by:
 // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/datagrams/examples/MulticastServerThread.java
public class Host extends Thread {
	private DatagramSocket socket;
	private InetAddress group;
	
	public Host() {
		this("Host");
	}
	
	public Host(String name) {
		super(name);
		
		System.out.println("Host starting up...");
		
		try{
			socket = new DatagramSocket(Constants.HostData.SOCKET_PORT_NUMBER);
			group = InetAddress.getByName(Constants.HostData.INET_ADDRESS);
		} catch(SocketException e) {
			// I should almost definitely determine a new port from here.
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public boolean continueStreaming() {
		return true;
	}
	
	public void run() {	
		while(continueStreaming()) {
			byte[] buf = new byte[256];
			
			String sampleMessage = new Date().toString();
			buf = sampleMessage.getBytes();
			
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, Constants.HostData.SOCKET_PORT_NUMBER);
			
			try{ 
				sleep(5000L);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		socket.close();
	}
	
	
}