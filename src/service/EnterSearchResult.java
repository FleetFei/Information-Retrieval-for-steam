import java.util.ArrayList;

public class EnterSearchResult {
	
	ArrayList<String> nonInitialResult;
	ArrayList<String> initialResult;
	ArrayList<String> suggestionResult;
	
	EnterSearchResult(ArrayList<String> nonInitialResult, ArrayList<relativeName> inputInitialResult, ArrayList<relativeName> inputSuggestionResult) {
		this.nonInitialResult = nonInitialResult;
		this.initialResult = new ArrayList<String>();
		this.suggestionResult = new ArrayList<String>();
		
		for (relativeName game : inputInitialResult) {
			this.initialResult.add(game.name);
		}
		for (relativeName game : inputSuggestionResult) {
			this.suggestionResult.add(game.name);
		}
	}
	
	void print() {
		System.out.println("Results only contain Initial words: ");
		for (String st1 : initialResult) {
			System.out.println(st1);
		}
		System.out.println();
		
		
		System.out.println("Results don't contain Initial words: ");
		for (String st2 : nonInitialResult) {
			System.out.println(st2);
		}
		System.out.println();
		
		System.out.println("Suggested Results: ");
		for (String st3 : suggestionResult) {
			System.out.println(st3);
		}
		System.out.println();
	}
}
