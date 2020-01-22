import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
/**
 * public class Trie
 * 
 * Stores words in a Tree, where each node represents a character and stores . The path from the root to this node
 * forms a string being potentially a word.(if contained == true). Every node contains a HashMap storing key value pairs of nodes,
 * instead of storing potential null pointers in arrays.
 * 
 * @author Alex
 *
 */
public class Trie {
	private class Node{
		private HashMap<Character, Node> children;
		private boolean contained;
		private char letter;
		
		private Node(char c) {
			children = new HashMap<Character, Node>();
			letter = c;
		}
		private Node() {
			children = new HashMap<Character, Node>();
		}
	}
	private Node root;
	
	public Trie() {
		root = new Node();
	}
	/**
	 * Constructor used for reading a dictionary, which is required to have line seperated words
	 * @param reader
	 * @throws IOException
	 */
	public Trie(BufferedReader reader) throws IOException{
		root = new Node();
		readDictFromFile(reader);
	}
	/**
	 * Used for reading a dictionary, which is required to have line seperated words
	 * @param reader
	 * @throws IOException
	 */
	public void readDictFromFile(BufferedReader reader) throws IOException{
		String word;
		while((word = reader.readLine())!=null) {
			insert(word);
		}
	}
	/**
	 * Runs through the tree character by character until the end of word. Creates HashMap entries on the way
	 * @param word
	 */
	public void insert(String word) {
		Node pointer = root;
		word = word.toLowerCase();
		for(int i = 0; i < word.length(); i++) {
			pointer = pointer.children.computeIfAbsent(word.charAt(i), c -> new Node(c));
		}
		pointer.contained = true;
	}
	/**
	 * Runs through the Tree to find word
	 * @param word
	 * @return true if word was found, false otherwise
	 */
	public boolean find(String word) {
		Node pointer = root;
		for(int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			Node node = pointer.children.get(c);
			if(node == null) {
				return false;
			}
			pointer = node;
		}
		return pointer.contained;
	}
	/**
	 * Runs through Tree to find a potential subword contained in word
	 * @param word
	 * @return index of last char of subword, -1 if not found
	 */
	public int findSubWord(String word) {
		Node pointer = root;
		for(int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			Node node = pointer.children.get(c);
			if(node == null) {
				return -1;
			}
			pointer = node;
			if(pointer.contained) {
				return i;
			}
		}
		return -1;
	}
}
