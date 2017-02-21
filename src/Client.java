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
			socket = new MulticastSocket(Constants.Network.CLIENT_PORT);
			group = InetAddress.getByName(Constants.Network.INET_ADDRESS);
		} catch(IOException e) {
			// I should almost definitely determine a new port from here.
			e.printStackTrace();
		}
	}

	public void connect() {
		System.out.println("Client connecting...");
		
		try {
			socket.joinGroup(group);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Client connected!");
		
		receiveData();
	}
	
	public void receiveData() {
		DatagramPacket packet;
		byte[] buf = new byte[Constants.DataSizes.MB];
	
		for(int i = 0; i < 9999; i++) {
			packet = new DatagramPacket(buf, buf.length);
			
			try {
				socket.receive(packet);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println(received);
		}
		
		disconnect();
	}
	
	public void disconnect() {
		System.out.println("Disconnecting...");
		
		try {
			socket.leaveGroup(group);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		socket.close();
	}
	
 }