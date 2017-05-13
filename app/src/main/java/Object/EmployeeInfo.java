package Object;

/**
 * Created by ct on 2016/7/18.
 */
public class EmployeeInfo {
    private String createTime;
    private String phone;
    private String address;
    private String status;
    private String email;
    private String name;
    private String lastModifyTime;

    public String getcreateTime(){
        return createTime;
    }
    public void setcreateTime(String s){
        this.createTime= s;
    }
    public String getphone(){
        return phone;
    }
    public void setphone(String s){
        this.phone = s;
    }

    public String getaddress(){
        return address;
    }
    public void setaddress(String s){
        this.address = s;
    }
    public String getstatus(){
        return status;
    }
    public void setstatus(String s){
        this.status = s;
    }
    public String getemail(){
        return email;
    }
    public void setemail(String s){
        this.email = s;
    }
    public String getname(){
        return name;
    }
    public void setname(String s){
        this.name = s;
    }
    public String getlastModifyTime(){
        return lastModifyTime;
    }
    public void setlastModifyTime(String s){
        this.lastModifyTime = s;
    }
}
