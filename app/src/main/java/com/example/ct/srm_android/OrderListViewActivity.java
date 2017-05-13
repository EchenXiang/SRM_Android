package com.example.ct.srm_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import Tools.*;
import Object.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AbsListView.*;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;

public class OrderListViewActivity extends Activity {
    private List<Map<String, Object>> testData;
    private ListView listView;
    private Button item_button;
    List<Stock> stocks ;

    List<OrderTrack> orderTracks;
    List<OrderTrack> orderTrack;
    List<OrderStatistic> orderStatistics;
    List<OrderStatistic> orderStatistic;
    List<OrderFeedback> orderFeedbacks;
    List<OrderFeedback> orderFeedback;
    List<OrderSelect> orderSelects;
    List<OrderSelect> orderSelect;
    private Button load;
    private ProgressBar pg;
    private MyAdapter adapter;
    private int pagenumber = 1;
    private ImageView back;
    private int Modelnum ;
    private View moreView;

    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private Button btnReturn;

    private String order_select_company_name;
    private String order_select_order_id;
    private String order_select_order_generate_day_from;
    private String order_select_order_generate_day_to;
    private String order_select_order_status;
    private String order_select_provider_confirm;
    private String order_select_delivery_status;

    private String order_track_company_name;
    private String order_track_order_id;
    private String order_track_material_number;
    private String order_track_specified_delivery_from;
    private String order_track_specified_delivery_to;
    private String order_track_delivery_status_spinner;

    private String order_statistic_company_name;
    private String order_statistic_order_generate_day_from;
    private String order_statistic_order_generate_day_to;
    private String order_statistic_provider_confirm_spinner;
    private String order_statistic_order_status_spinner;
    private String order_statistic_delivery_status_spinner;

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
        setContentView(R.layout.activity_order_list_view);
        orderSelects = new ArrayList<OrderSelect>();
        orderTracks = new ArrayList<OrderTrack>();
        orderStatistics = new ArrayList<OrderStatistic>();
        orderFeedbacks = new ArrayList<OrderFeedback>();

