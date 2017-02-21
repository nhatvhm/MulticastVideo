/*
 * In charge of running the program.
 */
 
 public class ClientApplication {
	public static void main(String[] args) {
		Host host = new Host("MulticastTest");
		host.run();
	}
 }