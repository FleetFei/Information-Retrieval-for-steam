package service;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ObjectsandTools.relativeName;

// 
public class findResultDetails {
	
	BufferedReader br;
	
	public String getTags(relativeName game) {
		return game.tags;
	}
	
	public String getDescription(String desPath, relativeName game) throws IOException {
		boolean ifContains = false;
		br = new BufferedReader(new FileReader(desPath));
		
		
		String line = "";
		while ((line = br.readLine()) != null ) {
			if(line.contains(game.appid)) {
				ifContains = true;
			}
			else if (ifContains) {
				return line;
			}
		}
		
		return "no description found";
	}
	
}
