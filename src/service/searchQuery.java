package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ObjectsandTools.EnterSearchResult;
import ObjectsandTools.Path;
import ObjectsandTools.ResultComparator;
import ObjectsandTools.TypingResult;
import ObjectsandTools.relativeName;


/*
 * This class is designed for searching Game Names
 * Main features:
 * 1. search for result game names with given query
 * 2. filter results by given tags, publishers
 *
 * About the search algorithm:
 * 1. Firstly return : correct spell with initial letter in it, match all given tags and publishers.
 * 2. Secondly return: correct spell with initial letter in it, do not match tags and publishers.
 * 3. Thirdly return: correct spell WITHOUT initial letter in it.
 * 4. Lastly return:  spell with a little mistake.
 * 
 * 
 * Attention: input game name CANNOT be null, if user don't know the name, just use search by keywords
 */



public class searchQuery {
	
	private BufferedReader gamelistReader;
	
//	constructor: open gamelist reader
	public searchQuery(String path) throws IOException {
		gamelistReader = new BufferedReader( new FileReader(new File(path)));
	}
	
//	mainly it is designed for search while typing, So only return correct spell with initial letter in it.
	public TypingResult searchTyping(String query) throws IOException {
		
		
		String line = "";
		String templine = "";
		boolean ifContains = false;
		int queryLocation = -1;
		ArrayList<relativeName> result = new ArrayList<relativeName>(); 
		
//		process query
		query = query.toLowerCase().replaceAll("[-:()!?_~; &\'\"./]", "");
		
//		读取gamelist每一行
		while ((line = gamelistReader.readLine()) != null) {
//			保存游戏原始名
			if (line.contains("\t\t\t\t\"original name\":")) {
				templine = line.trim().substring(16);
				continue;
			}
//			如果包含，看首字母位置是否符合，如果符合，将原始游戏名加入结果list
			if(line.contains("\t\t\t\t\"name\":") && line.contains(query)) {
				queryLocation = line.trim().substring(7).indexOf(query);
				ifContains = true;
				continue;
			}
			if(ifContains) {
				if (line.contains("\t\t\t\t\"loca\":")) {
					String locationline = line.trim().substring(7);
//				如果首字母位置符合，优先返回第一个首字母开头的游戏名 （将该游戏score记为1）
					if(locationline.charAt(queryLocation) == '$')
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
			
		}
//		根据之前给的score排序，排在前面的是以游戏最首开头符合query的结果
//		for example: query = "legend"
//		优先返回： "legend of hero"
//		次优先返回： "hero of legend"
		result.sort(new ResultComparator());
		
		TypingResult typeresult = new TypingResult(result);
//		此处为测试
//		typeresult.print();
		
		gamelistReader.close();
		return typeresult;
	}
	
	
//  it is designed for search after pressing "enter" and returns 4 result lists
	public EnterSearchResult searchEntering(String query, ArrayList<String> tags, String publisher, String releasedate) throws IOException {	
		for (String st : tags) {
			st.toLowerCase();
		}
		publisher = publisher.toLowerCase();
		releasedate = releasedate.toLowerCase();
		query = query.toLowerCase();
		
		
		String line = "";
		String tempappid = "";
		String templine = "";
		String temptag = "";
		String tempreleasedate = "";
		String temppublisher = "";
		int ratingscore = 0;
		int scoreToResult3 = 0;
		boolean ifContains = false;
		boolean ifaddResult3 = false;
		int queryLocation = -1;
		
		
//		返回的四种结果
//		优先级：1
		ArrayList<relativeName> allMatchResult = new ArrayList<relativeName>();
//		优先级：2
		ArrayList<relativeName> NmatchInitialResult = new ArrayList<relativeName>();
//		优先级：3
		ArrayList<relativeName> nonInitialResult = new ArrayList<relativeName>();
//		优先级：4
		ArrayList<relativeName> incorrectSpellResult = new ArrayList<relativeName>();
		
		
//		处理query
		query = query.toLowerCase().replaceAll("[-:()!?_~; &\'\"./]", "");
		
//		如果query长度大于20，将其分为4个字母的组合
//		如果query长度不大于20，将其分为3个字母的组合
//		保存在division中
//		example: legend -------> leg,ege,gen,end
		ArrayList<String> division = new ArrayList<String>();
		int n = 4;
		if (query.length() < 20) {
			n = 3;
		}
		for (int i = 0; i < query.length(); i ++) {
			if (i + n <= query.length()) {
				division.add(query.substring(i, i+n));
			}
		}
		
		
		
//		读取gamelist中的每一行
		while ((line = gamelistReader.readLine()) != null) {
//			如果是原始名，将其存为temp
			if (line.contains("\t\t\t\t\"appid\":")) {
				int length = line.trim().length();
				tempappid = line.trim().substring(9, length - 1);
				continue;
			}
			if (line.contains("\t\t\t\t\"original name\":")) {
				templine = line.trim().substring(16);
				continue;
			}
//			如果是处理后的游戏名，测试是否包括query
			if(line.contains("\t\t\t\t\"name\":")) {
//				如果包括，继续往下读
				if (line.contains(query)) {
					queryLocation = line.trim().substring(7).indexOf(query);
					ifContains = true;
					continue;
				}
//				如果不包括，测试是否是拼写错误
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
//					如果超过半数的division中的组合都出现在游戏名中，则视作可返回
					if (containsCount >= nocontainsCount) {
						scoreToResult3 = containsCount - nocontainsCount;
						ifContains = true;
						ifaddResult3 = true;
						continue;
					}					
				}	
			}
//			如果之前认定已经包含query，进一步处理以判断加入到哪个结果，并同时读取rating信息
			if(ifContains) {
//				先记录下tag信息
				if (line.contains("\t\t\t\t\"tag\":")) {
					int length = line.trim().length();
					temptag = line.trim().substring(7, length - 1);
				}
//				记录下发行日期 release date
				else if (line.contains("\t\t\t\t\"release date\":")) {
					int length = line.trim().length();
					tempreleasedate = line.trim().substring(16, length - 1);
					
				}
//				记录下出版商 publisher
				else if (line.contains("\t\t\t\t\"publisher\":")) {
					int length = line.trim().length();
					temppublisher = line.trim().substring(13, length - 1);
				}
//				记录下rating分数
				else if (line.contains("\t\t\t\t\"rating\":")) {
					int length = line.trim().length();
					String score = line.trim().substring(10, length - 1);
					if (score.equals("-1")) {
						ratingscore = 0;
					}
					else {
						ratingscore = Integer.parseInt(score);
					}
//					此处是加入的拼写错误的结果并加入rating
					if (ifaddResult3) {
						incorrectSpellResult.add(new relativeName(tempappid, scoreToResult3, templine,ratingscore, tempreleasedate, temptag));
						ifaddResult3 = false;
						ifContains = false;
						continue;
					}
				}
//				检查是否含有首字母，与searchTyping同理
				else if (line.contains("\t\t\t\t\"loca\":")) {
					String initiallocation = line.trim().substring(7);
					if(initiallocation.charAt(queryLocation) == '$')
					{
						if (queryLocation == 1) {
//							判断是否符合输入时选择的tag，如果符合，加入到allmatchresult，否则加入到nmatch
							if (ifTagMatch(tags, temptag, publisher, releasedate, temppublisher, tempreleasedate)) {
								if(!temptag.equals("none")) {
									allMatchResult.add(new relativeName(tempappid, 3, templine, ratingscore, tempreleasedate, temptag));
								}
								else {
									allMatchResult.add(new relativeName(tempappid, 1, templine, ratingscore, tempreleasedate, temptag));
								}
							}
							else {
								if(!temptag.equals("none")) {
									NmatchInitialResult.add(new relativeName(tempappid, 3, templine, ratingscore, tempreleasedate, temptag));
								}
								else {
									NmatchInitialResult.add(new relativeName(tempappid, 1, templine, ratingscore, tempreleasedate, temptag));
								}
							}
						}
						else {
//							同理
							if (ifTagMatch(tags, temptag, publisher, releasedate, temppublisher, tempreleasedate)) {
								if(!temptag.equals("none")) {
									allMatchResult.add(new relativeName(tempappid, 2, templine, ratingscore, tempreleasedate, temptag));
								}
								else {
									allMatchResult.add(new relativeName(tempappid, 0, templine, ratingscore, tempreleasedate, temptag));
								}
							}
							else {
								if(!temptag.equals("none")) {
									NmatchInitialResult.add(new relativeName(tempappid, 2, templine, ratingscore, tempreleasedate, temptag));
								}
								else {
									NmatchInitialResult.add(new relativeName(tempappid, 0, templine, ratingscore, tempreleasedate, temptag));
								}

							}
						}
					}
//					如果不包含首字母，加入noninitialresult
//					example: query:"ota"------> return: "dota"
					else {
						nonInitialResult.add(new relativeName(tempappid, 0, templine, ratingscore, tempreleasedate, temptag));
					}
					ifContains = false;
				}
			}
		}
		
//		此处排序
		NmatchInitialResult.sort(new ResultComparator());
		incorrectSpellResult.sort(new ResultComparator());
		allMatchResult.sort(new ResultComparator());
		
//		记录结果到结果object
		EnterSearchResult result = new EnterSearchResult(nonInitialResult, NmatchInitialResult, incorrectSpellResult, allMatchResult);

//		此处为测试
//		result.print();
		
		
		gamelistReader.close();
		return result;
	}
	
	
//	检测输入的tag是否符合游戏的tag:
//	首先判断发行日期和出版商是否符合
//	如果user没有选择tag，则返回所有
//	如果user选择了tag，但是游戏没有tag，优先返回allmatch
	boolean ifTagMatch(ArrayList<String> tags, String gametags, String inputpublisher, String inputreleasedate, String publisher, String releasedate) {

		if (!publisher.equals(inputpublisher)) {
			return false;
		}
		if (inputreleasedate != null && !releasedate.equals(inputreleasedate)) {
			return false;
		}
		
		if (tags == null) {
			return true;
		}
		else if (gametags.equals("none")) {
			return false ;
		}
		for (String st : tags) {
			if (!gametags.contains(st)) {
				return false;
			}
		}
		return true;
	}
 	
	
//	
//	void print(ArrayList<String> result) {
//		for (String string : result) {
//			System.out.println(string);
//		}
//	}
//	
//	void print2(ArrayList<relativeName> result) {
//		for (relativeName rn : result) {
//			System.out.println(rn.name + " " + rn.score);
//		}
//	}
//
//	


}
