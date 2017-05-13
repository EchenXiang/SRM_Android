package com.example.ct.srm_android.Logistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.text.TextPaint;


import com.example.ct.srm_android.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Tools.JsonTools;
import Tools.LogisticsHttpRequest;
import Object.*;


/**
 * Created by xiangyichen on 2017/4/25.
 */

public class Delivery_ListViewActivity extends Activity {
    List<DeliveryInfo> deliveryInfos;
    List<DeliveryInfo> deliveryInfo;

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


    private String sgoods_entname;
    private String sgoods_snumber;
    private String sgoods_sdate;
    private String sgoods_edate;
    private String sgoods_status;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list_view);

        deliveryInfos = new ArrayList<DeliveryInfo>();
        Bundle bundle = this.getIntent().getExtras();

        sgoods_entname = bundle.getString("logistics_layout_delivery_edittext_sgoods_entname");
        sgoods_snumber = bundle.getString("logistics_layout_delivery_edittext_sgoods_snumber");
        sgoods_status = bundle.getString("logistics_layout_delivery_edittext_sgoods_status");
        sgoods_sdate = bundle.getString("logistics_layout_delivery_edittext_sgoods_sdate");
        sgoods_edate = bundle.getString("logistics_layout_delivery_edittext_sgoods_edate");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());


        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.logistics_moredata, null);

        logistics_moredata_load = (Button) moreView.findViewById(R.id.logistics_moredata_load);
        logistics_moredata_pb = (ProgressBar) moreView.findViewById(R.id.logistics_moredata_pb);
        activity_list_view_back = (ImageView) findViewById(R.id.activity_list_view_back);
        activity_list_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delivery_ListViewActivity.this.finish();
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
                        adapter.notifyDataSetChanged();// 通知delivery_listView刷新数据
                    }
                }, 2000);
            }
        });
        logistics_listView = (ListView) findViewById(R.id.delivery_listview);
        // 加上底部View，注意要放在setAdapter方法前
        logistics_listView.addFooterView(moreView);

        DeliveryInfoThread();
    }

    //     根据服务器发送的返回数据，生成通知的数据
