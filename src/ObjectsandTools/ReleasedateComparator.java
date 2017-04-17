package ObjectsandTools;

import java.util.Comparator;

public class ReleasedateComparator implements Comparator<relativeName> {
	
	public int compare(relativeName t1, relativeName t2){
		if (t1.releasedate == t2.releasedate) {
			return 0;
		}
		else if (t1.releasedate > t2.releasedate) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
	
