package search;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 * 
 */

public class Indexer
{

    private IndexWriter writer;
    private Directory indexDirectory;

    public Indexer(String indexDirectoryPath) throws IOException
    {
       //this directory will contain the indexes
       File f=new File(indexDirectoryPath);
       if (f.exists())
       {
            File[] files = f.listFiles();
            for(int i=0;i<files.length;i++)
                if(files[i].isFile())
                    files[i].delete();
        }
       this.indexDirectory = FSDirectory.open(new File(indexDirectoryPath).toPath());

        //create the indexer
        this.writer = new IndexWriter(indexDirectory,new IndexWriterConfig(new StandardAnalyzer()));
        this.writer.deleteAll();
    }

    public void close() throws CorruptIndexException, IOException
    {
       this.writer.close();
    }

    private Document getDocument(File file) throws IOException
    {
       Document document = new Document();
       //index file contents
       TextField contentField = new TextField(LuceneConstants.CONTENTS,new FileReader(file));

       //index file name
       Field fileNameField = new StringField(LuceneConstants.FILE_NAME,file.getName(),Field.Store.YES);
       //index file path
       Field filePathField = new StringField(LuceneConstants.FILE_PATH,file.getCanonicalPath(),Field.Store.YES);

       document.add(contentField);
       document.add(fileNameField);
       document.add(filePathField);
       return document;
    }   

    private void indexFile(File file) throws IOException
    {
       //System.out.println("Indexing "+file.getCanonicalPath());
       Document document = getDocument(file);
       this.writer.addDocument(document);
    }

    public int createIndex(String dataDirPath, FileFilter filter) throws IOException
    {
       //get all files in the data directory
       File[] files = new File(dataDirPath).listFiles();
       for (File file : files)
       {
            if(!file.isDirectory() && !file.isHidden() && file.exists() && file.canRead() && filter.accept(file))
            {
                indexFile(file);
            }
       }
       return this.writer.numDocs();
    }
}