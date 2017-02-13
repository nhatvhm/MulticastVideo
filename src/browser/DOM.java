// A class to define DOM, which consists of a tree of nodes.
// https://limpet.net/mbrubeck/2014/08/08/toy-layout-engine-1.html

import java.util.ArrayList;
import java.util.HashMap;

// Simple main class.
class Node {
    public ArrayList<Node> children;
    

    public static TextNode text(String data) {
	return new TextNode(new ArrayList<Node>(), data);
    }

    public static ElementNode elem(String name, HashMap<String, String> attrMap, ArrayList<Node> children) {
	return new ElementNode(name, attrMap, children);
    }
}

/*
 * Subclasses of Node, specifying individual Node Types.
 */

class TextNode extends Node {
    String text;

    public TextNode(ArrayList<Node> children, String data) {
	this.children = children;
	this.text = data;
    }
}

class ElementNode extends Node {
    String tagName;
    HashMap<String, String> attributes;

    public ElementNode(String name, HashMap<String, String> attributeMap, ArrayList<Node> children) {
	this.children = children;
	this.tagName = name;
	this.attributes = attributeMap;
    }
}