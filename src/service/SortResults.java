package service;

import java.util.ArrayList;

import ObjectsandTools.RatingComparator;
import ObjectsandTools.ReleasedateComparator;
import ObjectsandTools.relativeName;

public class SortResults {
	
	public ArrayList<relativeName> sortByReleasedate(ArrayList<relativeName> gameresults) {
		
		gameresults.sort(new ReleasedateComparator());
		return gameresults;
		
	}
	
	public ArrayList<relativeName> sortByRating(ArrayList<relativeName> gameresults) {
		
		gameresults.sort(new RatingComparator());
		return gameresults;
	}


}
