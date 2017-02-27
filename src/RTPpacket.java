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
	}
	
	
	// A helper method to build the RTP header.
	// Constructed according to:
	// https://en.wikipedia.org/wiki/Real-time_Transport_Protocol#Packet_header
	private void buildHeader(byte[] header) {
		
		// Build the first eight bytes. Contains:
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
	}
}