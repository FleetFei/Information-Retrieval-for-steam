package ObjectsandTools;

import java.util.ArrayList;

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
