package search;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import bean.Msg;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class LuceneTester {
	
   public String indexDir; 
   public String dataDir;
   public Indexer indexer;
   public Searcher searcher;

   public static ArrayList<String> test(String query)
   {
        ArrayList<String> files=new ArrayList<String>();
        try
        {
            LuceneTester tester=new LuceneTester();
            tester.indexDir="C:\\Users\\depiano.it\\Desktop\\index";
            tester.dataDir="C:\\Users\\depiano.it\\Desktop\\store_messages\\messages";
            tester.createIndex();
            files=tester.search(query);
        }
        catch (IOException | ParseException e)
        {
            e.printStackTrace();
        }
        return files;
   }
   
   private void createIndex() throws IOException
   {
	   
    indexer = new Indexer(indexDir);
    int numIndexed;
    long startTime = System.currentTimeMillis();	
    numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
    long endTime = System.currentTimeMillis();
    indexer.close();
    //System.out.println(numIndexed+" File indexed, time taken: "+(endTime-startTime)+" ms");		
   }

   private ArrayList<String> search(String searchQuery) throws IOException, ParseException
   {
        searcher = new Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
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