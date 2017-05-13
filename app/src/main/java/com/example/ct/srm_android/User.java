package com.example.ct.srm_android;

import android.app.Application;

/**
 * Created by ct on 2016/7/18.
 */
public class User extends Application {
    private String staff_role ;
    private String userID ;
    private String enterpriseName ;
    private String enterpriseId ;
    private String current_user_role ;
    private String SupplyRole ;
    private String enterpriserMCNow ;
    private String userName ;
    private String userna;


    public String getstaff_role(){
        return staff_role;
    }
    public void setstaff_role(String s){
        this.staff_role = s;
    }
    public String getuserID(){
        return userID;
    }
    public void setuserID(String s){
        this.userID = s;
    }
    public String getenterpriseName(){
        return enterpriseName;
    }
    public void setenterpriseName(String s){
        this.enterpriseName = s;
    }
    public String getenterpriseId(){
        return enterpriseId;
    }
    public void setenterpriseId(String s){
        this.enterpriseId = s;
    }
    public String getcurrent_user_role(){
        return current_user_role;
    }
    public void setcurrent_user_role(String s){
        this.current_user_role = s;
    }
    public String getSupplyRole(){
        return SupplyRole;
    }
    public void setSupplyRole(String s){
        this.SupplyRole = s;
    }
    public String getenterpriserMCNow(){
        return enterpriserMCNow;
    }
    public void setenterpriserMCNow(String s){
        this.enterpriserMCNow = s;
    }
    public String getuserName(){
        return userName;
    }
    public void setuserName(String s){
        this.userName = s;
    }

    public String getuserna(){
        return userna;
    }
    public void setuserna(String s){
        this.userna = s;
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        setstaff_role(""); //初始化全局变量
        setuserID("");
        setenterpriseName("");
        setenterpriseId("");
        setcurrent_user_role("");
        setSupplyRole("");
        setenterpriserMCNow("");
        setuserName("");
        setuserna("");

    }
}
