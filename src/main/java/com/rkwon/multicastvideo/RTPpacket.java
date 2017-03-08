/*
 * An implementation of an RTPpacket.
 * Initial implementation inspired/borrowed from:
 * https://www.csee.umbc.edu/~pmundur/courses/CMSC691C/RTPpacket.html
 */

public class RTPpacket {
	
	// Size of the RTP header
	static int HEADER_SIZE = 12;
	
	// Fields that compose the RTP header
	public int version;
	public int padding;
	public int extension;
	public int cc;
	public int marker;
	public int payloadType;
	public int sequenceNumber;
	public int timeStamp;
	public int sSrc;
	
	// Bitstream of the RTP header
	public byte[] header;
	
	// Size of the payload
	public int payloadSize;
	
	// Bitstream of the RTP payload
	public byte[] payload;
	
	public RTPpacket(int pType, int frameNmbr, int time, byte[] data, int dataLength) {
		// Fill header fields by default:
		version = 2;
		padding = 0;
		extension = 0;
		cc = 0;
		marker = 0;
		sSrc = 0;
		
		// Fill changing header fields.
		sequenceNumber = frameNmbr;
		timeStamp = time;
		payloadType = pType;
		
		// Builder header bitstream
		header = new byte[HEADER_SIZE];
		buildHeader(header);
		
		payloadSize = dataLength;
		payload = data;
	}
	
	// Constructor from the packet bistream.
	public RTPpacket(byte[] packet, int packetSize) {
		
		// Fill default fields.
		version = 2;
		padding = 0;
		extension = 1;
		cc = 0;
		marker = 0;
		sSrc = 0;
		
		// Check if total packet size is lower than the header size.
		if (packetSize >= HEADER_SIZE) {
			// Get header bitstream:
			header = new byte[HEADER_SIZE];
			for(int i=0; i < HEADER_SIZE; i++) {
				header[i] = packet[i];
			}
			
			// Get payload bitstream
			payloadSize = packetSize - HEADER_SIZE;
			payload = new byte[payloadSize];
			for(int i = HEADER_SIZE; i < packetSize; i++) {
				payload[i-HEADER_SIZE] = packet[i];
			}
			
			// Interpret some changing fields of the header.
			// TODO: BE CAREFUL WITH COMPUTATIONS BELOW.
			payloadType = header[1] & 127;
			int hm1, hm2, hm3, hm4;
			hm1 = RTPpacket.convertByteToInt(header[3], 0);
			hm2 = RTPpacket.convertByteToInt(header[2], 8);
			sequenceNumber = hm1 + hm2;
			
			hm1 = RTPpacket.convertByteToInt(header[7], 0);
			hm2 = RTPpacket.convertByteToInt(header[6], 8);
			hm3 = RTPpacket.convertByteToInt(header[5], 16);
			hm4 = RTPpacket.convertByteToInt(header[4], 24);
			timeStamp = hm1 + hm2 + hm3 + hm4;
		}
	}
	
	// Return the payload bitstream of the RTPpacket and its size.
	public int getPayload(byte[] data) {
		for(int i = 0; i < payloadSize; i++) {
			data[i] = payload[i];
		}
		
		return payloadSize;
	}
	
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	public int getPayloadType() {
		return payloadType;
	}
	
	
	// An in-place helper method to build the RTP header.
	// Constructed according to:
	// https://en.wikipedia.org/wiki/Real-time_Transport_Protocol#Packet_header
	private void buildHeader(byte[] header) {
		
		// Build the first eight bits. Contains:
		// Version : 0-1
		// Padding : 2
		// Extension: 3
		// CSRC Count: 4-7
		byte temp = 0;
		temp |= (version << 6);
		temp |= (padding << 5);
		temp |= (extension << 4);
		temp |= cc;
		header[0] = temp;
		
		// 2nd set of 8 bits. Contains:
		// Marker: 1 bit
		// Payload Type: 7 bits
		temp = 0;
		temp |= (marker << 7);
		temp |= payloadType;
		header[1] = temp;
		
		// 2 bytes are dedicated to sequenceNumber
		byte bitmask = -1; // Bytes are signed. :(
		byte first = 0, second = 0;
		first |= ((sequenceNumber & (bitmask << 8)) >> 8);
		second |= (sequenceNumber & (bitmask));
		header[2] = first;
		header[3] = second;
		
		// 4 bytes dedicated to timestamp.
		first = second = 0;
		byte third = 0, fourth = 0;
		first |= ((timeStamp & (bitmask << 24)) >> 24);
		second |= ((timeStamp & (bitmask << 16)) >> 16);
		third |= ((timeStamp & (bitmask << 8)) >> 8);
		fourth |= (timeStamp & (bitmask));
		header[4] = first;
		header[5] = second;
		header[6] = third;
		header[7] = fourth;
		
		// 4 bytes dedicated to Synchronization Source Identifier.
		// This is sSrc.
		first = second = third = fourth = 0;
		first |= ((sSrc & (bitmask << 24)) >> 24);
		second |= ((sSrc & (bitmask << 16)) >> 16);
		third |= ((sSrc & (bitmask << 8)) >> 8);
		fourth |= (sSrc & (bitmask));
		
		header[8] = first;
		header[9] = second;
		header[10] = third;
		header[11] = fourth;
	}
	
	// Java has some nonsensical byte-to-int conversions.
	// This is the muchhhh more intuitive one, with no sign-conserving
	// byte-to-int expansions.
	public static int convertByteToInt(byte x, int shift) {
		int eraseNonByte = 0xFF;
		return (eraseNonByte & x) << shift;
	}
	
	// For testing.
	public static void main(String[] args) {
		byte first, second;
		first = 127;
		second = -1;
		int hm1 = 0, hm2 = 0;
		hm1 = convertByteToInt(first, 0);
		hm2 = convertByteToInt(second, 8);
		
		//System.out.format("%x\n", second);
		//System.out.format("%x\n", hm1);
		//System.out.format("%x\n", hm2);
		//System.out.format("%x\n", hm1 + hm2);
		
		int pType = 1;
		int frameNumber = 2;
		int time = 1230;
		byte[] data = new byte[2];
		int dataLength = 2;
		RTPpacket packet = new RTPpacket(pType, frameNumber, time, data, dataLength);
		
		for(int i = 0; i < HEADER_SIZE; i++) {
			System.out.format("%x\n", packet.header[i]);
		}
	}
}