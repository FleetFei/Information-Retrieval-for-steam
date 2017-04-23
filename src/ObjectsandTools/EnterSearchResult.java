package ObjectsandTools;
import java.util.ArrayList;
import java.util.List;

public class EnterSearchResult {
	
	public ArrayList<String> nonInitialResult;
	public ArrayList<String> initialResult;
	public ArrayList<String> suggestionResult;
	public ArrayList<String> allMatchResult;
	
	public ArrayList<relativeName> originAllMatchResult;
	public ArrayList<relativeName> originNmatchResult;
	public ArrayList<relativeName> originSuggestResult;
	public ArrayList<relativeName> originNinitalResult;

	
	public EnterSearchResult(ArrayList<relativeName> nonInitialResult, List<relativeName> nmatchInitial, List<relativeName> incorrectSpell, ArrayList<relativeName> allmatchResult) {
		
		this.nonInitialResult = new ArrayList<String>();
		this.initialResult = new ArrayList<String>();
		this.suggestionResult = new ArrayList<String>();
		this.allMatchResult = new ArrayList<String>();

		this.originAllMatchResult = allmatchResult;
		this.originNinitalResult = nonInitialResult;
		this.originSuggestResult = new ArrayList<relativeName>();
		this.originNmatchResult = new ArrayList<relativeName>();

		for (relativeName game : nmatchInitial) {
			this.initialResult.add(game.name);
		}
		for (relativeName game : incorrectSpell) {
			this.suggestionResult.add(game.name);
		}
		for (relativeName game : allmatchResult) {
			this.allMatchResult.add(game.name);
		}
		for (relativeName game : nonInitialResult) {
			this.nonInitialResult.add(game.name);
		}
		
		for (relativeName game : nmatchInitial) {
			this.originNmatchResult.add(game);
		}
		for (relativeName game : incorrectSpell) {
			this.originSuggestResult.add(game);
		}
		
		
		
	}
	
	
	public void setAllmatchResult(ArrayList<relativeName> input) {
		this.allMatchResult = new ArrayList<String>();
		for (relativeName game : input) {
			this.allMatchResult.add(game.name);
		}
		
	}
	
	public void setNmatchResult(ArrayList<relativeName> input) {
		this.initialResult = new ArrayList<String>();
		for (relativeName game : input) {
			this.initialResult.add(game.name);
		}
		
	}	
	public void setSuggestResult(ArrayList<relativeName> input) {
		this.suggestionResult = new ArrayList<String>();
		for (relativeName game : input) {
			this.suggestionResult.add(game.name);
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
