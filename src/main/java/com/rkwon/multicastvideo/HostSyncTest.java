public class HostSyncTest {

	public static void main(String[] args) throws Exception {

		HostSync host = new HostSync(Constants.Network.HOST_SYNC_PORT, 3000);
		Thread hostThread = new Thread(host);
		System.out.println("HOST IP ADDRESS: " + host.hostAddress);

		hostThread.start();
		Thread.sleep(5000);
		System.out.println("Telling host to stop receiving clients.");
		host.stopAcceptingClients();

		host.ping(false);
		host.ping(false);
		host.ping(false);
		host.ping(true);

		
	}
}
