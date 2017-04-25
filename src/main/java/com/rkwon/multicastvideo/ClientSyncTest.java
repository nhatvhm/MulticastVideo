public class ClientSyncTest {
	public static void main(String [] args) {
		System.out.println("Starting Client Sync Test");
		System.out.println("Username is: " + System.getProperty("user.name"));

		ClientSync client = new ClientSync(null, "137.165.8.120", Constants.Network.HOST_SYNC_PORT);
		Thread clientThread = new Thread(client);

		clientThread.start();
	}
}
