package PreprocessTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import ObjectsandTools.Path;



public class stopwordRemover {
	//use HashSet to store the stopwords because it does a good time performance when check if contains some words
	private static HashSet<String> words = new HashSet <String> ();
	
	
	public stopwordRemover( ) throws IOException {
		// load and store the stop words from specific location

		
		
		String line = "";
		File f = new File(Path.StopwordDir);
		BufferedReader reader = new BufferedReader(new FileReader(f));
		while ((line = reader.readLine()) != null) {
			words.add(line);
		}
		reader.close();
	}
	
	public boolean isStopword(String word ) {
		// return true if the input word is a stopword, or false if not
		
		if(words.contains(word)) {
			return true;
		}
		
		return false;
	}
}