//    @return 返回的是供适配器绑定的数据
//


    private void logistics_moredata_loadMoreDate() {
        pagenum = pagenum + 1;
        DeliveryInfoThread();
    }




    //MyAdapter，它继承自BaseAdapter，
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
                LayoutInflater inflater = LayoutInflater.from(mContext);//把layout转换成View【LayoutInflater的作用】

                view = inflater.inflate(R.layout.logistics_listview_delivery_item, null);//通过上面layout得到的view来获取里面的具体控件

                holder = new Holder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }

            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
            holder.logistics_listview_delivery_item_sgoods_snumber.setText((String) testData.get(position).get("sgoods_snumber"));
            holder.logistics_listview_delivery_item_sgoods_status.setText((String) testData.get(position).get("sgoods_status"));
            holder.logistics_listview_delivery_item_sgoods_date.setText((String) testData.get(position).get("sgoods_date"));

            String s = holder.logistics_listview_delivery_item_sgoods_status.getText().toString();
            if (s.equals("2")) {
                holder.logistics_listview_delivery_item_sgoods_status.setText("未发货");
                holder.logistics_listview_delivery_item_sgoods_status.setTextColor(Color.RED);
            } else {
                holder.logistics_listview_delivery_item_sgoods_status.setText("已发货");
                holder.logistics_listview_delivery_item_sgoods_status.setTextColor(Color.BLACK);
            }
            view.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("sgoods_entname", deliveryInfos.get(position).getEntname());
                    intent.putExtra("sgoods_number", deliveryInfos.get(position).getNumber());
                    intent.putExtra("sgoods_status", deliveryInfos.get(position).getStatus());
                    intent.putExtra("sgoods_sdate", deliveryInfos.get(position).getDate());

                    intent.putExtra("sgoods_logistics_number", deliveryInfos.get(position).getLogistics_number());
                    intent.putExtra("sgoods_plan_date", deliveryInfos.get(position).getPlan_date());
                    intent.putExtra("sgoods_sender", deliveryInfos.get(position).getSender());
                    intent.putExtra("sgoods_receiver", deliveryInfos.get(position).getReceiver());
                    intent.putExtra("sgoods_re_tele", deliveryInfos.get(position).getRe_tele());
                    intent.putExtra("sgoods_re_addr", deliveryInfos.get(position).getRe_addr());



                    intent.setClass(Delivery_ListViewActivity.this, DeliveryDetailActivity.class);
                    startActivity(intent);
                }
            });
            return view;
        }

        public class Holder {
            public TextView logistics_listview_delivery_item_sgoods_date;
            public TextView logistics_listview_delivery_item_sgoods_snumber;
            public TextView logistics_listview_delivery_item_sgoods_status;
            public TextView logistics_moredata_load;

            public Holder(View view) {
                logistics_listview_delivery_item_sgoods_date = (TextView) view.findViewById(R.id.logistics_listview_delivery_item_sgoods_date);
                logistics_listview_delivery_item_sgoods_snumber = (TextView) view.findViewById(R.id.logistics_listview_delivery_item_sgoods_snumber);
                logistics_listview_delivery_item_sgoods_status = (TextView) view.findViewById(R.id.logistics_listview_delivery_item_sgoods_status);
                logistics_moredata_load = (TextView) view.findViewById(R.id.logistics_moredata_load);
//                TextPaint textPaint = logistics_listview_delivery_item_sgoods_snumber.getPaint();
//                textPaint.setFakeBoldText(true);

            }
        }
    }


    //子线程创建
    private void DeliveryInfoThread() {
        new Thread(new Runnable() {
            @Override
            public void run(){
                String url1 ="http://211.87.227.120:3000/Delivery/deliveryquery";//供应商订单追踪接口
                Map<String, String> params = new HashMap<String, String>();
                /**
                 * Created by xiangyichen on 2017/4/25.
                 * 供应商库存查询参数
                 */

                params.put("pageSize", "10");
                params.put("currentPage", "1");
                params.put("sgoods_entname", sgoods_entname);
                params.put("sgoods_snumber", sgoods_snumber);
                params.put("sgoods_status", sgoods_status);
                params.put("sgoods_sdate", sgoods_sdate);
                params.put("sgoods_edate", sgoods_edate);

                try {
                    //调用写好的工具类LogisticsHttpRequest来进行GET操作

                    /*stocks = JsonTools.getStocks("data",result);
                    String s = stocks.get(0).getcharge_number();*/
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects
                    String result = LogisticsHttpRequest.sendGetRequest(url1, params, "UTF-8");
                    deliveryInfo = JsonTools.getDeliveryInfo("data", result);
                    MaxDateNum = JsonTools.getTotal("total",result);
                    String s = deliveryInfo.get(0).getNumber();

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
        Delivery_ListViewActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        Delivery_ListViewActivity.this.finish();
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
                    testData = build_Delivery_Data();
                    adapter = new MyAdapter(getApplicationContext());
                    logistics_listView.setAdapter(adapter);
                    if(adapter.getCount()==MaxDateNum){
                        logistics_listView.removeFooterView(moreView);
                        Toast.makeText(Delivery_ListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
                    }
                    break;

                case 2:
                    int count = adapter.getCount();//监控变量
                    if (count < MaxDateNum) {
                        testData = build_Delivery_Data();
                        logistics_moredata_load.setVisibility(View.VISIBLE);
                        logistics_moredata_pb.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知listView刷新数据
                    }
                    if(adapter.getCount()==MaxDateNum){
                        logistics_listView.removeFooterView(moreView);
                        Toast.makeText(Delivery_ListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
                    }
            }
        }
    };


    /**根据服务器发送的返回数据，生成送货单的数据
     * @return 返回的是供适配器绑定的数据
     */
    private List<Map<String,Object>> build_Delivery_Data(){
        for(int i=0;i<deliveryInfo.size();i++){
            deliveryInfos.add(deliveryInfo.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<deliveryInfos.size();i++){

            map = new HashMap<String, Object>();
            map.put("sgoods_entname", deliveryInfos.get(i).getEntname());
            map.put("sgoods_date", deliveryInfos.get(i).getDate());
            map.put("sgoods_status", deliveryInfos.get(i).getStatus());
            map.put("sgoods_snumber", deliveryInfos.get(i).getNumber());

            data.add(map);

        }

        return data;
    }



}

