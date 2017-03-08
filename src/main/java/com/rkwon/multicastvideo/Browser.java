// Author: Ryan Kwon
// Consider https://limpet.net/mbrubeck/2014/08/08/toy-layout-engine-1.html
// to be inspiration.

/*
 * The Browser class will primarily be used to traverse to a
 * specific URL, capture video streaming data, and prepare it
 * for streaming.
 */

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;


// Some code inspired by http://www.sourcecodester.com/tutorials/java/5732/simple-web-browser-java.html
public class Browser extends JPanel {
	String url;
	Document doc;
	Elements videoElements;

    JEditorPane content;
	JScrollPane scrollPane;

    public Browser(String URL) {
		this.url = URL;
		
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		videoElements = doc.select("[src]");
		
		printVideoElements();
		
		setLayout(new BorderLayout(1, 1));
		
		content = new JEditorPane();
		content.setContentType("text/html");
		content.setEditable(false);
		
		scrollPane = new JScrollPane(content);
		
		add(scrollPane, BorderLayout.CENTER);
    }

    // Bandaid method for now.
    public void updateContent(String htmlData) {
		try {
			//content.setPage(htmlData);
			content.setPage(htmlData);
		} catch(IOException e) {
			e.printStackTrace();
		}
    }
	
	public void printVideoElements() {
		for(Element src : videoElements) {
			if (src.tagName().equals("img"))
                System.out.format(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
						src.attr("alt"));
            else
                System.out.format(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
			
			System.out.println();
		}
	}
	
	public ArrayList<String> listVideoFiles() {
		return new ArrayList<String>();
	}

    // Main method.
    public static void main(String[] args) {

		JFrame window = new JFrame("Browser");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		int width = (int) (screenSize.getWidth() * .75);
		int height = (int) (screenSize.getHeight() * .75);
		
		window.setSize(width, height);
		
		Browser browser;
		
		try {
			String url = "https://www.youtube.com/watch?v=9bZkp7q19f0";
			
			
			
			
			browser = new Browser(url);
			browser.updateContent(url);

			window.add(browser);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		

		window.setVisible(true);
    }
}