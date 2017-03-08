/*
 * In charge of running the program.
 */
 
 import java.io.*;
import java.net.*;
import java.util.*;
 
 public class ClientApplication {
	public static void main(String[] args) {
		Client client = new Client();
		client.connect();
	}
 }