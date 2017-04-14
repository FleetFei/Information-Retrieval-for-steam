package Build;

import java.util.Map;

import IndexingLucene.MyIndexWriter;
import IndexingLucene.PreProcessedCorpusReader;


/*
 * This class is designed to write lucene index
 */
public class WriteLuceneIndex {
	
	public void writeIndex() throws Exception {
		// Initiate pre-processed collection file reader
		PreProcessedCorpusReader corpus=new PreProcessedCorpusReader();
		
		// initiate the output object
		MyIndexWriter output=new MyIndexWriter();
		
		// initiate a doc object, which can hold document number and document content of a document
		Map<String, String> doc = null;

		int count=0;
		// build index of corpus document by document
		while ((doc = corpus.nextDocument()) != null) {
			// load document number and content of the document
			String docno = doc.keySet().iterator().next();
			String content = doc.get(docno);
			// index this document
			output.index(docno, content); 
			
			count++;
			if(count%10000==0)
				System.out.println("finish "+count+" docs");
		}
		output.close();
	}

}
