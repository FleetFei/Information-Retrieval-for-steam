import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class searchQuery {
	
	private BufferedReader br;
//	private FileWriter fr;
	
	public searchQuery() throws IOException {
		br = new BufferedReader( new FileReader(new File(path.lowerCaseGamelist)));
//		fr = new FileWriter(path.resultlocation);
	}
	
//	mainly it is designed for search while typing
	public TypingResult search1(String query) throws IOException {
		
		String line = "";
		String templine = "";
		boolean ifContains = false;
		int queryLocation = -1;
		ArrayList<relativeName> result = new ArrayList<relativeName>();  
		query = query.toLowerCase().replaceAll("[:()!?_~; &-\'\"./]", "");
		
		
		while ((line = br.readLine()) != null) {
			if (line.contains("\t\t\t\t\"original name\":")) {
				templine = line.trim().substring(16);
				continue;
			}
			if(line.contains("\t\t\t\t\"name\":") && line.contains(query)) {
				queryLocation = line.trim().substring(7).indexOf(query);
				ifContains = true;
				continue;
			}
			if(ifContains) {
				String st2 = line.trim().substring(7);
				if(st2.charAt(queryLocation) == '$')
				{
					if (queryLocation == 1) {
						result.add(new relativeName(1, templine));
					}
					else {
						result.add(new relativeName(0, templine));
					}
				}
				ifContains = false;
			}
			
		}
		result.sort(new ResultComparator());
		TypingResult typeresult = new TypingResult(result);
		typeresult.print();
		
		
		br.close();
		return typeresult;
	}
	
	
//  it is designed for search after pressing "enter"
	public EnterSearchResult search3(String query) throws IOException {
		
		
		String line = "";
		String templine = "";
		boolean ifContains = false;
		int queryLocation = -1;
		ArrayList<String> result1 = new ArrayList<String>();
		ArrayList<relativeName> result2 = new ArrayList<relativeName>();
		ArrayList<relativeName> result3 = new ArrayList<relativeName>();
		query = query.toLowerCase().replaceAll("[:()!?_~; &-\'\"./]", "");
//		String[] querylist = query.split(" ");
//		
//		System.out.println(querylist.length);
		
		ArrayList<String> division = new ArrayList<String>();
		int n = 4;
		if (query.length() < 20) {
			n = 3;
		}
		for (int i = 0; i < query.length(); i ++) {
			if (i + n <= query.length()) {
				division.add(query.substring(i, i+n));
			}
			else {
				continue;
			}
		}
		
		while ((line = br.readLine()) != null) {
			if (line.contains("\t\t\t\t\"original name\":")) {
				templine = line.trim().substring(16);
				continue;
			}
			if(line.contains("\t\t\t\t\"name\":")) {
				if (line.contains(query)) {
					queryLocation = line.trim().substring(7).indexOf(query);
					ifContains = true;
					continue;
				}
				else {
					int containsCount = 0;
					int nocontainsCount = 0;
					for (String st : division) {
						if (line.contains(st)) {
							containsCount ++;
						}
						else {
							nocontainsCount ++;
						}
					}
					if (containsCount >= nocontainsCount) {
						result3.add(new relativeName((containsCount - nocontainsCount), templine));
					}					
				}	
			}
			if(ifContains) {
				String st2 = line.trim().substring(7);
				if(st2.charAt(queryLocation) == '$')
				{
					if (queryLocation == 1) {
						result2.add(new relativeName(1, templine));
					}
					else {
						result2.add(new relativeName(0, templine));
					}
				}
				else {
					result1.add(templine);
				}
				ifContains = false;
			}
			
		}
		result2.sort(new ResultComparator());
		result3.sort(new ResultComparator());
		
		
		EnterSearchResult result = new EnterSearchResult(result1, result2, result3);
		result.print();
		br.close();
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	void print(ArrayList<String> result) {
		for (String string : result) {
			System.out.println(string);
		}
	}
	
	void print2(ArrayList<relativeName> result) {
		for (relativeName rn : result) {
			System.out.println(rn.name + " " + rn.score);
		}
	}

	
//	public ArrayList<String> search2(String query) throws IOException {
//		
//		String line = "";
//		String templine = "";
//		boolean ifContains = false;
//		int queryLocation = -1;
//		ArrayList<String> result1 = new ArrayList<String>();
//		ArrayList<relativeName> result2 = new ArrayList<relativeName>();
//		query = query.toLowerCase().replaceAll("[:()!?_~; &-\'\"./]", "");
//
//		
//		while ((line = br.readLine()) != null) {
//			if (line.contains("\t\t\t\t\"original name\":")) {
//				templine = line.trim().substring(16);
//				continue;
//			}
//			if(line.contains("\t\t\t\t\"name\":") && line.contains(query)) {
//				queryLocation = line.trim().substring(7).indexOf(query);
//				ifContains = true;
//				continue;
//			}
//			if(ifContains) {
//				String st2 = line.trim().substring(7);
//				if(st2.charAt(queryLocation) == '$')
//				{
//					if (queryLocation == 1) {
//						result2.add(new relativeName(1, templine));
//					}
//					else {
//						result2.add(new relativeName(0, templine));
//					}
//				}
//				else {
//					result1.add(templine);
//				}
//				ifContains = false;
//			}
//			
//		}
//		result2.sort(new ResultComparator());
//		print2(result2);
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		print(result1);
//		br.close();
//		return null;	
//	}
//	

}
