
/*
 * Host actually delivers some specified video/audio data
 * to clients.
 */

 import java.io.*;
 import java.net.*;
 import java.util.*;
 import net.bramp.ffmpeg.*;
 import net.bramp.ffmpeg.builder.*;

 // Initial version strongly influenced by:
 // https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/networking/datagrams/examples/MulticastServerThread.java
public class Host extends Thread {
	private DatagramSocket socket;
	private FFmpegExecutor executor;
	//private InetAddress group;

	public Host() {
		this("Host");
	}

	public Host(String name) {
		super(name);

		System.out.println("Host starting up...");

		try{
			socket = new DatagramSocket(Constants.Network.HOST_PORT);
		} catch(SocketException e) {
			// I should almost definitely determine a new port from here.
			e.printStackTrace();
		}

	}

	// In the future, I hopefully won't need to download the video files
	// I want to send downstream.
	// Current version is for testing/other aspects of the program
	// though.
	public byte[] selectVideoFile() {
		File videoFile = new File("res/FFbyMitski.mp4");
		byte[] bytesArray = new byte[(int) videoFile.length()];

		return bytesArray;
	}

	public boolean continueStreaming() {
		return true;
	}

	// Set up the FFMPEG wrapper to let us stream video.
	public void setUpFFmpegWrapper() {
		FFmpeg ffmpeg;
		FFprobe ffprobe;

		try {
			ffmpeg = new FFmpeg("ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffmpeg.exe");
			ffprobe = new FFprobe("ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffprobe.exe");

			FFmpegBuilder builder = new FFmpegBuilder()
				.setInput("res/FFbyMitski.mp4")
				.addOutput("rtp://" + Constants.Network.INET_ADDRESS + ":" + Constants.Network.CLIENT_PORT)
					.setFormat("mp4")
					.addExtraArgs("-bufsize", "4000k")
					.addExtraArgs("-maxrate", "1000k")
					.done();

			executor = new FFmpegExecutor(ffmpeg);
			executor.createJob(builder).run();
		}  catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		setUpFFmpegWrapper();

		/*
		byte[] videoFile = selectVideoFile();
		int bufferSize = Constants.DataSizes.MAX_UDP_SIZE;
		//byte[] buf = new byte[Constants.DataSizes.MB];
		int numOfPacketsToSend = videoFile.length / bufferSize;

		while(continueStreaming()) {

			for(int i = 0; i < numOfPacketsToSend; i++) {
				System.out.println("Sending packet " + i + " ...");
				int startOffset = numOfPacketsToSend * bufferSize;
				int end = startOffset + bufferSize;

				byte[] buf = Arrays.copyOfRange(videoFile, startOffset, end);
				try {
					InetAddress group = InetAddress.getByName(Constants.Network.INET_ADDRESS);
					DatagramPacket packet = new DatagramPacket(buf, buf.length, group, Constants.Network.CLIENT_PORT);
					socket.send(packet);
				} catch(UnknownHostException e) {
					e.printStackTrace();
				} catch(IOException e) {
					e.printStackTrace();
				}

				try{
					sleep(2000L);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		socket.close();
		*/
	}


}
