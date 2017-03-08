/*
 * Handles the client application, which is intended to connect to a host,
 * received streamed video data, and display that video.
 */
 
import java.io.*;
import java.net.*;
import java.util.*;

import net.bramp.ffmpeg.*;
import net.bramp.ffmpeg.builder.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

 // Initial version inspired by:
 // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/datagrams/examples/MulticastClient.java
 public class Client extends JFrame {
	MulticastSocket socket;
	InetAddress group;
	FFmpegExecutor executor;
	
	public Client() {
		super("Multicast Video Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = (int) (screenSize.getWidth() * .75);
		int height = (int) (screenSize.getHeight() * .75);
		
		setSize(width, height);

		try{
			socket = new MulticastSocket(Constants.Network.CLIENT_PORT);
			group = InetAddress.getByName(Constants.Network.INET_ADDRESS);
		} catch(IOException e) {
			// I should almost definitely determine a new port from here.
			e.printStackTrace();
		}

		setVisible(true);
	}

	public void connect() {
		System.out.println("Client connecting...");
		
		try {
			socket.joinGroup(group);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Client connected!");
		
		//runFFPlay();
		receiveData();
	}


	// TODO: FIX OR REMOVE.
	// Doesn't work.
	public void runFFPlay() {
		FFmpeg ffmpeg;
		FFprobe ffprobe;

		try {
			ffmpeg = new FFmpeg("ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffmpeg.exe");
			ffprobe = new FFprobe("ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffprobe.exe");

			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput("rtp://" + Constants.Network.INET_ADDRESS + ":" + Constants.Network.CLIENT_PORT)
				.addOutput("ffplay")
					.done();

			executor = new FFmpegExecutor(ffmpeg);
			executor.createJob(builder).run();
		}  catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveData() {
		DatagramPacket packet;
		byte[] buf = new byte[Constants.DataSizes.MAX_UDP_SIZE];
	
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