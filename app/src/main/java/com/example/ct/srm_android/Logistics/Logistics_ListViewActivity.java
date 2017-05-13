package com.example.ct.srm_android.Logistics;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ct.srm_android.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import Object.*;
import Tools.JsonTools;
import Tools.LogisticsHttpRequest;

/**
 * Created by xiangyichen on 2017/4/25.
 */

public class Logistics_ListViewActivity extends Activity {
    List<LogisticsInfo> logisticsInfos;
    List<LogisticsInfo> logisticsInfo;

    private List<Map<String, Object>> testData;
    private ListView logistics_listView;
    private Button logistics_moredata_load;
    private ProgressBar logistics_moredata_pb;
    private MyAdapter adapter;

    private ImageView activity_list_view_back;
    private View moreView;

    private int pagenum = 1;

    private int MaxDateNum;// 设置一个最大的数据条数，超过即不再加载
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount; // 当前窗口可见项总数


    private String logistics_entname;
    private String logistics_status;
    private String logistics_number;
    private String logistics_sdate;
    private String logistics_edate;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_list_view);
        logisticsInfos = new ArrayList<LogisticsInfo>();
        Bundle bundle = this.getIntent().getExtras();


        logistics_entname = bundle.getString("logistics_layout_logistics_edittext_logistics_entname");
        logistics_number = bundle.getString("logistics_layout_logistics_edittext_logistics_number");
        logistics_sdate = bundle.getString("logistics_layout_logistics_edittext_logistics_sdate");
        logistics_status = bundle.getString("logistics_layout_logistics_edittext_logistics_status");
        logistics_edate = bundle.getString("logistics_layout_logistics_edittext_logistics_edate");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());


        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.logistics_moredata, null);

        logistics_moredata_load = (Button) moreView.findViewById(R.id.logistics_moredata_load);
        logistics_moredata_pb = (ProgressBar) moreView.findViewById(R.id.logistics_moredata_pb);
        activity_list_view_back = (ImageView) findViewById(R.id.activity_list_view_back);
        activity_list_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logistics_ListViewActivity.this.finish();
            }
        });

        logistics_moredata_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logistics_moredata_pb.setVisibility(View.VISIBLE);// 将进度条可见
                logistics_moredata_load.setVisibility(View.GONE);// 按钮不可见
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logistics_moredata_loadMoreDate();// 加载更多数据
                        logistics_moredata_load.setVisibility(View.VISIBLE);
                        logistics_moredata_pb.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知logistics_listView刷新数据
                    }
                }, 2000);
            }
        });
        logistics_listView = (ListView) findViewById(R.id.logistics_listview);
        // 加上底部View，注意要放在setAdapter方法前
        logistics_listView.addFooterView(moreView);
        // 绑定监听器
        /*listView.setOnScrollListener(this);*/

            LogisticsInfoThread();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    //     根据服务器发送的返回数据，生成通知的数据
