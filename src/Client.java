/*
 * Handles the client application, which is intended to connect to a host,
 * received streamed video data, and display that video.
 */
 
import java.io.*;
import java.net.*;
import java.util.*;
 
 // Initial version inspired by:
 // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/datagrams/examples/MulticastClient.java
 public class Client {
	MulticastSocket socket;
	InetAddress group;
	
	public Client() {
		try{
			socket = new DatagramSocket(Constants.Network.SOCKET_PORT_NUMBER);
			group = InetAddress.getByName(Constants.Network.INET_ADDRESS);
		} catch(SocketException e) {
			// I should almost definitely determine a new port from here.
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public boolean connect() {
		System.out.println("Client connecting...");
		socket.joinGroup(group);
		
		receiveData();
	}
	
	public void receiveData() {
		DatagramPacket packet;
	
		for(int i = 0; i < 9999; i++) {
			byte[] buf = new byte[256];
			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			
			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println(received);
		}
		
		disconnect();
	}
	
	public void disconnect() {
		System.out.println("Disconnecting...");
		socket.leaveGroup(group);
		socket.close();
	}
	
 }