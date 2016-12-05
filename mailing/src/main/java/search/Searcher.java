package search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 * 
 */

public class Searcher {
	
   private IndexSearcher indexSearcher;
   private QueryParser queryParser;
   private Query query;
   private Directory indexDirectory;
   
   public Searcher(String indexDirectoryPath) throws IOException
   {
      this.indexDirectory =FSDirectory.open(new File(indexDirectoryPath).toPath());
      indexSearcher = new IndexSearcher(DirectoryReader.open(indexDirectory));
      queryParser = new QueryParser(LuceneConstants.CONTENTS,new StandardAnalyzer());  
   }
   
   public TopDocs search( String searchQuery) throws IOException, ParseException
   {
      query = queryParser.parse(searchQuery);
      System.out.println(query.toString());
      return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
   }

   public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException
   {
      return indexSearcher.doc(scoreDoc.doc);	
   }
}