//    @return 返回的是供适配器绑定的数据
//

    private void logistics_moredata_loadMoreDate() {
        pagenum = pagenum + 1;

        LogisticsInfoThread();


    }




    //这个ListViewAdapter是我们自定义适配器，它继承自BaseAdapter，
    // 实例化此适配器需要一个Context对象来获取LayoutInflater实例和一个集合对象来充当适配器的数据集；
    // 在getView方法中我们填充list_item.xml布局文件，完成列表每一项的数据显示；
    // addItem方法用来在加载数据时向数据集中添加新数据;addItem方法用来在加载数据时向数据集中添加新数据。
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext = null;
        private Map<String, Object> map;


        public MyAdapter(Context context) {
            this.map = map;
            mContext = context;
            this.inflater = LayoutInflater.from(context);
        }

        //这里的getCount方法是程序在加载显示到ui上时就要先读取的，这里获得的值决定了listview显示多少行
        @Override
        public int getCount() {
            return testData.size();
        }

        //根据ListView所在位置返回View
        @Override
        public Object getItem(int position) {
            //TODO Auto-generated method stub
            return testData.get(position);
        }

        //根据ListView位置得到数据源集合中的Id
        @Override
        public long getItemId(int position) {
            return position;
        }

        //决定listview界面的样式的
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Holder holder;          //自定义一个Holder 把一个Item中的View绑定起来
            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.logistics_listview_logistics_item, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }

            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
            holder.logistics_listview_item_logistics_date.setText((String) testData.get(position).get("logistics_listview_item_logistics_date"));
            holder.logistics_listview_item_logistics_status.setText((String) testData.get(position).get("logistics_listview_item_logistics_status"));
            holder.logistics_listview_item_logistics_number.setText((String) testData.get(position).get("logistics_listview_item_logistics_number"));

            String s = holder.logistics_listview_item_logistics_status.getText().toString();
            if (s.equals("2")) {
                holder.logistics_listview_item_logistics_status.setText("未发货");
                holder.logistics_listview_item_logistics_status.setTextColor(Color.RED);
            } else {
                holder.logistics_listview_item_logistics_status.setText("已发货");
                holder.logistics_listview_item_logistics_status.setTextColor(Color.BLACK);
            }
            view.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("logistics_entname", logisticsInfos.get(position).getLogistics_entname());
                    intent.putExtra("logistics_date", logisticsInfos.get(position).getLogistics_date());
                    intent.putExtra("logistics_status", logisticsInfos.get(position).getLogistics_status());
                    intent.putExtra("logistics_number", logisticsInfos.get(position).getLogistics_number());
                    intent.putExtra("logistics_company", logisticsInfos.get(position).getLogistics_company());
                    intent.putExtra("logistics_company_tele", logisticsInfos.get(position).getLogistics_company_tele());
                    intent.putExtra("logistics_plan_date", logisticsInfos.get(position).getLogistics_plan_date());

                    intent.setClass(Logistics_ListViewActivity.this, LogisticsDetailActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        public class Holder {

            public TextView logistics_listview_item_logistics_date;
            public TextView logistics_listview_item_logistics_number;
            public TextView logistics_listview_item_logistics_status;
            public TextView logistics_moredata_load;

            public Holder(View view) {
                logistics_listview_item_logistics_date = (TextView) view.findViewById(R.id.logistics_listview_item_logistics_date);
                logistics_listview_item_logistics_number = (TextView) view.findViewById(R.id.logistics_listview_item_logistics_number);
                logistics_listview_item_logistics_status = (TextView) view.findViewById(R.id.logistics_listview_item_logistics_status);

                logistics_moredata_load = (TextView) view.findViewById(R.id.logistics_moredata_load);


            }
        }
    }


    //子线程创建
    /**
     * Created by xiangyichen on 2017/4/25.
     * 物流查询参数
     */
    private void LogisticsInfoThread() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                String url2 ="http://211.87.227.120:3000/Logistics/Logistics_query";//供应商订单追踪接口
                Map<String, String> params = new HashMap<String, String>();

                params.put("pageSize", "10");
                params.put("currentPage", "1");
                params.put("logistics_entname", logistics_entname);
                params.put("logistics_number", logistics_number);
                params.put("logistics_status", logistics_status);
                params.put("logistics_sdate", logistics_sdate);
                params.put("logistics_edate", logistics_edate);

                try {
                    //调用写好的工具类LogisticsHttpRequest来进行GET操作
                    //调用写好的Json工具类进行数据的解析并且返回LogisticsInfo
                    String result = LogisticsHttpRequest.sendGetRequest(url2, params, "UTF-8");
                    logisticsInfo = JsonTools.getLogisticsInfo("data", result);
                    MaxDateNum = JsonTools.getTotal("total",result);
                    String s = logisticsInfo.get(0).getLogistics_number();

                    System.out.println(s);    //测试orderSelects对象组中是否有数据
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = mHandler.obtainMessage();

                    if(pagenum == 1) {
                        message.what = 1;
                        mHandler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes
                    }
                    else{
                        message.what = 2;
                        mHandler.sendMessage(message); //网络操作成功后 子线程给Handler发mes
                    }
                }catch (Exception e){

                }
            }
        }).start();
    }



//    logistics_listView=(ListView)
//
//    findViewById(R.id.logistics_listview);
//    // 加上底部View，注意要放在setAdapter方法前
//    logistics_listView.addFooterView(moreView);
//    // 绑定监听器
//       /*logistics_listView.setOnScrollListener(this);*/
//    adapter=new
//
//    MyAdapter(getApplicationContext()
//
//    );


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        //设置返回数据
        Logistics_ListViewActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        Logistics_ListViewActivity.this.finish();
    }


    /*     *
           * UI界面的Handler 可以接收子线程的消息 并对UI界面进行控制和操作
          * 子线程和UI线程的桥梁
          */
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {//此方法在ui线程运行
            System.out.println("msg0" + msg);
            switch (msg.what) {
                case 1:
                    testData = build_Logistics_Data();
                    adapter = new MyAdapter(getApplicationContext());
                    logistics_listView.setAdapter(adapter);
                    if(adapter.getCount()==MaxDateNum){
                        logistics_listView.removeFooterView(moreView);
                        Toast.makeText(Logistics_ListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                    }


                case 2:
                    int count = adapter.getCount();//监控变量

                    if (count < MaxDateNum) {
                        testData = build_Logistics_Data();
                        logistics_moredata_load.setVisibility(View.VISIBLE);
                        logistics_moredata_pb.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知listView刷新数据
                    }
                    if(adapter.getCount()==MaxDateNum){
                        logistics_listView.removeFooterView(moreView);
                        Toast.makeText(Logistics_ListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                    }

            }
        }
    };





    private List<Map<String,Object>> build_Logistics_Data(){

        for(int i=0;i<logisticsInfo.size();i++){
            logisticsInfos.add(logisticsInfo.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<logisticsInfos.size();i++){
           map = new HashMap<String, Object>();
            map.put("logistics_entname", logisticsInfos.get(i).getLogistics_entname());
            map.put("logistics_date", logisticsInfos.get(i).getLogistics_date());
            map.put("logistics_status", logisticsInfos.get(i).getLogistics_status());
            map.put("logistics_number", logisticsInfos.get(i).getLogistics_number());


            data.add(map);

        }


        return data;
    }
}