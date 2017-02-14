/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author depiano.it
 */
public class LuceneTesterStub extends LuceneTester
{
    public LuceneTesterStub()
    {
        
    }
    
    public void createIndex()throws IOException
   {
	   
    Indexer indexer = new Indexer("C:\\Users\\depiano.it\\Desktop\\index");
    int numIndexed;
    long startTime = System.currentTimeMillis();	
    numIndexed = indexer.createIndex("C:\\Users\\depiano.it\\Desktop\\store_messages\\messages", new TextFileFilter());
    long endTime = System.currentTimeMillis();
    indexer.close();
    //System.out.println(numIndexed+" File indexed, time taken: "+(endTime-startTime)+" ms");		
   }
}
