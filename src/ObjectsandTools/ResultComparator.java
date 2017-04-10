package ObjectsandTools;

import java.util.Comparator;

public class ResultComparator implements Comparator<relativeName> {
	
	public int compare(relativeName t1, relativeName t2){
		if (t1.score == t2.score) {
			return 0;
		}
		else if (t1.score > t2.score) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
	
