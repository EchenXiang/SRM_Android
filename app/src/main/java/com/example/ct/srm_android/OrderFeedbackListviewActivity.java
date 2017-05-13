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

public class OrderFeedbackListviewActivity extends AppCompatActivity {
    private List<Map<String, Object>> testData;
    private ListView listView;

    List<OrderFeedback> orderFeedbacks;
    List<OrderFeedback> orderFeedback;
    private Button load;
    private ProgressBar pg;
    private MyAdapter adapter;
    private int pagenumber = 1;
    private ImageView back;
    private View moreView;
    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;

    private String order_feedback_company_name;
    private String order_feedback_material_number;
    private String order_feedback_order_id;
    private String order_feedback_order_generate_day_from;
    private String order_feedback_order_generate_day_to;
    private String order_feedback_plan_ship_day_from;
    private String order_feedback_plan_ship_day_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_feedback_listview);
        orderFeedbacks = new ArrayList<OrderFeedback>();
        Bundle bundle = this.getIntent().getExtras();
        order_feedback_company_name = bundle.getString("order_feedback_company_name");
        order_feedback_material_number = bundle.getString("order_feedback_material_number");
        order_feedback_order_id = bundle.getString("order_feedback_order_id");
        order_feedback_order_generate_day_from = bundle.getString("order_feedback_order_generate_day_from");
        order_feedback_order_generate_day_to = bundle.getString("order_feedback_order_generate_day_to");
        order_feedback_plan_ship_day_from = bundle.getString("order_feedback_plan_ship_day_from");
        order_feedback_plan_ship_day_to = bundle.getString("order_feedback_plan_ship_day_to");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.order_moredata, null);

        load = (Button) moreView.findViewById(R.id.load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
      /*  item_button = (Button) findViewById(R.id.item_button);*/
        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFeedbackListviewActivity.this.finish();
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
        listView = (ListView) findViewById(R.id.order_feedback_listView);
        // 加上底部View，注意要放在setAdapter方法前
        listView.addFooterView(moreView);
        OrderFeedbackThread();
    }

    private void loadMoreDate() {
        pagenumber = pagenumber + 1;
        OrderFeedbackThread();
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

                view = inflater.inflate(R.layout.order_feedback_listview_item, null);

                holder = new Holder(view);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
            //判断是订单的哪个模块

            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
            holder.order_feedback_item_order_id.setText((String) testData.get(position).get("order_id"));
            holder.order_feedback_item_number.setText((String) testData.get(position).get("number"));
            holder.order_feedback_item_material_name.setText((String) testData.get(position).get("material_name"));
            holder.order_feedback_item_order_generate_day.setText((String) testData.get(position).get("order_generate_day"));
            holder.order_feedback_item_specified_delivery.setText((String) testData.get(position).get("specified_delivery"));
            holder.order_feedback_item_plan_ship_day.setText((String) testData.get(position).get("plan_ship_day"));
            holder.order_feedback_item_plan_delivery_day.setText((String) testData.get(position).get("plan_delivery_day"));


            //为listview上的button添加click监听

            String string = holder.order_feedback_item_order_id.getText().toString();




            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();



                        intent.putExtra("company_code", orderFeedbacks.get(position).getcompany_code());
                        intent.putExtra("order_id", orderFeedbacks.get(position).getorder_id());
                        intent.putExtra("material_number", orderFeedbacks.get(position).getmaterial_number());
                        intent.putExtra("material_name", orderFeedbacks.get(position).getmaterial_name());
                        intent.putExtra("order_unit", orderFeedbacks.get(position).getorder_unit());
                        intent.putExtra("number", orderFeedbacks.get(position).getnumber());
                        intent.putExtra("plan_supply_number", orderFeedbacks.get(position).getplan_supply_number());
                        intent.putExtra("plan_ship_day", orderFeedbacks.get(position).getplan_ship_day());
                        intent.putExtra("plan_delivery_day", orderFeedbacks.get(position).getplan_delivery_day());
                        intent.putExtra("order_generate_day", orderFeedbacks.get(position).getorder_generate_day());
                        intent.putExtra("specified_delivery", orderFeedbacks.get(position).getspecified_delivery());



                        intent.setClass(OrderFeedbackListviewActivity.this, OrderFeedbackDetailActivity.class);

                    startActivity(intent);

                }
            });
            return view;
        }



        public class Holder {
            public TextView order_feedback_item_order_id;
            public TextView order_feedback_item_number;
            public TextView order_feedback_item_material_name;
            public TextView order_feedback_item_order_generate_day;
            public TextView order_feedback_item_specified_delivery;
            public TextView order_feedback_item_plan_ship_day;
            public TextView order_feedback_item_plan_delivery_day;

            public TextView load;

            public Holder(View view) {


                order_feedback_item_order_id = (TextView) view.findViewById(R.id.order_feedback_item_order_id);
                order_feedback_item_number = (TextView) view.findViewById(R.id.order_feedback_item_number);
                order_feedback_item_material_name = (TextView) view.findViewById(R.id.order_feedback_item_material_name);
                order_feedback_item_order_generate_day = (TextView) view.findViewById(R.id.order_feedback_item_order_generate_day);
                order_feedback_item_specified_delivery = (TextView) view.findViewById(R.id.order_feedback_item_specified_delivery);
                order_feedback_item_plan_ship_day = (TextView) view.findViewById(R.id.order_feedback_item_plan_ship_day);
                order_feedback_item_plan_delivery_day = (TextView) view.findViewById(R.id.order_feedback_item_plan_delivery_day);


                load = (TextView) view.findViewById(R.id.load);

                TextPaint textPaint = order_feedback_item_order_id.getPaint();
                textPaint.setFakeBoldText(true);

            }
        }
    }

    private void OrderFeedbackThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/order/orderFeedback";//供应商订单反馈接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单追踪查询参数
                 */
                params.put("company_code", order_feedback_company_name);
                params.put("material_number", order_feedback_material_number);
                params.put("from", ""+pagenumber);
                params.put("order_generate_day_from", order_feedback_order_generate_day_from);
                params.put("order_generate_day_to", order_feedback_order_generate_day_to);
                params.put("order_id", order_feedback_order_id);
                params.put("pageNumber", "10");
                params.put("plan_ship_day_from", order_feedback_plan_ship_day_from);
                params.put("plan_ship_day_to", order_feedback_plan_ship_day_to);
                params.put("provider_name", "丹阳市中远车灯有限公司");


                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");

                    /*stocks = JsonTools.getStocks("data",result);
                    String s = stocks.get(0).getcharge_number();*/
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects

                    orderFeedback = JsonTools.getOrderFeedback("data",result);

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

                        testData = build_Feedback_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderFeedbackListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }

                    break;

                case 2:

                    int count = adapter.getCount();//监控变量

                        if (count < MaxDateNum) {
                            testData = build_Feedback_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderFeedbackListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }


            }
        }
    };

    private List<Map<String,Object>> build_Feedback_Data(){

        for(int i=0;i<orderFeedback.size();i++){
            orderFeedbacks.add(orderFeedback.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderFeedbacks.size();i++){

            map = new HashMap<String, Object>();
            map.put("order_id", orderFeedbacks.get(i).getorder_id());
            map.put("number", orderFeedbacks.get(i).getnumber());
            map.put("material_name", orderFeedbacks.get(i).getmaterial_name());
            map.put("order_generate_day", orderFeedbacks.get(i).getorder_generate_day());
            map.put("specified_delivery", orderFeedbacks.get(i).getspecified_delivery());
            map.put("plan_ship_day", orderFeedbacks.get(i).getplan_ship_day());
            map.put("plan_delivery_day", orderFeedbacks.get(i).getplan_delivery_day());
            data.add(map);

        }


        return data;
    }
}