        Bundle bundle = this.getIntent().getExtras();
        Modelnum = bundle.getInt("Modelnum");
        if(Modelnum==1){
            order_select_company_name = bundle.getString("order_select_company_name");
            order_select_order_id = bundle.getString("order_select_order_id");
            order_select_order_generate_day_from = bundle.getString("order_select_order_generate_day_from");
            order_select_order_generate_day_to = bundle.getString("order_select_order_generate_day_to");
            order_select_order_status = bundle.getString("order_select_order_status");
            order_select_provider_confirm = bundle.getString("order_select_provider_confirm");
            order_select_delivery_status = bundle.getString("order_select_delivery_status");
        }
        if(Modelnum==2){
            order_track_company_name = bundle.getString("order_track_company_name");
            order_track_order_id = bundle.getString("order_track_order_id");
            order_track_material_number = bundle.getString("order_track_material_number");
            order_track_specified_delivery_from = bundle.getString("order_track_specified_delivery_from");
            order_track_specified_delivery_to = bundle.getString("order_track_specified_delivery_to");
            order_track_delivery_status_spinner = bundle.getString("order_track_delivery_status_spinner");
        }
        if(Modelnum==3){
            order_statistic_company_name = bundle.getString("order_statistic_company_name");
            order_statistic_order_generate_day_from = bundle.getString("order_statistic_order_generate_day_from");
            order_statistic_order_generate_day_to = bundle.getString("order_statistic_order_generate_day_to");
            order_statistic_provider_confirm_spinner = bundle.getString("order_statistic_provider_confirm_spinner");
            order_statistic_order_status_spinner = bundle.getString("order_statistic_order_status_spinner");
            order_statistic_delivery_status_spinner = bundle.getString("order_statistic_delivery_status_spinner");
        }
        if(Modelnum==4){
            order_feedback_company_name = bundle.getString("order_feedback_company_name");
            order_feedback_material_number = bundle.getString("order_feedback_material_number");
            order_feedback_order_id = bundle.getString("order_feedback_order_id");
            order_feedback_order_generate_day_from = bundle.getString("order_feedback_order_generate_day_from");
            order_feedback_order_generate_day_to = bundle.getString("order_feedback_order_generate_day_to");
            order_feedback_plan_ship_day_from = bundle.getString("order_feedback_plan_ship_day_from");
            order_feedback_plan_ship_day_to = bundle.getString("order_feedback_plan_ship_day_to");
        }
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
               OrderListViewActivity.this.finish();
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
        listView = (ListView) findViewById(R.id.order_listView);
        // 加上底部View，注意要放在setAdapter方法前
        listView.addFooterView(moreView);
        // 绑定监听器
        /*listView.setOnScrollListener(this);*/
        if(Modelnum ==1) {
            OrderSelectThread();
        }
        if(Modelnum ==2) {
            OrderTrackThread();
        }
        if(Modelnum ==3){
            OrderStatisticThread();
        }
        if(Modelnum ==4){
            OrderFeedbackThread();
        }

    }


    private List<Map<String, Object>> buildData() {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();


        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("item_image", R.drawable.company_logo);
            map.put("item_title", "中通客车股份有限公司");
            map.put("item_id", "7894561234");
            map.put("item_one", "车灯");
            map.put("item_two", "小");
            map.put("item_three", "1006");
            data.add(map);
        }
        return data;
    }

    private void loadMoreDate() {
        pagenumber = pagenumber+1;
        switch (Modelnum) {
            case 1:

                OrderSelectThread();
                break;
            case 2:
                OrderTrackThread();
                break;
            case 3:
                OrderStatisticThread();
                break;
            case 4:
                OrderFeedbackThread();
                break;

        }

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

                    view = inflater.inflate(R.layout.order_select_listview_item, null);

                    holder = new Holder(view);
                    view.setTag(holder);

            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
               //判断是订单的哪个模块

                //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
                holder.item_image.setBackgroundResource((Integer) testData.get(position).get("item_image"));
                holder.item_title.setText((String) testData.get(position).get("item_title"));
                holder.item_shouyan_id.setText((String) testData.get(position).get("item_id"));
                holder.item_one.setText((String) testData.get(position).get("item_one"));
                holder.item_two.setText((String) testData.get(position).get("item_two"));
                holder.item_three.setText((String) testData.get(position).get("item_three"));

                //为listview上的button添加click监听
                holder.item_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   /* Intent intent = new Intent(OrderListViewActivity.this, item_chose.class);
                    startActivity(intent);*/
                    }
                });
                String string = holder.item_one.getText().toString();

                if (holder.item_one.getText().toString().equals("未确认")) {

                    holder.item_one.setTextColor(Color.RED);
                } else {
                    holder.item_one.setTextColor(Color.BLACK);
                }
                if (holder.item_two.getText().toString().equals("未发货")) {

                    holder.item_two.setTextColor(Color.RED);
                } else {
                    holder.item_two.setTextColor(Color.BLACK);
                }
                if (holder.item_three.getText().toString().equals("未收货")) {

                    holder.item_three.setTextColor(Color.RED);
                } else {
                    holder.item_three.setTextColor(Color.BLACK);
                }
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
                    if(Modelnum == 1) {


                        intent.putExtra("company_code", orderSelects.get(position).getcompany_code());
                        intent.putExtra("order_id", orderSelects.get(position).getorder_id());
                        intent.putExtra("purchase_contact_name", orderSelects.get(position).getpurchase_contact_name());
                        intent.putExtra("contact", orderSelects.get(position).getcontact());
                        intent.putExtra("order_generate_day", orderSelects.get(position).getorder_generate_day());
                        intent.putExtra("provider_confirm", orderSelects.get(position).getprovider_confirm());
                        intent.putExtra("order_status", orderSelects.get(position).getorder_status());
                        intent.putExtra("print_number", orderSelects.get(position).getprint_number());


                        intent.setClass(OrderListViewActivity.this, OrderSelectDetailActivity.class);
                    }
                    if(Modelnum == 2) {


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



                        intent.setClass(OrderListViewActivity.this, OrderTrackDetailActivity.class);
                    }
                    if(Modelnum == 3) {


                        intent.putExtra("company_code", orderStatistics.get(position).getcompany_code());
                        intent.putExtra("order_id", orderStatistics.get(position).getorder_id());
                        intent.putExtra("order_generate_day", orderStatistics.get(position).getorder_generate_day());
                        intent.putExtra("provider_confirm", orderStatistics.get(position).getprovider_confirm());
                        intent.putExtra("order_status", orderStatistics.get(position).getorder_status());
                        intent.putExtra("delivery_status", orderStatistics.get(position).getdelivery_status());



                        intent.setClass(OrderListViewActivity.this, OrderStatisticDetailActivity.class);
                    }
                    if(Modelnum == 4) {


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



                        intent.setClass(OrderListViewActivity.this, OrderFeedbackDetailActivity.class);
                    }
                        startActivity(intent);

                }
            });





            return view;
        }



        public class Holder {
            public ImageView item_image;
            public TextView item_title;
            public TextView item_shouyan_id;
            public TextView item_one;
            public TextView item_two;
            public TextView item_three;
            public TextView item_button;
            public TextView load;

            public Holder(View view) {

                   /* item_image = (ImageView) view.findViewById(R.id.item_image);
                    item_title = (TextView) view.findViewById(R.id.item_title);
                    item_shouyan_id = (TextView) view.findViewById(R.id.item_id);
                    item_one = (TextView) view.findViewById(R.id.item_one);
                    item_two = (TextView) view.findViewById(R.id.item_two);
                    item_three = (TextView) view.findViewById(R.id.item_three);
                    item_button = (TextView) view.findViewById(R.id.item_button);*/
                    load = (TextView) view.findViewById(R.id.load);

                    TextPaint textPaint = item_title.getPaint();
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



    private void OrderStatisticThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                        /*loginByGet();*/

                String url2 ="http://211.87.227.120:3000/Order/supplierOrderTJ";//供应商订单统计接口
                Map<String, String> params = new HashMap<String, String>();

                /**
                 * Created by luo on 2016/7/4.
                 * 供应商订单追踪查询参数
                 */
                params.put("company_code", order_statistic_company_name);
                params.put("confirm", "");
                params.put("from", ""+pagenumber);
                params.put("delivery", "");
                params.put("delivery_status", order_statistic_delivery_status_spinner);
                params.put("order_generate_day_from", order_statistic_order_generate_day_from);
                params.put("order_generate_day_to", order_statistic_order_generate_day_to);
                params.put("order_id", "");
                params.put("order_status", order_statistic_order_status_spinner);
                params.put("pageNumber", "10");
                params.put("provider_confirm", order_statistic_provider_confirm_spinner);
                params.put("ship", "");

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url2,params,"UTF-8");

                    /*stocks = JsonTools.getStocks("data",result);
                    String s = stocks.get(0).getcharge_number();*/
                    //调用写好的Json工具类进行数据的解析并且返回orderSelects

                    orderStatistic = JsonTools.getOrderStatistic("data",result);

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
                   if(Modelnum ==1) {
                       testData = build_Order_Data();
                       adapter = new MyAdapter(getApplicationContext());
                       listView.setAdapter(adapter);
                       if(adapter.getCount()==MaxDateNum){
                           listView.removeFooterView(moreView);
                           Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                       }

                   }
                    if(Modelnum ==2){
                        testData = build_Track_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(Modelnum ==3){
                        testData = build_Statistic_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(Modelnum ==4){
                        testData = build_Feedback_Data();
                        adapter = new MyAdapter(getApplicationContext());
                        listView.setAdapter(adapter);
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }
                    break;

                case 2:

                    int count = adapter.getCount();//监控变量
                    if(Modelnum ==1) {
                        if (count < MaxDateNum) {
                            testData = build_Order_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(Modelnum ==2) {
                        if (count < MaxDateNum) {
                            testData = build_Track_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }
                    if(Modelnum ==3) {
                        if (count < MaxDateNum) {
                            testData = build_Statistic_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
                    }

                    if(Modelnum ==4) {
                        if (count < MaxDateNum) {
                            testData = build_Feedback_Data();
                            load.setVisibility(View.VISIBLE);
                            pg.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();// 通知listView刷新数据
                        }
                        if(adapter.getCount()==MaxDateNum){
                            listView.removeFooterView(moreView);
                            Toast.makeText(OrderListViewActivity.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                        }
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
            map.put("item_image", R.drawable.company_logo);
            map.put("item_title", "中通客车股份有限公司");
            map.put("item_id", "订单号:"+orderSelects.get(i).getorder_id());
            map.put("item_one", orderSelects.get(i).getprovider_confirm());
            map.put("item_two", orderSelects.get(i).getorder_status());
            map.put("item_three", orderSelects.get(i).getdelivery_status());
            data.add(map);

        }


        return data;
    }

    private List<Map<String,Object>> build_Track_Data(){

        for(int i=0;i<orderTrack.size();i++){
            orderTracks.add(orderTrack.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderTracks.size();i++){

            map = new HashMap<String, Object>();
            map.put("item_image", R.drawable.company_logo);
            map.put("item_title", "中通客车股份有限公司");
            map.put("item_id", "订单号:"+orderTracks.get(i).getorder_id());
            map.put("item_one", orderTracks.get(i).getmaterial_number());
            map.put("item_two", orderTracks.get(i).getorder_generate_day());
            map.put("item_three", orderTracks.get(i).getspecified_delivery());
            data.add(map);

        }


        return data;
    }

    private List<Map<String,Object>> build_Statistic_Data(){

        for(int i=0;i<orderStatistic.size();i++){
            orderStatistics.add(orderStatistic.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderStatistics.size();i++){

            map = new HashMap<String, Object>();
            map.put("item_image", R.drawable.company_logo);
            map.put("item_title", "丹阳市中远车灯有限公司");
            map.put("item_id", "订单号:"+orderStatistics.get(i).getorder_id());
            map.put("item_one", orderStatistics.get(i).getprovider_confirm());
            map.put("item_two", orderStatistics.get(i).getorder_status());
            map.put("item_three", orderStatistics.get(i).getdelivery_status());
            data.add(map);

        }


        return data;
    }

    private List<Map<String,Object>> build_Feedback_Data(){

        for(int i=0;i<orderFeedback.size();i++){
            orderFeedbacks.add(orderFeedback.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        Map<String,Object> map = new HashMap<String,Object>();

        for(int i=0;i<orderFeedbacks.size();i++){

            map = new HashMap<String, Object>();
            map.put("item_image", R.drawable.company_logo);
            map.put("item_title", "丹阳市中远车灯有限公司");
            map.put("item_id", "订单号:"+orderFeedbacks.get(i).getorder_id());
            map.put("item_one", orderFeedbacks.get(i).getmaterial_number());
            map.put("item_two", orderFeedbacks.get(i).getorder_unit());
            map.put("item_three", orderFeedbacks.get(i).getmaterial_name());
            data.add(map);

        }


        return data;
    }
}


