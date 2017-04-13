package PreprocessTools;

public class WordNormalizer {

	public String lowercase(String chars ) {
		//transform the uppercase characters in the word to lowercase
		return chars.toLowerCase();
		
	}
	
	public String stem(String chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		
		Stemmer s = new Stemmer();
		for (int i = 0; i < chars.length(); i++)
		{
			s.add(chars.charAt(i));
		}
		s.stem();
		
		return s.toString();
	}
	
}