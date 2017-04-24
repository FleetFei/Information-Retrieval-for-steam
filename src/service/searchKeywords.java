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
import ObjectsandTools.KeywordsResult;
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
	int x = 10;
	int y = 2;
	
//	Constructor: open reader to read SearchGameList
	public searchKeywords(String path) throws FileNotFoundException {
		gamelistReader = new BufferedReader( new FileReader(new File(path)));
	}
	
	
	public KeywordsResult search(String stopwordPath, String indexpath, String keywords, ArrayList<String> taglist, String publisher, String releasedate) throws Exception {
		ArrayList<relativeName> result = new ArrayList<relativeName>();
		
		
		if (taglist != null) {
			for (String st : taglist) {
				st.toLowerCase();
			}
		}

		publisher = publisher.toLowerCase();
		
		if (releasedate != null) {
			releasedate = releasedate.toLowerCase();
		}
		
		
//		keywords can be empty
		if (keywords == null || keywords.equals("")) {
			
			if (taglist == null && publisher.equals("unknown") && (releasedate.equals("") || releasedate == null)) {
				return null;
			}
			
			String line = "";
			String tempappid = "";
			String tempname = "";
			String temptag = "";
			String temppublisher = "";
			String tempreleasedate = "";
			int temprating;
			int infocount = 0;
			
			boolean ifContains = false;
			
			while ((line = gamelistReader.readLine()) != null) {
				if (line.contains("\t\t\t\t\"appid\":")) {
					int length = line.trim().length();
					tempappid = line.trim().substring(9, length - 1);
					ifContains = true;
				}
				else if (ifContains) {
					if (line.contains("\t\t\t\t\"original name\":")) {
						tempname = line.trim().substring(16);
					}
					else if (line.contains("\t\t\t\t\"tag\":")) {
						int length = line.trim().length();
						temptag = line.trim().substring(7, length - 1);
						
						if(!temptag.equals("none")) {
							infocount ++;
						}
						
						
						
						if (taglist != null) {
							
							ifContains = false;

							for (String st : taglist) {
								if (temptag.contains(st.toLowerCase())) {
									ifContains = true;
								}
							}
						}
					}
					else if (line.contains("\t\t\t\t\"publisher\":")) {
						
						int length = line.trim().length();
						temppublisher = line.trim().substring(13, length - 1);
						if (!publisher.equals("unknown") && !temppublisher.equals(publisher)) {
							ifContains = false;
						}
						
					}
					else if (line.contains("\t\t\t\t\"release date\":")) {
						
						int length = line.trim().length();
						tempreleasedate = line.trim().substring(16, length - 1);
						
						if(!tempreleasedate.equals("0")) {
							infocount ++;
						}
						
						
						if (releasedate != null && !releasedate.equals("")) {
							if (!tempreleasedate.equals(releasedate)) {
								ifContains = false;
							}
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
							infocount ++;

						}
						if (infocount >= 2) {
							result.add(new relativeName(tempappid, 0, tempname, temprating, tempreleasedate, temptag));
						}
						infocount = 0;
						ifContains = false;
					}
				}
					
			}
			
			gamelistReader.close();
//			test result
//			print(result);
			List<relativeName> finalresult = new ArrayList<relativeName>();
			if (result.size() < x) {
				finalresult = result;
			}
			else {
				finalresult = result.subList(0, x);
			}
			
			return new KeywordsResult(finalresult);
		}
//		if query not empty:
		else {

			
//			first process keywords
			String processedQuery = process(stopwordPath, keywords);
			
			HashSet<String> appIds = new HashSet<String>();
			MyIndexReader ixreader = new MyIndexReader(indexpath);
			// Initialize the MyRetrievalModel
			QueryRetrievalModel model = new QueryRetrievalModel(ixreader, indexpath);
//			use Lucene to read index and get topN results for keywords
			List<Document> relativeDocs = model.retrieveQuery(processedQuery, 100);
//			put results into appIds
			if (relativeDocs != null) {
				for (Document doc : relativeDocs) {
					System.out.println(doc.docno() + " " + doc.score());
					appIds.add(doc.docno());
				}
			}
			
			boolean ifContains = false;
			String line = "";
			String tempappid = "";
			String tempname = "";
			String temptag = "";
			String temppublisher = "";
			String tempreleasedate = "";
			int temprating;
			int infocount = 0;
//			go through each line of gamelist to get information
			while ((line = gamelistReader.readLine()) != null) {
				if (line.contains("\t\t\t\t\"appid\":")) {
					int length = line.trim().length();
					tempappid = line.trim().substring(9, length - 1);
					if (appIds.contains(tempappid)) {
						ifContains = true;
					}
				}
				else if (ifContains) {
//					accoring to users' options to achieve results
					if (line.contains("\t\t\t\t\"original name\":")) {
						
						tempname = line.trim().substring(16);
					}
					else if (line.contains("\t\t\t\t\"tag\":")) {

						int length = line.trim().length();
						temptag = line.trim().substring(7, length - 1);
						if(!temptag.equals("none")) {
							infocount ++;
						}
						if (taglist != null) {
							ifContains = false;
							for (String st : taglist) {
								if (temptag.contains(st.toLowerCase())) {
									ifContains = true;
								}
							}
						}
					}
					else if (line.contains("\t\t\t\t\"publisher\":")) {

						
						int length = line.trim().length();
						temppublisher = line.trim().substring(13, length - 1);
						
						
						if (!publisher.equals("unknown") && !temppublisher.equals(publisher)) {
								ifContains = false;
						}
					}
					else if (line.contains("\t\t\t\t\"release date\":")) {

						
						int length = line.trim().length();
						tempreleasedate = line.trim().substring(16, length - 1);
						if(!tempreleasedate.equals("0")) {
							infocount ++;
						}
						if (!releasedate.equals("") && releasedate != null) {
							if (!tempreleasedate.equals(releasedate)) {
								ifContains = false;
							}
						}
						
					}
					else if (line.contains("\t\t\t\t\"rating\":")) {
						int length = line.trim().length();
						String score = line.trim().substring(10, length - 1);
						if (score.equals("-1")) {
							temprating = 0;
						}
						else {
							infocount++;
							temprating = Integer.parseInt(score);
						}

						if (infocount >= y) {
							result.add(new relativeName(tempappid, 0, tempname, temprating, tempreleasedate, temptag));
						}
						infocount = 0;
						ifContains = false;
					}
				}
					
			}
			ixreader.close();
			gamelistReader.close();
//			test result 
//			print(result);
			List<relativeName> finalresult = new ArrayList<relativeName>();
			if (result.size() < x) {
				finalresult = result;
			}
			else {
				finalresult = result.subList(0, x);
			}
			return new KeywordsResult(finalresult);
		}
		
		
	}
//	void print(ArrayList<relativeName> result) {
//		for (relativeName rn : result) {
//			System.out.println(rn.name + " " +  rn.rating);
//		}
//	}
//	
	String process(String stopwordPath, String line) throws IOException {
		
		WordNormalizer wn = new WordNormalizer();
		stopwordRemover swr = new stopwordRemover(stopwordPath);
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
