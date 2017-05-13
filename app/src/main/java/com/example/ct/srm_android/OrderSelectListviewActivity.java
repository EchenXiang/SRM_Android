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

public class OrderSelectListviewActivity extends AppCompatActivity {
    List<OrderSelect> orderSelects;
    List<OrderSelect> orderSelect;
    private List<Map<String, Object>> testData;
    private ListView listView;
    private Button load;
    private ProgressBar pg;
    private MyAdapter adapter;
    private int pagenumber = 1;
    private ImageView back;
    private View moreView;
    private int MaxDateNum;

    private String order_select_company_name;
    private String order_select_order_id;
    private String order_select_order_generate_day_from;
    private String order_select_order_generate_day_to;
    private String order_select_order_status;
    private String order_select_provider_confirm;
    private String order_select_delivery_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select_listview);
        orderSelects = new ArrayList<OrderSelect>();
        Bundle bundle = this.getIntent().getExtras();
        order_select_company_name = bundle.getString("order_select_company_name");
        order_select_order_id = bundle.getString("order_select_order_id");
        order_select_order_generate_day_from = bundle.getString("order_select_order_generate_day_from");
        order_select_order_generate_day_to = bundle.getString("order_select_order_generate_day_to");
        order_select_order_status = bundle.getString("order_select_order_status");
        order_select_provider_confirm = bundle.getString("order_select_provider_confirm");
        order_select_delivery_status = bundle.getString("order_select_delivery_status");

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.order_moredata, null);

        load = (Button) moreView.findViewById(R.id.load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSelectListviewActivity.this.finish();
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
        listView = (ListView) findViewById(R.id.order_select_listView);
        // 加上底部View，注意要放在setAdapter方法前
        listView.addFooterView(moreView);

        OrderSelectThread();
    }

    private void loadMoreDate() {
        pagenumber = pagenumber+1;
        OrderSelectThread();
        }

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

                view = inflater.inflate(R.layout.order_select_listview_item, null);

                holder = new Holder(view);
                view.setTag(holder);

            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
            //判断是订单的哪个模块

            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
            holder.item_order_select_item_order_id.setText((String) testData.get(position).get("order_id"));
            holder.item_order_select_item_order_generate_day.setText((String) testData.get(position).get("order_generate_day"));
            holder.item_order_select_order_provider_confirm.setText((String) testData.get(position).get("provider_confirm"));
            holder.item_order_select_item_order_status.setText((String) testData.get(position).get("order_status"));
            holder.item_order_select_item_delivery_status.setText((String) testData.get(position).get("delivery_status"));


            String string = holder.item_order_select_order_provider_confirm.getText().toString();

            if (holder.item_order_select_order_provider_confirm.getText().toString().equals("未确认")) {

                holder.item_order_select_order_provider_confirm.setBackgroundColor(Color.RED);
            } else {
                holder.item_order_select_order_provider_confirm.setBackgroundColor(Color.BLACK);
            }
            if (holder.item_order_select_item_order_status.getText().toString().equals("未发货")) {

                holder.item_order_select_item_order_status.setBackgroundColor(Color.RED);
            } else {
                holder.item_order_select_item_order_status.setBackgroundColor(Color.BLACK);
            }
            if (holder.item_order_select_item_delivery_status.getText().toString().equals("未收货")) {

                holder.item_order_select_item_delivery_status.setBackgroundColor(Color.RED);
            } else {
                holder.item_order_select_item_delivery_status.setBackgroundColor(Color.BLACK);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("company_code", orderSelects.get(position).getcompany_code());
                    intent.putExtra("order_id", orderSelects.get(position).getorder_id());
                    intent.putExtra("purchase_contact_name", orderSelects.get(position).getpurchase_contact_name());
                    intent.putExtra("contact", orderSelects.get(position).getcontact());
                    intent.putExtra("order_generate_day", orderSelects.get(position).getorder_generate_day());
                    intent.putExtra("provider_confirm", orderSelects.get(position).getprovider_confirm());
                    intent.putExtra("order_status", orderSelects.get(position).getorder_status());
                    intent.putExtra("print_number", orderSelects.get(position).getprint_number());
                    intent.setClass(OrderSelectListviewActivity.this, OrderSelectDetailActivity.class);
                    startActivity(intent);

                }
            });
            return view;
        }



        public class Holder {
            public TextView item_order_select_item_order_id;
            public TextView item_order_select_item_order_generate_day;
            public TextView item_order_select_order_provider_confirm;
            public TextView item_order_select_item_order_status;
            public TextView item_order_select_item_delivery_status;
            public TextView load;

            public Holder(View view) {


                item_order_select_item_order_id = (TextView) view.findViewById(R.id.order_select_item_order_id);
                item_order_select_item_order_generate_day = (TextView) view.findViewById(R.id.order_select_item_order_generate_day);
                item_order_select_order_provider_confirm = (TextView) view.findViewById(R.id.order_select_item_provider_confirm);
                item_order_select_item_order_status = (TextView) view.findViewById(R.id.order_select_item_order_status);
                item_order_select_item_delivery_status = (TextView) view.findViewById(R.id.order_select_item_delivery_status);
                load = (TextView) view.findViewById(R.id.load);

                TextPaint textPaint = item_order_select_item_order_id.getPaint();
                textPaint.setFakeBoldText(true);

            }
        }
    }

    private void OrderSelectThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/
                String url ="http://211.87.227.120:3000/Stock/hostStock";//供应商库存接口
                String url2 ="http://211.87.227.120:3000/order/supplierOrderQuery";//供应商订单接口
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
                 * 供应商订单查询参数
                 */
                params.put("company_code", order_select_company_name);
                params.put("company_name", "丹阳市中远车灯有限公司");
                params.put("delivery_status", order_select_delivery_status);
                params.put("from", ""+pagenumber);
                params.put("orderConfigValue", "");
                params.put("orderConfigValue", "哈哈");
                params.put("orderConfigValue", "学号");
                params.put("order_generate_day_from", order_select_order_generate_day_from);
                params.put("order_generate_day_to", order_select_order_generate_day_to);
                params.put("order_id", order_select_order_id);
                params.put("order_status", order_select_order_status);
                params.put("pageNumber", "10");
                params.put("provider_confirm", order_select_provider_confirm);
                params.put("provider_name", "丹阳市中远车灯有限公司");
                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");

                    /*stocks = JsonTools.getStocks("data",result);
                    String s = stocks.get(0).getcharge_number();*/
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects

                    orderSelect = JsonTools.getOrders("data","data1",result);

                    MaxDateNum = JsonTools.getTotal("total",result);
                    String s = orderSelect.get(0).getorder_id();

                    System.out.println(s);    //测试orderSelects对象组中是否有数据
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

                        testData = build_Order_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderSelectListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }

                    break;

                case 2:

                    int count = adapter.getCount();//监控变量

                        if (count < MaxDateNum) {
                            testData = build_Order_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderSelectListviewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }

            }
        }
    };

    /**根据服务器发送的返回数据，生成订单的数据
     * @return 返回的是供适配器绑定的数据
     */
    private List<Map<String,Object>> build_Order_Data(){

        for(int i=0;i<orderSelect.size();i++){
            orderSelects.add(orderSelect.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderSelects.size();i++){

            map = new HashMap<String, Object>();
            map.put("order_id", orderSelects.get(i).getorder_id());
            map.put("order_generate_day", orderSelects.get(i).getorder_generate_day());
            map.put("provider_confirm", orderSelects.get(i).getprovider_confirm());
            map.put("order_status", orderSelects.get(i).getorder_status());
            map.put("delivery_status", orderSelects.get(i).getdelivery_status());

            data.add(map);

        }


        return data;
    }

}
