package store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.Msg;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.apache.commons.io.FileUtils;


/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class Store
{
    private String name_project;
    private int from_year;
    private int from_month;
    private int at_year;
    private int at_month;
    private File dir_store;
    private String url_base;
    private File dir_split_msgs;
    private File dir_output_msg;
        
    public Store(String name_project,int from_year,int from_month,int at_year,int at_month)
    {
        this.name_project=name_project;
        this.from_month=from_month;
        this.from_year=from_year;
        this.at_year=at_year;
        this.at_month=at_month;
        this.url_base="http://mail-archives.apache.org/mod_mbox/"+this.name_project;

        try
        {
            this.dir_output_msg=new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\output");
            this.dir_store = new File("C:\\Users\\depiano.it\\Desktop\\store_messages");
            this.dir_split_msgs=new File("C:\\Users\\depiano.it\\Desktop\\store_messages\\messages");

            if (this.dir_store.exists())
            {
                File[] files = this.dir_store.listFiles();
                for(int i=0;i<files.length;i++)
                    if(files[i].isFile())
                        files[i].delete();
            }
            else
                this.dir_store.mkdir();

            if(this.dir_split_msgs.exists())
            {
                File[] files=this.dir_split_msgs.listFiles();
                for(int i=0;i<files.length;i++)
                    if(files[i].isFile())
                        files[i].delete();
            }
            else
                this.dir_split_msgs.mkdir();

           if(this.dir_output_msg.exists())
           {
                File[] files=this.dir_output_msg.listFiles();
                for(int i=0;i<files.length;i++)
                    if(files[i].isFile())
                        files[i].delete();
           }
           else
               this.dir_output_msg.mkdir();
        }
        catch(Exception e)
        {
            e.getMessage();
        }
    }
	
    public Boolean download()
    {
        try
        {
            do
            {
                URL url;
                File destination;
                if(this.from_month>=1 && this.from_month<=9)
                {
                    url = new URL(this.url_base+"/"+Integer.toString(this.from_year)+"0"+
                        Integer.toString(this.from_month)+".mbox");

                    destination = new File(this.dir_store+"\\"+Integer.toString(this.from_year)+"0"+
                        Integer.toString(this.from_month)+".txt");

                    // Copy bytes from the URL to the destination file.
                    FileUtils.copyURLToFile(url, destination);
                }
                else
                {
                    url = new URL(this.url_base+"/"+Integer.toString(this.from_year)+
                        Integer.toString(this.from_month)+".mbox");

                    destination = new File(this.dir_store+"\\"+Integer.toString(this.from_year)+
                        Integer.toString(this.from_month)+".txt");

                    //Copy bytes from the URL to the destination file.
                    FileUtils.copyURLToFile(url, destination);
                }
                if(this.from_year==this.at_year && this.from_month==this.at_month)
                    break;
                if(this.from_month==12)
                {
                    this.from_month=1;
                    this.from_year+=1;
                }
                else
                   this.from_month+=1;		
            }
            while(this.from_year<=this.at_year || this.from_month<=this.at_month);

            this.splitMsgs();

            return true;
        }
        catch (IOException e)
        {
           e.printStackTrace();
           return false;
        }
    }
	
    public void splitMsgs()
    {
        try
        {
            File[] files = this.dir_store.listFiles();
            if(files!=null)
            {
                int count=0;
                FileReader f;
                PrintWriter out;
                BufferedReader b;
                for(int i=0;i<files.length;i++)
                {
                    if(files[i].isFile())
                    {
                        f = new FileReader(files[i]);
                        b =new BufferedReader(f);

                        out=new PrintWriter(new FileWriter(this.dir_split_msgs+"\\msg"+Integer.toString(count)+".txt",true));

                        int flag=0;

                        while(b.ready())
                        {
                            String read_file=b.readLine();

                            if(read_file.indexOf("From ")>-1 && flag!=0)
                            {
                                count+=1;
                                out.close();
                                out=new PrintWriter(new FileWriter(this.dir_split_msgs+"\\msg"+Integer.toString(count)+".txt",true));
                            }
                            out.append(read_file+"\n");
                                flag=1;
                        }
                        b.close();
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }	
    }
	
    public ArrayList<Msg> analyzer(ArrayList<String> paths_files)
    {
        ArrayList<Msg> list=new ArrayList<Msg>();
        try
        {
            FileReader f;
            BufferedReader b;
            int flag0,flag1;

            for(int i=0;i<paths_files.size();i++)
            {
                f = new FileReader(paths_files.get(i));
                b =new BufferedReader(f);

                Msg m=new Msg();
                flag0=0;
                flag1=0;
                while(b.ready())
                {
                    String read_file=b.readLine();

                    int index=-1;

                    if((index=read_file.indexOf("To: "))>-1 && index==0 && m.getTo()==null)
                    {
                        flag0=0;
                        flag1=0;
                        m.setTo(read_file.substring("To: ".length(), read_file.length()));
                    }

                    if((index=read_file.indexOf("Date: "))>-1 && index==0 && m.getDate()==null)
                    {
                        flag0=0;
                        flag1=0;
                        m.setDate(read_file.substring("Date: ".length(), read_file.length()));
                    }

                    if((index=read_file.indexOf("Subject: "))>-1 && index==0 && m.getSubject()==null)
                    {
                        flag0=0;
                        flag1=0;
                        m.setSubject(read_file.substring("Subject: ".length(), read_file.length()));
                    }

                    if((index=read_file.indexOf("Message-ID: "))>-1 && index==0 && m.getMessage_id()==null)
                    {
                        flag0=0;
                        flag1=0;
                        m.setMessage_id(read_file.substring("Message-ID: ".length(), read_file.length()));
                    }

                    if((index=read_file.indexOf("In-Reply-To: "))>-1 && index==0 && m.getInReplyTO()==null)
                    {
                        flag0=0;
                        flag1=0;
                        m.setInReplyTO(read_file.substring("In-Reply-To: ".length(), read_file.length()));
                    }

                    if((index=read_file.indexOf(":"))>-1)
                        flag0=1;
                    if(read_file.equals(""))
                        flag1=1;
                    if(flag0==1 && flag1==1)
                        m.appendToContent(read_file);
                }
                b.close();
                list.add(m);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }
        
    public void saveMsgs(ArrayList<Msg> list_msg)
    {
        try
        {
            FileOutputStream fileOut =new FileOutputStream(this.dir_output_msg+"\\output.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for(int i=0;i<list_msg.size();i++)
            {
                out.writeObject(list_msg.get(i));
            }
            out.close();
            fileOut.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
