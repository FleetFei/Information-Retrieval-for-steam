package Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ObjectsandTools.Path;


/*
 * This class is used for CREATE gamelist contains each game's:
 * 1.original name
 * 2.processed name (used for search)
 * 3.initial letter location (used for search)
 * 4.tags
 * 5.rating of users
 * 6.publishers
 * 
 * By Yingpei Zhao
 * 
 * 
 * 
 */

public class BuildGameList {
	
	private BufferedReader gamelistReader;
	private BufferedReader idlistReader;
	
	private BufferedReader tags4idReader;
	private BufferedReader rating4idReader;
	private BufferedReader releasedate4idReader;
	
	private BufferedReader publisherReader;
	private BufferedReader publisherpostingReader;

	private FileWriter fr;
	
//	constructor: 
//	open gamelist.txt
//	open filewriter
	public BuildGameList() throws IOException {
		gamelistReader = new BufferedReader( new FileReader(new File(Path.gamelist)));
		fr = new FileWriter(Path.SearchGamelist);
	}
	
//	build Gamelist we need
	public void buildGamelist() throws IOException {
		
		String line = "";
		String tags = "";
		String rating = "";
		String releasedate = "";
		String publisher = "";
		
//		读 gamelist 每一行
		while ((line = gamelistReader.readLine()) != null) {
			
//			如果检测到是appid，打开idlist，tags，scores去找到该id对应的tag和rating score和release date
			if (line.trim().length() > 8 && line.trim().substring(0, 8).equals("\"appid\":")) {
				
				String linetag = "";
				String lineid = "";
				String linerating = "";
				String linereleasedate = "";
				
				idlistReader = new BufferedReader( new FileReader(new File(Path.tag4id)));
				tags4idReader = new BufferedReader( new FileReader(new File(Path.gametag)));
				rating4idReader = new BufferedReader( new FileReader(new File(Path.gamescore)));
				releasedate4idReader = new BufferedReader( new FileReader(new File(Path.gamereleasedate)));
				

				int length = line.trim().length();
				String appid = line.trim().substring(9, length - 1);
				
//				分别命名lineid，linetag，linerating,linereleasedate来获得每行对应的信息
				while ((lineid = idlistReader.readLine()) != null) {
					
					linetag = tags4idReader.readLine();
					linerating = rating4idReader.readLine();
					linereleasedate = releasedate4idReader.readLine();
//					如果找到该id，将信息写入tag，rating, releasedate
					if (lineid.equals(appid)) {
						if (linetag.equals("<none>")) {
							tags = "0";
						}
						else {
							tags = linetag.replaceAll(">", " ").replaceAll("<", "").trim();
						}
						
						if(linereleasedate.equals("Jan 1 9999") || linereleasedate.length() != 10) {
							releasedate = "0";
						}
						else {
							int ldlength = linereleasedate.length();
							releasedate = linereleasedate.substring(ldlength - 4, ldlength);
						}
						
						rating = linerating;
						break;
					}
				}
				
//				每次重新开这4个文件以确保从头开始readline
				tags4idReader.close();
				idlistReader.close();
				rating4idReader.close();
				releasedate4idReader.close();
				
//			    查找appid对应的游戏的发行商
				publisherReader = new BufferedReader( new FileReader(new File(Path.publisher)));
				publisherpostingReader = new BufferedReader( new FileReader(new File(Path.publisherposting)));
				
				String linepublisherpl = "";
				String linepublisher = "";
				while ((linepublisherpl = publisherpostingReader.readLine()) != null ) {
					linepublisher = publisherReader.readLine();
					if (linepublisherpl.contains(" " + appid + " ") || linepublisherpl.contains("<" + appid + " ")) {
						publisher = linepublisher;
					}
				}
				
				publisherReader.close();
				publisherpostingReader.close();
//				添加appid
				fr.append(line + "\n");
			}
			
			
//			如果检测到是gamename，将原始名，处理过的名字，里面单词的首字母位置（$代表），该id对应tag，rating，release date publisher同时写入。
//			遵循json格式

			else if (line.trim().length() > 8 && line.trim().substring(0, 7).equals("\"name\":")){
				
				line = line.trim();
				fr.append("\t\t\t\t\"original name\":"+ line.substring(8) + "\n");
			
//				处理原始游戏名：
//				1. lowercase
//				2. 去标点（留空格）
//				3. 记录空格位置
//				4. 去空格
				String name = line.substring(8, line.length() - 1).toLowerCase().replaceAll("[':()!?.]","").replaceAll("[-&_~;\"\'//]", " ").replaceAll(" +", " ");
				String gamename = name.replace(" ", "");
				String tokenLocation = "\"$";
				
//				讲空格位置写入到tokenlocation中
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
				
//				全部添加
				fr.append("\t\t\t\t\"name\":\"" + gamename + "\"\n" + "\t\t\t\t\"tag\":\"" + tags + "\"\n" +"\t\t\t\t\"release date\":\"" + releasedate + "\"\n" +"\t\t\t\t\"publisher\":\"" + publisher + "\"\n" +  "\t\t\t\t\"rating\":\"" + rating + "\"\n" + "\t\t\t\t\"loca\":" + tokenLocation + "\n");

				
			
			}

			else {
				fr.append(line + "\n");
			}
			
		}
		
		gamelistReader.close();
		tags4idReader.close();
		idlistReader.close();
		rating4idReader.close();
		releasedate4idReader.close();

		
		publisherReader.close();
		publisherpostingReader.close();

		fr.close();
	}


}

