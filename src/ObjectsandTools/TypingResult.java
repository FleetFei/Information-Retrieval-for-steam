package ObjectsandTools;

import java.util.ArrayList;

public class TypingResult {
	public ArrayList<String> initialResult;
	public ArrayList<relativeName> originResult;

	public TypingResult(ArrayList<relativeName> inputInitialResult) {
		
		this.initialResult = new ArrayList<String>();
		this.originResult = inputInitialResult;
		
		for (relativeName game : inputInitialResult) {
			this.initialResult.add(game.name);
		}
	}
	
	public void print() {
		System.out.println("Results only contain Initial words: ");
		for (String st1 : initialResult) {
			System.out.println(st1);
		}
		System.out.println();
	}
	

}
