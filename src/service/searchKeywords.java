package service;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import IndexingLucene.MyIndexReader;
import IndexingLucene.QueryRetrievalModel;
import ObjectsandTools.Document;
import ObjectsandTools.Path;
import ObjectsandTools.relativeName;
import PreprocessTools.WordNormalizer;
import PreprocessTools.stopwordRemover;

/*
 * This class is designed for searching Game Names by input keywords and filter options
 * Main features:
 * 1. search for result game names with given keywords
 * 2. filter results by given tags, publishers
 *
 * 
 * 
 * Attention: input CAN be null, if user don't want to input keywords, just search by other filter options
 */

public class searchKeywords {
	
	BufferedReader gamelistReader;
	
//	Constructor: open reader to read SearchGameList
	public searchKeywords(String path) throws FileNotFoundException {
		gamelistReader = new BufferedReader( new FileReader(new File(path)));
	}
	
	
	public ArrayList<relativeName> search(String indexpath, String keywords, ArrayList<String> taglist, String publisher, String releasedate) throws Exception {
		ArrayList<relativeName> result = new ArrayList<relativeName>();
		
//		keywords can be empty
		if (keywords.equals("")) {
			
			String line = "";
			String tempname = "";
			String temptag = "";
			String temppublisher = "";
			String tempreleasedate = "";
			int temprating;
			
			boolean ifContains = false;
			
			while ((line = gamelistReader.readLine()) != null) {
				if (line.contains("\t\t\t\t\"appid\":")) {
					ifContains = true;
				}
				else if (ifContains) {
					if (line.contains("\t\t\t\t\"original name\":")) {
						tempname = line.trim().substring(16);
					}
					else if (!taglist.isEmpty() && line.contains("\t\t\t\t\"tag\":")) {
						
						int length = line.trim().length();
						temptag = line.trim().substring(7, length - 1);
						for (String st : taglist) {
							if (!temptag.contains(st)) {
								ifContains = false;
							}
						}
					}
					else if (!publisher.equals("") && line.contains("\t\t\t\t\"publisher\":")) {
						
						int length = line.trim().length();
						temppublisher = line.trim().substring(13, length - 1);
						if (!temppublisher.equals(publisher)) {
							ifContains = false;
						}
					}
					else if (!releasedate.equals("") && line.contains("\t\t\t\t\"release date\":")) {
						
						int length = line.trim().length();
						tempreleasedate = line.trim().substring(16, length - 1);
						if (!tempreleasedate.equals(releasedate)) {
							ifContains = false;
						}
					}
					else if (line.contains("\t\t\t\t\"rating\":")) {
						int length = line.trim().length();
						String score = line.trim().substring(10, length - 1);
						if (score.equals("-1")) {
							temprating = 0;
						}
						else {
							temprating = Integer.parseInt(score);
						}
						result.add(new relativeName(0, tempname, temprating));
						ifContains = false;
					}
				}
					
			}
			
			gamelistReader.close();
//			test result
//			print(result);
			return result;
		}
//		if query not empty:
		else {
			
//			first process keywords
			String processedQuery = process(keywords);
			
			HashSet<String> appIds = new HashSet<String>();
			MyIndexReader ixreader = new MyIndexReader(indexpath);
			// Initialize the MyRetrievalModel
			QueryRetrievalModel model = new QueryRetrievalModel(ixreader);
//			use Lucene to read index and get topN results for keywords
			List<Document> relativeDocs = model.retrieveQuery(processedQuery, 100);
//			put results into appIds
			if (relativeDocs != null) {
				for (Document doc : relativeDocs) {
					appIds.add(doc.docno());
				}
			}
			
			boolean ifContains = false;
			String line = "";
			String tempname = "";
			String temptag = "";
			String temppublisher = "";
			String tempreleasedate = "";
			int temprating;
//			go through each line of gamelist to get information
			while ((line = gamelistReader.readLine()) != null) {
				if (line.contains("\t\t\t\t\"appid\":")) {
					int length = line.trim().length();
					String id = line.trim().substring(9, length - 1);
					if (appIds.contains(id)) {
						ifContains = true;
					}
				}
				else if (ifContains) {
//					accoring to users' options to achieve results
					if (line.contains("\t\t\t\t\"original name\":")) {
						tempname = line.trim().substring(16);
					}
					else if (!taglist.isEmpty() && line.contains("\t\t\t\t\"tag\":")) {
						
						int length = line.trim().length();
						temptag = line.trim().substring(7, length - 1);
						for (String st : taglist) {
							if (!temptag.contains(st)) {
								ifContains = false;
							}
						}
					}
					else if (!publisher.equals("") && line.contains("\t\t\t\t\"publisher\":")) {
						
						int length = line.trim().length();
						temppublisher = line.trim().substring(13, length - 1);
						if (!temppublisher.equals(publisher)) {
								ifContains = false;
						}
					}
					else if (!releasedate.equals("") && line.contains("\t\t\t\t\"release date\":")) {
						
						int length = line.trim().length();
						tempreleasedate = line.trim().substring(16, length - 1);
						if (!tempreleasedate.equals(releasedate)) {
								ifContains = false;
						}
					}
					else if (line.contains("\t\t\t\t\"rating\":")) {
						int length = line.trim().length();
						String score = line.trim().substring(10, length - 1);
						if (score.equals("-1")) {
							temprating = 0;
						}
						else {
							temprating = Integer.parseInt(score);
						}
						result.add(new relativeName(0, tempname, temprating));
						ifContains = false;
					}
				}
					
			}
			ixreader.close();
			gamelistReader.close();
//			test result 
//			print(result);
			return result;
		}
		
		
	}
//	void print(ArrayList<relativeName> result) {
//		for (relativeName rn : result) {
//			System.out.println(rn.name + " " +  rn.rating);
//		}
//	}
//	
	String process(String line) throws IOException {
		
		WordNormalizer wn = new WordNormalizer();
		stopwordRemover swr = new stopwordRemover();
		String[] tokenList = line.split("[ .,!?]");
		StringBuilder result = new StringBuilder();
		for (String token : tokenList) {
			token = token.toLowerCase();
			if (!swr.isStopword(token)) {
				
				result.append(wn.stem(token) + " ");
			}
			System.out.println(token);
		}
		return result.toString().replaceAll("-+", " ").replaceAll("\t+", " ").replaceAll(" +", " ").trim();
	}
	
	
}
