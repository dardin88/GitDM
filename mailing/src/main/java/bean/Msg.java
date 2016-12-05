package bean;

import java.io.Serializable;

/**
 * 
 * @author Antonio De Piano
 * Email: depianoantonio@gmail.com
 * web site: depiano.it
 *
 */

public class Msg implements Serializable
{
    private String date;
    private String message_id;
    private String from; 
    private String to; 
    private String subject; 
    private String content; 
    private String inReplyTO;

    public Msg(String date,String message_id,String from,String to,String subject,String content,String inReplyTO)
    {
        this.date=date;
        this.message_id=message_id;
        this.from=from;
        this.to=to;
        this.subject=subject;
        this.content=content;
        this.inReplyTO=inReplyTO;
    }

    public Msg()
    {
        this.message_id=null;
        this.content="";
        this.subject=null;
        this.inReplyTO=null;
        this.to=null;
        this.from=null;
        this.date=null;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInReplyTO() {
        return inReplyTO;
    }

    public void setInReplyTO(String inReplyTO) {
        this.inReplyTO = inReplyTO;
    }

    @Override
    public String toString()
    {
        return ("To: "+this.to+"\ndate: "+this.date+"\nmessage id: "+this.message_id+
                        "\nsubject: "+this.subject+"\nin-Reply-TO: "+this.inReplyTO+"\ncontent: "+this.content);
    }

    public void appendToContent(String text)
    {
        this.content=this.content+text+"\n";
    }
    
}
