package ObjectsandTools;
import java.util.ArrayList;

public class EnterSearchResult {
	
	public ArrayList<String> nonInitialResult;
	public ArrayList<String> initialResult;
	public ArrayList<String> suggestionResult;
	public ArrayList<String> allMatchResult;
	
	public EnterSearchResult(ArrayList<String> nonInitialResult, ArrayList<relativeName> inputInitialResult, ArrayList<relativeName> inputSuggestionResult, ArrayList<relativeName> allmatchResult) {
		
		this.nonInitialResult = nonInitialResult;
		this.initialResult = new ArrayList<String>();
		this.suggestionResult = new ArrayList<String>();
		this.allMatchResult = new ArrayList<String>();

		
		for (relativeName game : inputInitialResult) {
			this.initialResult.add(game.name);
		}
		for (relativeName game : inputSuggestionResult) {
			this.suggestionResult.add(game.name);
		}
		for (relativeName game : allmatchResult) {
			this.allMatchResult.add(game.name);
		}
		
		
	}
	
	public void print() {
		
		System.out.println("Results all match: ");
		
		for (String st : allMatchResult) {
			System.out.println(st);
		}
		System.out.println();
		
		
		System.out.println("Results only contain Initial words: ");
		for (String st1 : initialResult) {
			System.out.println(st1);
		}
		System.out.println();
		
//		
//		System.out.println("Results don't contain Initial words: ");
//		for (String st2 : nonInitialResult) {
//			System.out.println(st2);
//		}
//		System.out.println();
//		
//		System.out.println("Suggested Results: ");
//		for (String st3 : suggestionResult) {
//			System.out.println(st3);
//		}
//		System.out.println();
	}
}
