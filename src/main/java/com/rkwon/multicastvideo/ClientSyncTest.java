public class ClientSyncTest {
	public static void main(String [] args) {
		System.out.println("Starting Client Sync Test");
		ClientSync client = new ClientSync(null, "137.165.74.106", Constants.Network.HOST_SYNC_PORT);
		Thread clientThread = new Thread(client);

		clientThread.start();
	}
}