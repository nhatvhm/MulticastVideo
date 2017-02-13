// Author: Ryan Kwon
// Consider https://limpet.net/mbrubeck/2014/08/08/toy-layout-engine-1.html
// to be inspiration.

// The Browser class will primarily be used to 

import org.jsoup.*;
import org.jsoup.nodes.Document;

public class Browser {

    

    public static void main(String[] args) {
	try {
	    Document doc = Jsoup.connect("https://jsoup.org/cookbook/input/load-document-from-url").get();
	    System.out.println(doc);
	} catch(Exception e) {
	    System.err.println(e);
	}
    }
}