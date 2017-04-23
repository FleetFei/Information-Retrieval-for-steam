package IndexingLucene;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import IndexingLucene.MyIndexReader;
import ObjectsandTools.Document;
import ObjectsandTools.Path;

public class QueryRetrievalModel {

	protected MyIndexReader indexReader;

	private Directory directory;
	private DirectoryReader ireader;
	private IndexSearcher indexSearcher;

	public QueryRetrievalModel(MyIndexReader ixreader,String indexpath) {
		try {
			directory = FSDirectory.open(Paths.get(indexpath));
			ireader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(ireader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Document> retrieveQuery(String Query, int TopN) throws Exception {
		List<Document> results = new ArrayList<Document>();
		Query theQ = new QueryParser("CONTENT", new WhitespaceAnalyzer()).parse(Query);
		ScoreDoc[] scoreDoc = indexSearcher.search(theQ, TopN).scoreDocs;
		for (ScoreDoc score : scoreDoc) {
			results.add(new Document(score.doc + "", ireader.document(score.doc).get("DOCNO"), score.score));
		}
		
		return results;
		
	}

}