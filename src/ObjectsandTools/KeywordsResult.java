package ObjectsandTools;

import java.util.ArrayList;

public class KeywordsResult {
	public ArrayList<String> Result;
	
	public KeywordsResult(ArrayList<relativeName> inputResult) {
		
		this.Result = new ArrayList<String>();
		
		for (relativeName game : inputResult) {
			this.Result.add(game.name);
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
