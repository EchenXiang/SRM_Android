package Object;

import java.io.Serializable;


public class Notice implements Serializable {
    private  String id;
    private String receiver_ent;
    private String sender_ent;
    private String date;
    private String content;
    private String title;
    private String status;
    private String role;
    public Notice() {
        // TODO Auto-generated constructor stub
    }
    public String getid() {return id;}
    public void setid(String id) {this.id = id;}

    public String getreceiver_ent() {return receiver_ent;}
    public void setreceiver_ent(String receiver_ent) {this.receiver_ent = receiver_ent;}

    public String getsender_ent() {return sender_ent;}
    public void setsender_ent(String sender_ent) {this.sender_ent = sender_ent;}

    public String getdate() {return date;}
    public void setdate(String date) {
        this.date= date;
    }

    public String getcontent() {
        return content;
    }
    public void setcontent(String content) {this.content=content;}

    public String gettitle() {
        return title;
    }
    public void settitle(String title) {
        this.title=title;
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) { this.status = status;}

    public String getrole() {
        return role;
    }
    public void setrole(String role) {
        this.role =role;
    }

    public Notice(String id, String receiver_ent, String sender_ent, String date, String content, String title
            , String status, String role) {
        super();
        this.id = id;
        this.receiver_ent = receiver_ent;
        this.sender_ent =sender_ent;
    }

}

