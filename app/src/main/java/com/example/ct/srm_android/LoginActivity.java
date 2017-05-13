package com.example.ct.srm_android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tools.JsonTools;
import Tools.LuoHttpRequest;
import Object.*;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private EditText user_name;
    private EditText user_password;
    private Button LoginButton;
    private String userName;
    private String userPassword;
    private List<PersonalInfo> personalInfos;
    private EmployeeInfo employeeInfo;
    private ProgressDialog progressDialog = null;
    private String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (User)getApplication();

        user_name = (EditText)findViewById(R.id.login_username);
        user_password = (EditText)findViewById(R.id.login_password);
        LoginButton = (Button)findViewById(R.id.user_login_button) ;
        getAccount();
        if(user.getenterpriseId().length()>0){
            Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
            startActivity(intent);
            finish();
        }



            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = user_name.getText().toString();
                    userPassword = user_password.getText().toString();
                    if(userName==null || userName.trim().equals("")) {
                        user_name.setError("内容不能为空！");
                        return;
                    }
                    if(userPassword==null || userPassword.trim().equals("")) {
                        user_password.setError("内容不能为空！");
                        return;
                    }
                    progressDialog = ProgressDialog.show(LoginActivity.this, "", "正在登录,请稍候！");
                    LoginSignThread();
                }
            });



    }

    private void saveAccount() {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("login_info",
                MODE_PRIVATE);
        // 获取editor
        SharedPreferences.Editor editor = preference.edit();
        // 存入数据
        editor.putString("staff_role", user.getstaff_role());
        editor.putString("userID",user.getuserID());
        editor.putString("enterpriseName", user.getenterpriseName());
        editor.putString("enterpriseId", user.getenterpriseId());
        editor.putString("current_user_role", user.getcurrent_user_role());
        editor.putString("SupplyRole", user.getSupplyRole());
        editor.putString("enterpriserMCNow", user.getenterpriserMCNow());
        editor.putString("userName", user.getuserName());
        editor.putString("userna", user.getuserna());
        // 提交存入文件中
        editor.commit();
    }

    private void getAccount() {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("login_info",
                MODE_PRIVATE);
        // 获取存在SharedPreference中的用户名
        user.setstaff_role(preference.getString("staff_role", ""));
        user.setuserID(preference.getString("userID", ""));
        user.setenterpriseName(preference.getString("enterpriseName", ""));
        user.setenterpriseId(preference.getString("enterpriseId", ""));
        user.setcurrent_user_role(preference.getString("current_user_role", ""));
        user.setSupplyRole(preference.getString("SupplyRole", ""));
        user.setenterpriserMCNow(preference.getString("enterpriserMCNow", ""));
        user.setuserName(preference.getString("userName", ""));
        user.setuserna(preference.getString("userna", ""));
    }

    private void LoginSignThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/sign/sign";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("username", userName);
                params.put("password", userPassword);
                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    String staff_role = JsonTools.getStaff_role("data",result);
                    String userna = JsonTools.getuserna("data",result);

                    user.setstaff_role(JsonTools.getStaff_role("data",result));
                    user.setuserna(JsonTools.getuserna("data",result));
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();
                    if(staff_role.equals("")){
                        message.what=7;
                    }else {

                        message.what = 1;
                    }

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }


    private void getPersonalInfoThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/login/getPersonalInfo";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("userna", user.getuserna());

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    user.setuserID(JsonTools.getneoid(result));
                    personalInfos = JsonTools.getPersonalInfo("role",result);
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();

                    message.what = 2;

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }

    private void getAssignEnterpriseThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/login/getAssignEnterprise";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("id", user.getuserID());
                params.put("ak","123");

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    user.setenterpriseId(JsonTools.getenterpriseId("enterprise",result));
                    user.setenterpriseName(JsonTools.getenterpriseName("enterprise",result));
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();

                    message.what = 3;

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }


    private void getSupplyToEnterpriseThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/login/getSupplyToEnterprise";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("enterpriseId", user.getenterpriseId());
                params.put("enterpriseMC",user.getenterpriseName());

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    String ISok ;
                    ISok = JsonTools.getmessage(result);
                    if(ISok.equals("ok")){
                        user.setSupplyRole("供应商");
                        user.setenterpriserMCNow(JsonTools.getenterpriseMC("data","enterprise",result));
                    }
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();

                    message.what = 4;

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }

    private void getSupplyFromEnterpriseThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/login/getSupplyFromEnterprise";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("enterpriseId", user.getenterpriseId());
                params.put("enterpriseMC",user.getenterpriseName());

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    String ISok ;
                    ISok = JsonTools.getmessage(result);
                    if(ISok.equals("ok")){
                        user.setSupplyRole("主机厂");

                    }
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();

                    message.what = 5;

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }

    private void getEmployeeInfoByIdThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/login/getEmployeeInfoById";//供应商订单接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单查询参数
                 */
                params.put("id", user.getuserID());
                params.put("ak","123");

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    employeeInfo = JsonTools.getEmployeeInfo("employee",result);
                    user.setuserName(employeeInfo.getname());
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();

                    message.what = 6;

                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){
                    Message message = handler.obtainMessage();
                    message.what=8;
                    handler.sendMessage(message);
                }


            }
        }).start();
    }


    private Handler handler = new Handler() {

        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:
                    getPersonalInfoThread();

                    break;

                case 2:
                    getAssignEnterpriseThread();
                    break;
                case 3:
                    getSupplyToEnterpriseThread();
                    break;
                case 4:
                    getSupplyFromEnterpriseThread();
                    break;
                case 5:
                    getEmployeeInfoByIdThread();
                    break;
                case 6:
                    saveAccount();
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this,MainMenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 7:
                    progressDialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("用户名或密码错误！请重新输入")//设置显示的内容

                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub



                                }

                            }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮



                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件

                            // TODO Auto-generated method stub



                        }

                    }).show();//在按键响应事件中显示此对话框

                    break;

                case 8:
                    progressDialog.dismiss();
                    new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")//设置对话框标题

                            .setMessage("网络错误!请检查您的网络连接")//设置显示的内容

                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮



                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub



                                }

                            }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮



                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件

                            // TODO Auto-generated method stub



                        }

                    }).show();//在按键响应事件中显示此对话框

                    break;


            }
        }
    };


}
