package Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ObjectsandTools.Path;
import PreprocessTools.WordNormalizer;
import PreprocessTools.stopwordRemover;

/*
 * This class is designed for build descriptions.trectext
 * The trectext file can be recognized by Lucene.
 */

public class BuildDescription {
	
	private BufferedReader br;
	private FileWriter fr;
	private File des_set;
	
	public BuildDescription() throws IOException {
		des_set = new File(Path.descriptions);
		fr = new FileWriter(Path.descriptionTrec);
	}
	
//	open all the descritions files and read them
	public void buildResult() throws IOException {
		for (File file : des_set.listFiles()) {
			
//			firstly record doc names
			if (file.getName().contains(".txt")) {
				String appid = file.getName().substring(0,file.getName().length() - 4);
				br = new BufferedReader( new FileReader(file));
				if (br.readLine() == null) {
					continue;
				}
				fr.append(appid + "\n");
				
//				then record content
				String line = "";
				int linecount = 0;
				while ((line = br.readLine()) != null) {
					linecount ++;
					if(linecount >= 2) {
						fr.append(process(line.trim()) + " ");
					}
				}
				br.close();	
				fr.append("\n");
			}
		}
		fr.close();
		
	}
	
//	process all the description:
//	1. lowercase
//	2. tokenize
//	3. stem
//	4. remove stop word
	String process(String line) throws IOException {
		
		WordNormalizer wn = new WordNormalizer();
		stopwordRemover swr = new stopwordRemover();
		
		String[] tokenList = line.split("[ .,!?]");
		StringBuilder result = new StringBuilder();
		
		for (String token : tokenList) {
			token = wn.lowercase(token);
			if (!swr.isStopword(token)) {
				
				result.append(wn.stem(token) + " ");
			}
		}
		return result.toString().replaceAll("-+", " ").replaceAll("\t+", " ").replaceAll(" +", " ").trim();
	}


}