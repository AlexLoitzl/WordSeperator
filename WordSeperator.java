import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * public class WordSeperator
 * 
 * Provides Capability to split compound words. You an either instanciate it as an Object to use in another context,
 * or run it on the Commandline expecting a line seperated dictionary as input. Uses public class Trie to store dictionary.
 * 
 * @author Alex
 *
 */
public class WordSeperator {
	/**
	 * Returns all subwords of word as a String Array.
	 * Traverses through the tree, if a subword was found, tries the rest, if not increases starting char.
	 * 
	 * @param word
	 * @param trie
	 * @return String Array of subwords
	 */
	public String[] trieSplit(String word, Trie trie) {
		word = word.toLowerCase();
		ArrayList<String> list = new ArrayList<String>();
		int EndIndex;
		while(word.length() > 1) {
			EndIndex = trie.findSubWord(word);
			if(EndIndex == -1) {
				word = word.substring(1,word.length());
			}else {
				list.add(word.substring(0,EndIndex+1));
				word = word.substring(EndIndex+1);
			}
		}
		return (String[]) list.toArray(new String[0]);
	}
	/**
	 * CLI
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: WordSeparator <Dictionary>");
		}else {
			try (Scanner s = new Scanner(System.in); BufferedReader reader = new BufferedReader(new FileReader(args[0], StandardCharsets.UTF_8))){
				Trie dict = new Trie(reader);
				reader.close();
				String input = "";
				WordSeperator sep = new WordSeperator();
				while(!input.equals("q")) {
					System.out.print("Enter Word to be separated or q to quit: ");
					input = s.next().strip();
					if(!input.equals("q")) {
						System.out.println(Arrays.toString(sep.trieSplit(input,dict)));
					}
				}
			}catch(FileNotFoundException e) {
				System.out.println("File not found. Try rerunning with a different parameter");
			}catch(IOException e) {
				System.out.println("There was an issue handling file. Try rerunning with a different parameter");
			}catch(InputMismatchException e) {
				System.out.println("There was an issue with your input. Rerun the programm and try a different input");
			}catch(Exception e) {
				System.out.println("An error occured. Try rerunnning the programm");
			}
		}
	}
}