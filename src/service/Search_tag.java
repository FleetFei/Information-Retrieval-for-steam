package service;

import java.util.ArrayList;
import java.io.*;

public class Search_tag {
	String path_tag = "C:\\ir_data\\result\\tag4pst.txt";//�޸ĳ����·����
	String path_tag_classify = "C:\\ir_data\\result\\tag_posting.txt";//�޸ĳ����·��
	ArrayList<String> result;
	BufferedReader br1;
	BufferedReader br2;
	int count = 0;
	public Search_tag() throws IOException{
		br1 = new BufferedReader(new FileReader(path_tag));
		br2 = new BufferedReader(new FileReader(path_tag_classify));
		result = new ArrayList<>();
	}
	public ArrayList<String> search(String s) throws IOException{
		String temp1 = "";
		String temp2 = "";
		System.out.println(temp1);
		while(true){
			temp1 = br1.readLine();
			count++;
			if(temp1.equalsIgnoreCase(s))
				break;
		}
		while(count>0){
			temp2 = br2.readLine();
			count--;
		}
		temp2 = temp2.replaceAll("[\\pP\\p{Punct}]", " ").trim();
		String[] temp = temp2.split(" ");
		for(int i =0;i<temp.length;i++){
			result.add(temp[i]);
		}
		return result;
	}

}
