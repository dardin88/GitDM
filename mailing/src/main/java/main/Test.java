package main;

import java.util.ArrayList;



import bean.Msg;
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
        Store st=new Store("lucene-general",2014,10,2016,10);
        //Scarico i messaggi dall'archivio
        if(st.download())
        {
            ArrayList<String> files=LuceneTester.test("\"Fork\"");

            ArrayList<Msg> list_msg=st.analyzer(files);
            for(int i=0;i<list_msg.size();i++)
            {
                System.out.println(list_msg.get(i).toString());
            }
            st.saveMsgs(list_msg);
        }
    }

}
