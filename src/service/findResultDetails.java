package service;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ObjectsandTools.relativeName;

// 
public class findResultDetails {
	
	BufferedReader br;
	
	public String getTags(relativeName game) {
		return game.tags;
	}
	
//	public String getDescription(String desPath, relativeName game) throws IOException {
//		boolean ifContains = false;
//		br = new BufferedReader(new FileReader(desPath));
//		
//		
//		String line = "";
//		while ((line = br.readLine()) != null ) {
//			if(line.equals(game.appid)) {
//				ifContains = true;
//			}
//			else if (ifContains) {
//				return line;
//			}
//		}
//		
//		return "no description found";
//	}
	
	public HashMap<String, String> getDescription(String desPath, ArrayList<relativeName> gamelist) throws IOException {
		boolean ifContains = false;
		HashMap<String, String> result = new HashMap<String, String>();
		HashSet<String> gameset = new HashSet<String>();
		
		for (relativeName game : gamelist) {
			gameset.add(game.appid);
		}
		
		br = new BufferedReader(new FileReader(desPath));
		
		String tempappid = "";
		String line = "";
		while ((line = br.readLine()) != null ) {
			if(gameset.contains(line)) {
				tempappid = line;
				ifContains = true;
			}
			else if (ifContains) {
				result.put(tempappid, line);
				ifContains = false;
			}
		}
		
		for (relativeName game : gamelist) {
			if (!result.containsKey(game.appid)) {
				result.put(game.appid, "no description found");
			}
		}
		
		return result;
	}
	
}
