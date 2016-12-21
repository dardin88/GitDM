package store;

import Exceptions.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import bean.Msg;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
        if(name_project==null)throw new ProjectNameNullException("Error: Project name null!");
        if(from_year<2001 || from_year>(new GregorianCalendar()).get(Calendar.YEAR))throw new InvalidYearException("Error: Invalid input from-year!\nValid input: 2001<=From-Year<=Current-Year");
        if(at_year<2001 || at_year>(new GregorianCalendar()).get(Calendar.YEAR))throw new InvalidYearException("Error: Invalid input at-year\nValid input: 2001<=At-Year<=Current-Year AND At-Year>=From-Year");
        if(at_year<from_year)throw new InvalidAtYearException("Error: Invalid time range!\nNecessarily at-year>=from-year");
        if(from_month<1 || from_month>12)throw new InvalidMonthException("Error: Invalid input from-month!\n Valid input: 1<=From-month<=12");
        if(at_month<1 || at_month>12)throw new InvalidMonthException("Error: Invalid input at-month!\nValid input: 1<=At-month<=12");
        if(from_year==at_year && from_month>at_month)throw new InvalidMonthException("Error: Invalid time range!\nValid input: from-year=at-year AND from-month>at-month!");
        if(name_project.length()<2)throw new InvalidProjectNameException("Error: the project-name must have a minimum of two characters!");
        if(name_project.length()>25)throw new InvalidProjectNameException("Error: the project-name must have a maximum of twenty five characters!");
        
        if(from_year==(new GregorianCalendar()).get(Calendar.YEAR))
            if(from_month>((new GregorianCalendar()).get(Calendar.MONTH)+1))throw new InvalidMonthException("Error: Invalid input from-month!\n");
            
        if(at_year==(new GregorianCalendar()).get(Calendar.YEAR))
            if(at_month>((new GregorianCalendar()).get(Calendar.MONTH)+1))throw new InvalidMonthException("Error: Invalid input at-month!\n");
        
        this.name_project=name_project;
        this.from_month=from_month;
        this.from_year=from_year;
        this.at_year=at_year;
        this.at_month=at_month;
        this.url_base="http://mail-archives.apache.org/mod_mbox/"+this.name_project;
       
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
	
    public Boolean download()
    {
        try
        {
            System.out.println("\nPlease wait...\nDownloads messages...\n");;
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
           return false;
        }
    }
	
    public void splitMsgs()throws IOException
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
	
    public ArrayList<Msg> analyzer(ArrayList<String> paths_files)throws IOException
    {
        if(paths_files==null)throw new PathFileNullException("Error: Path File Null!");
        if(paths_files.size()==0)throw new PathsFilesZeroException("Warning: 0 path file found!");
        ArrayList<Msg> list=new ArrayList<Msg>();
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
        return list;
    }
        
    public void saveMsgs(ArrayList<Msg> list_msg)throws IOException
    {
        if(list_msg==null)throw new SaveMsgNullException("Error: msg object null!");
        if(list_msg.isEmpty())throw new InputEmptySaveMsgException("Error: msg object empty!");
        FileOutputStream fileOut =new FileOutputStream(this.dir_output_msg+"\\output.dat");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        for(int i=0;i<list_msg.size();i++)
        {
            out.writeObject(list_msg.get(i));
        }
        out.close();
        fileOut.close();
    }
    
    public Path getDirOutput()
    {
        return this.dir_output_msg.toPath();
    }
    
    public Path getDirSplitMsg()
    {
        return this.dir_split_msgs.toPath();
    }
    
    public Path getDirStore()
    {
        return this.dir_store.toPath();
    }
}
