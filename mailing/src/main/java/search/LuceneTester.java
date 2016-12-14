package search;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import bean.Msg;
import java.io.File;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class LuceneTester {
	
   private String indexDir; 
   private String dataDir;
   private Indexer indexer;
   private Searcher searcher;

   public static ArrayList<String> test(String query) throws IOException,ParseException
   {
        ArrayList<String> files=new ArrayList<String>();
        LuceneTester tester=new LuceneTester();
        tester.indexDir="C:\\Users\\depiano.it\\Desktop\\index";
        tester.dataDir="C:\\Users\\depiano.it\\Desktop\\store_messages\\messages";
        tester.createIndex();
        files=tester.search(query);
        return files;
   }
   
   private void createIndex() throws IOException
   {
	   
    this.indexer = new Indexer(this.indexDir);
    int numIndexed;
    long startTime = System.currentTimeMillis();	
    numIndexed = this.indexer.createIndex(this.dataDir, new TextFileFilter());
    long endTime = System.currentTimeMillis();
    this.indexer.close();
    //System.out.println(numIndexed+" File indexed, time taken: "+(endTime-startTime)+" ms");		
   }

   private ArrayList<String> search(String searchQuery) throws IOException, ParseException
   {
        this.searcher = new Searcher(this.indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = this.searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();

        System.out.println(hits.totalHits +" documents found. Time :" + (endTime - startTime)+" ms");

        ArrayList<String> file_found=new ArrayList<String>();
        for(ScoreDoc scoreDoc : hits.scoreDocs)
        {
           Document doc = searcher.getDocument(scoreDoc);
           System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
           file_found.add(doc.get(LuceneConstants.FILE_PATH));
        }
        return file_found;
   }
}