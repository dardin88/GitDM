package main;

import java.util.ArrayList;



import bean.Msg;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import search.LuceneTester;
import store.Store;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class Test
{

    public static void main(String[] args)
    {
        try
        {
            Store st=new Store("lucene-general",2010,10,2016,10);
            //Scarico i messaggi dall'archivio
            if(st.download())
            {
                ArrayList<String> files=LuceneTester.test("Fork");
                ArrayList<Msg> list_msg=st.analyzer(files);
                for(int i=0;i<list_msg.size();i++)
                {
                    System.out.println(list_msg.get(i).toString());
                }
                st.saveMsgs(list_msg);
            }
            else
                System.out.println("\nError: invalid project name!\n");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
}
