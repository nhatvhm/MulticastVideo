import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Test {
	public static void main(String[] args) {

		System.out.println("Test started!");

		boolean found = new NativeDiscovery().discover();
		System.out.println(found);
		
		/*
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());
		*/
	}
}