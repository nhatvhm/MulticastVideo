// Author: Ryan Kwon
// Consider https://limpet.net/mbrubeck/2014/08/08/toy-layout-engine-1.html
// to be inspiration.

/*
 * The Browser class will primarily be used to traverse to a
 * specific URL, capture video streaming data, and prepare it
 * for streaming.
 */

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.nodes.Document;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFrame;

import java.awt.BorderLayout;


// Some code inspired by http://www.sourcecodester.com/tutorials/java/5732/simple-web-browser-java.html
public class Browser extends JPanel {
    JEditorPane content;
	JScrollPane scrollPane;

    public Browser() {
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
	    content.setPage(htmlData);
	} catch(IOException e) {
		e.printStackTrace();
	}
    }

    // 
    public static void main(String[] args) {

	JFrame window = new JFrame("Browser");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setSize(800, 600);
	
	Browser browser = new Browser();
	
	
	try {
	    String url = "https://www.youtube.com/watch?v=9bZkp7q19f0";
	    //Document doc = Jsoup.connect(url).get();
	    //System.out.println(doc.toString());
	    browser.updateContent(url);

	} catch(Exception e) {
	    e.printStackTrace();
	}

	window.setVisible(true);
    }
}