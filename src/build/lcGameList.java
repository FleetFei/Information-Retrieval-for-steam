package build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class lcGameList {
	
	private BufferedReader br;
	private FileWriter fr;
	
	public lcGameList(String brpath, String frpath) throws IOException {
		br = new BufferedReader( new FileReader(new File(brpath)));
		fr = new FileWriter(frpath);
	}
	
	public void search() throws IOException {
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.trim().length() > 8 && line.trim().substring(0, 7).equals("\"name\":"))
			{
				fr.append("\t\t\t\t\"original name\":"+ line.trim().substring(8) + "\n");
				String name = line.trim().substring(8, line.trim().length() - 1).toLowerCase().replaceAll("[':()!?.]","").replaceAll("[-&_~;\"\'//]", " ").replaceAll(" +", " ");
				String gamename = name.replace(" ", "");
				String tokenLocation = "\"$";
				
				for (int i = 2; i < name.length(); i++) {
					if (name.charAt(i) == ' ') {
						name = name.replaceFirst(" ", "");
						tokenLocation += '$';
					}
					else {
						tokenLocation += ' ';
					}
				}
				
				tokenLocation += "\"";
				
				fr.append("\t\t\t\t\"name\":\"" + gamename + "\"\n" + "\t\t\t\t\"loca\":" + tokenLocation + "\n");

			}
			else {
				fr.append(line + "\n");
			}
		}
		
		br.close();
		fr.close();
	}

}
