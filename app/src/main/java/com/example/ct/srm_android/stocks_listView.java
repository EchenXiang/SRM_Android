package com.example.ct.srm_android;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Object.Stock;


import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ProgressBar;



import org.json.JSONObject;

import Tools.*;


public class stocks_listView extends Activity  {
    private List<Map<String, Object>> testData;
    List<Stock> stocks;
    List<Stock> stocksSelect;
    private static final int STOCK_SUCCESS = 1;
    private static final int STOCK_NULL = 0;
    private ListView listView;
    private Button stocks_load;
    private ProgressBar pg;
    private MyAdapter adapter;
    private int pagenumber = 1;
    String result;

    private View moreView;
    private Handler handler;
    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;       // 当前窗口可见项总数
    private ImageView btnReturn;

    private String host;
    private String material_number;
    private String material_name;
    //HttpServletRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks_list_view);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        stocksSelect = new ArrayList<Stock>();

        host = getIntent().getExtras().getString("host");
        material_number = getIntent().getExtras().getString("material_number");
        material_name = getIntent().getExtras().getString("material_name");

        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.stocks_moredata, null);

        stocks_load = (Button) moreView.findViewById(R.id.stocks_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        handler = new Handler();

        btnReturn = (ImageView) findViewById(R.id.btn_left);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stocks_listView.this, stocks_MainActivity.class));
                finish();
            }
        });
        stocks_load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                stocks_load.setVisibility(View.GONE);// 按钮不可见

                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMoreDate();// 加载更多数据
                    }

                }, 1000);
            }
        });
        listView = (ListView) findViewById(R.id.stocks_listView);
        // 加上底部View，注意要放在setAdapter方法前
        listView.addFooterView(moreView);
        // 绑定监听器
        StocksSelectThread();

    }
    private void loadMoreDate() {
        pagenumber = pagenumber+1;
        StocksSelectThread();
    }
    /**
     * 继承BaseAdapter的自定义适配器
     * 把listView中的控件与适配器绑定 而适配器用的是我们从服务器获取的数据
     * Created by luo on 2016/7/1.
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext = null;

        public MyAdapter(Context context) {
            mContext = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return testData.size();
        }

        @Override
        public Object getItem(int position) {
            //TODO Auto-generated method stub
            return testData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Holder holder;          //自定义一个Holder 把一个Item中的View绑定起来
            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.stocks_list_item, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
            holder.stocks_charge_number.setText((String) testData.get(position).get("stocks_charge_number"));
            holder.stocks_warehouse.setText((String) testData.get(position).get("stocks_warehouse"));
            holder.stocks_specification.setText((String) testData.get(position).get("stocks_specification"));
            holder.stocks_material_name.setText((String) testData.get(position).get("stocks_material_name"));
            holder.stocks_amount.setText((String) testData.get(position).get("stocks_amount"));
            holder.stocks_unit.setText((String) testData.get(position).get("stocks_unit"));
            holder.stocks_storage_time.setText((String) testData.get(position).get("stocks_storage_time"));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("host", stocksSelect.get(position).gethost());
                    intent.putExtra("host_number", stocksSelect.get(position).gethost_number());
                    intent.putExtra("charge_number", stocksSelect.get(position).getcharge_number());
                    intent.putExtra("warehouse", stocksSelect.get(position).getwarehouse());
                    intent.putExtra("material_name", stocksSelect.get(position).getmaterial_name());
                    intent.putExtra("specification", stocksSelect.get(position).getspecification());
                    intent.putExtra("amount", stocksSelect.get(position).getamount());
                    intent.putExtra("unit", stocksSelect.get(position).getunit());
                    intent.putExtra("material_number", stocksSelect.get(position).getmaterial_number());
                    intent.putExtra("storage_time", stocksSelect.get(position).getstorage_time());
                    intent.putExtra("remarks", stocksSelect.get(position).getremarks());
                    intent.setClass(stocks_listView.this, stocks_item_chose.class);
                    startActivity(intent);
                }
            });
            return view;
        }
        public class Holder {
            public TextView stocks_unit;
            public TextView stocks_charge_number;
            public TextView stocks_material_name;
            public TextView stocks_specification;
            public TextView stocks_amount;
            public TextView stocks_warehouse;
            public TextView stocks_load;
            public TextView stocks_storage_time;

            public Holder(View view) {
                stocks_charge_number = (TextView) view.findViewById(R.id.stocks_charge_number);
                stocks_warehouse = (TextView) view.findViewById(R.id.stocks_warehouse);
                stocks_specification = (TextView) view.findViewById(R.id.stocks_specification);
                stocks_material_name = (TextView) view.findViewById(R.id.stocks_material_name);
                stocks_amount = (TextView) view.findViewById(R.id.stocks_amount);
                stocks_unit = (TextView) view.findViewById(R.id.stocks_unit);
                stocks_storage_time = (TextView) view.findViewById(R.id.stocks_storage_time);

                stocks_load = (TextView) view.findViewById(R.id.stocks_load);

                TextPaint textPaint = stocks_charge_number.getPaint();
                textPaint.setFakeBoldText(true);

            }
        }
    }


        private void StocksSelectThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://211.87.227.120:3000/Stock/hostStock";//供应商库存接口

                Map<String, String> params = new HashMap<String, String>();
                /**
                 * Created by luo on 2016/7/4.
                 * 供应商库存查询参数
                 */

                params.put("from", ""+pagenumber);
                params.put("pageNumber", "10");
                params.put("host",host);
                params.put("material_number",material_number);
                params.put("material_name",material_name);
                params.put("provider","丹阳市中远车灯有限公司");
                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    result = LuoHttpRequest.sendGetRequest(url, params, "UTF-8");

                    stocks = JsonTools.getStocks("data", result);

                    MaxDateNum = JsonTools.getTotal("total",result);

                    String s = stocks.get(0).getId();
                    System.out.println(s);    //测试orderSelects对象组中是否有数据
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = handler.obtainMessage();
                    if(pagenumber==1) {
                        message.what = 1;
                    }else {
                        message.what = 2;
                    }
                    mHandler.sendMessage(message);

                } catch (Exception e) {
                }
            }
        }).start();
    }

    /**
     * UI界面的Handler 可以接收子线程的消息 并对UI界面进行控制和操作
     * 子线程和UI线程的桥梁
     */
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case STOCK_SUCCESS:
                    testData = build_Stock_Data();
                    adapter = new MyAdapter(getApplicationContext());
                    listView.setAdapter(adapter);
                    if(adapter.getCount()==MaxDateNum){
                        listView.removeFooterView(moreView);
                        Toast.makeText(stocks_listView.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                    }
                    break;
                case STOCK_NULL:
                    System.out.println("msg" +msg);
                    AlertDialog.Builder builder = new  AlertDialog.Builder(stocks_listView.this)
                            .setTitle("提示")
                            .setMessage("没有相应的数据！");
                    builder.setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    onBackPressed();
                                    arg0.dismiss();
                                }
                            });
                    builder.create().show();
                    break;
                case 2:
                    adapter = new MyAdapter(getApplicationContext());
                    int count = adapter.getCount();//监控变量
                    if (count < MaxDateNum) {
                        testData = build_Stock_Data();
                        stocks_load.setVisibility(View.VISIBLE);
                        pg.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知listView刷新数据
                    }
                    if(adapter.getCount()==MaxDateNum){
                        listView.removeFooterView(moreView);
                        Toast.makeText(stocks_listView.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();

                    }
            }

        }
    };

    /**
     * 根据服务器发送的返回数据，生成库存的数据
     *
     * @return 返回的是供适配器绑定的数据
     */
    private List<Map<String, Object>> build_Stock_Data() {

        for(int i=0;i<stocks.size();i++){
            stocksSelect.add(stocks.get(i));
        }

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 0; i < stocksSelect.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("stocks_charge_number", stocksSelect.get(i).getcharge_number());
            map.put("stocks_warehouse", stocksSelect.get(i).getwarehouse());
            map.put("stocks_material_name", stocksSelect.get(i).getmaterial_name());
            map.put("stocks_specification", stocksSelect.get(i).getspecification());
            map.put("stocks_amount", stocksSelect.get(i).getamount());
            map.put("stocks_unit", stocksSelect.get(i).getunit());
            map.put("stocks_storage_time", stocksSelect.get(i).getstorage_time());

            data.add(map);

        }
        return data;
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        startActivity(new Intent(stocks_listView.this, stocks_MainActivity.class));
        Bundle bundle = new Bundle();
        finish();
    }


}