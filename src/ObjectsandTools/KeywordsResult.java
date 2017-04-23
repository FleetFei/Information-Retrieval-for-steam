package ObjectsandTools;

import java.util.ArrayList;
import java.util.List;

public class KeywordsResult {
	public ArrayList<String> Result;
	public ArrayList<relativeName> originResult;
	
	public KeywordsResult(List<relativeName> inputResult) {
		
		this.Result = new ArrayList<String>();
		this.originResult = new ArrayList<relativeName>();;
		
		for (relativeName game : inputResult) {
			this.Result.add(game.name);
		}
		
		for (relativeName game : inputResult) {
			this.originResult.add(game);
		}
		
		
	}
	
	public void print() {
		System.out.println("Results only contain Initial words: ");
		for (String st1 : Result) {
			System.out.println(st1);
		}
		System.out.println();
	}
	

}
