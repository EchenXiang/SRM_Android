package com.example.ct.srm_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
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

import Object.*;
import Tools.JsonTools;
import Tools.LuoHttpRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTrackListviewActivity extends AppCompatActivity {
    private List<Map<String, Object>> testData;
    private ListView listView;
    List<OrderTrack> orderTracks;
    List<OrderTrack> orderTrack;
    private Button load;
    private ProgressBar pg;
    private MyAdapter adapter;
    private int pagenumber = 1;
    private ImageView back;
    private View moreView;
    private int MaxDateNum;

    private String order_track_company_name;
    private String order_track_order_id;
    private String order_track_material_number;
    private String order_track_specified_delivery_from;
    private String order_track_specified_delivery_to;
    private String order_track_delivery_status_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track_liseview);
        orderTracks = new ArrayList<OrderTrack>();
        Bundle bundle = this.getIntent().getExtras();
        order_track_company_name = bundle.getString("order_track_company_name");
        order_track_order_id = bundle.getString("order_track_order_id");
        order_track_material_number = bundle.getString("order_track_material_number");
        order_track_specified_delivery_from = bundle.getString("order_track_specified_delivery_from");
        order_track_specified_delivery_to = bundle.getString("order_track_specified_delivery_to");
        order_track_delivery_status_spinner = bundle.getString("order_track_delivery_status_spinner");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.order_moredata, null);

        load = (Button) moreView.findViewById(R.id.load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderTrackListviewActivity.this.finish();
            }
        });

        load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                load.setVisibility(View.GONE);// 按钮不可见

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMoreDate();// 加载更多数据
                        /*load.setVisibility(View.VISIBLE);
                        pg.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知listView刷新数据*/
                    }

                }, 1000);
            }
        });
        listView = (ListView) findViewById(R.id.order_track_listView);
        // 加上底部View，注意要放在setAdapter方法前
        listView.addFooterView(moreView);

        OrderTrackThread();
    }

    private void loadMoreDate() {
        pagenumber = pagenumber + 1;
        OrderTrackThread();
    }


    /**
     * Created by Tian on 2016/7/4.
     */
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
            int s = testData.size();
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

            Holder holder;
            View view;
            //回收item
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                //把vlist layout转换成View【LayoutInflater的作用】

                view = inflater.inflate(R.layout.order_track_listview_item, null);

                holder = new Holder(view);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
            //判断是订单的哪个模块

            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值

            holder.order_track_item_order_id.setText((String) testData.get(position).get("order_id"));
            holder.order_track_item_number.setText((String) testData.get(position).get("number"));
            holder.order_track_item_material_name.setText((String) testData.get(position).get("material_name"));
            holder.order_track_item_order_generate_day.setText((String) testData.get(position).get("order_generate_day"));
            holder.order_track_item_specified_delivery.setText((String) testData.get(position).get("specified_delivery"));


            String string = holder.order_track_item_order_id.getText().toString();


            /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(OrderListViewActivity.this,OrderSelectDetailActivity.class);
                    startActivity(intent);
                }
            });*/

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("order_status", orderTracks.get(position).getorder_status());
                    intent.putExtra("company_code", orderTracks.get(position).getcompany_code());
                    intent.putExtra("order_id", orderTracks.get(position).getorder_id());
                    intent.putExtra("material_number", orderTracks.get(position).getmaterial_number());
                    intent.putExtra("material_name", orderTracks.get(position).getmaterial_name());
                    intent.putExtra("order_generate_day", orderTracks.get(position).getorder_generate_day());
                    intent.putExtra("specified_delivery", orderTracks.get(position).getspecified_delivery());
                    intent.putExtra("number", orderTracks.get(position).getnumber());
                    intent.putExtra("had_ship_number", orderTracks.get(position).gethad_ship_number());
                    intent.putExtra("delivery_complete", orderTracks.get(position).getdelivery_complete());
                    intent.setClass(OrderTrackListviewActivity.this, OrderTrackDetailActivity.class);
                    startActivity(intent);

                }
            });
            return view;
        }



        public class Holder {

            public TextView order_track_item_order_id;
            public TextView order_track_item_number;
            public TextView order_track_item_material_name;
            public TextView order_track_item_order_generate_day;
            public TextView order_track_item_specified_delivery;

            public TextView load;

            public Holder(View view) {


                order_track_item_order_id = (TextView) view.findViewById(R.id.order_track_item_order_id);
                order_track_item_number = (TextView) view.findViewById(R.id.order_track_item_number);
                order_track_item_material_name = (TextView) view.findViewById(R.id.order_track_item_material_name);
                order_track_item_order_generate_day = (TextView) view.findViewById(R.id.order_track_item_order_generate_day);
                order_track_item_specified_delivery = (TextView) view.findViewById(R.id.order_track_item_specified_delivery);

                load = (TextView) view.findViewById(R.id.load);

                TextPaint textPaint = order_track_item_order_id.getPaint();
                textPaint.setFakeBoldText(true);

            }
        }
    }

    private void OrderTrackThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/order/orderStatus";//供应商订单追踪接口
                Map<String, String> params = new HashMap<String, String>();
                /**
                 * Created by luo on 2016/7/4.
                 * 供应商库存查询参数
                 */
                /*params.put("from", "1");
                params.put("pageNumber", "10");
                params.put("host", "");
                params.put("material_number", "");
                params.put("material_name", "");
                params.put("provider", "丹阳市中远车灯有限公司");*/
                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单追踪查询参数
                 */
                params.put("company_code", order_track_company_name);
                params.put("delivery_complete", order_track_delivery_status_spinner);
                params.put("from", ""+pagenumber);
                params.put("material_number", order_track_material_number);
                params.put("order_id", order_track_order_id);
                params.put("pageNumber", "10");
                params.put("provider_name", "丹阳市中远车灯有限公司");
                params.put("specified_delivery_from", order_track_specified_delivery_from);
                params.put("specified_delivery_to", order_track_specified_delivery_to);

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");

                    /*stocks = JsonTools.getStocks("data",result);
                    String s = stocks.get(0).getcharge_number();*/
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects

                    orderTrack = JsonTools.getOrderTrack("data",result);

                    MaxDateNum = JsonTools.getTotal("total",result);



                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();
                    if(pagenumber==1) {
                        message.what = 1;
                    }else {
                        message.what = 2;
                    }
                    handler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){

                }


            }
        }).start();
    }

    /**
     * UI界面的Handler 可以接收子线程的消息 并对UI界面进行控制和操作
     * 子线程和UI线程的桥梁
     */
    private Handler handler = new Handler() {

        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:
                    /*testData = build_Stock_Data();*/
                   
                   
                        testData = build_Track_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderTrackListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                   
                    break;

                case 2:

                    int count = adapter.getCount();//监控变量
                    
                        if (count < MaxDateNum) {
                            testData = build_Track_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderTrackListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                   

            }
        }
    };

    private List<Map<String,Object>> build_Track_Data(){

        for(int i=0;i<orderTrack.size();i++){
            orderTracks.add(orderTrack.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderTracks.size();i++){

            map = new HashMap<String, Object>();

            map.put("order_id", orderTracks.get(i).getorder_id());
            map.put("number", orderTracks.get(i).getnumber());
            map.put("material_name", orderTracks.get(i).getmaterial_name());
            map.put("order_generate_day", orderTracks.get(i).getorder_generate_day());
            map.put("specified_delivery", orderTracks.get(i).getspecified_delivery());
            data.add(map);

        }


        return data;
    }
}
