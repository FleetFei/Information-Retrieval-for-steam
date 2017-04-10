package service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class findGamelist {
	
	private BufferedReader br;
	private FileWriter fr;
	
	public findGamelist(String brpath, String frpath) throws IOException {
		br = new BufferedReader( new FileReader(new File(brpath)));
		fr = new FileWriter(frpath);
	}
	
	public void search(String query) throws IOException {
		String line = "";
		String id = "";
//		ArrayList<String> result = new ArrayList<String>();  
//		fr.append("{\n");
//		fr.append("\t\"resultlist\": {\n");
//		fr.append("\t\t\"apps\": [\n");
		while ((line = br.readLine()) != null) {
			if(line.contains(query)) {
//				fr.append("\t\t\t{\n");
//				fr.append(id + "\n");
				fr.append(line.trim().substring(9, line.trim().length() - 1) + "\n");
//				fr.append("\t\t\t},\n");
//				result.add(line.trim().substring(9, 10));
			}
//			id = line;
		}
		
//		System.out.println(result.toString());
//		fr.append("\t\t]\n");
//		fr.append("\t}\n");
//		fr.append("}");
		br.close();
//		return result;
		fr.close();
	}


}
