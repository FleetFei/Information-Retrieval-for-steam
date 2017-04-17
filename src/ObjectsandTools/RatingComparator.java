package ObjectsandTools;

import java.util.Comparator;

public class RatingComparator implements Comparator<relativeName> {
	
	public int compare(relativeName t1, relativeName t2){
		if (t1.rating == t2.rating) {
			return 0;
		}
		else if (t1.rating > t2.rating) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
	
