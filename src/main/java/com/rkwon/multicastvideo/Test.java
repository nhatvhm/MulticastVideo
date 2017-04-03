import java.util.HashSet;
import java.util.Arrays;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.NativeLibrary;

public class Test {

	public static void main(String[] args) {

		System.out.println("Test started!");
        
		NativeDiscovery nativeDiscovery = new NativeDiscovery();

		boolean found = false;

		found = nativeDiscovery.discover();
		
		System.out.println(found);
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());
	}
}