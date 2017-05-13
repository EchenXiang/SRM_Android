package com.example.ct.srm_android.Plan;

/**
 * Created by Administrator on 2016/7/15.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ct.srm_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Object.YearPurchasePlan;
import Tools.*;

public class YearPurchasePlanListview extends Activity  {

    private List<Map<String,Object>> testData;
    private ListView listView;
    private List<YearPurchasePlan> yearplanpurchases;
    private List<YearPurchasePlan> yearplanpurchasesTotal;
    private int page=1;
    private View loadMoreView;
    private Button loadMoreButton;
    private int visibleLastIndex = 0;
    private Handler handler = new Handler();
    MyAdapter ada ;
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.year_purchase_plan_listview);

        listView = (ListView)findViewById(R.id.year_listView);
        yearplanpurchasesTotal=new ArrayList<YearPurchasePlan>();

        loadMoreView = getLayoutInflater().inflate(R.layout.threeday_purchase_plan_loadmore, null);
        loadMoreButton = (Button)loadMoreView.findViewById(R.id.three_purchase_plan_loadMoreButton);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadMoreButton.setText("正在加载中...");   //璁剧疆鎸夐挳鏂囧瓧
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMoreData();
                        ada.notifyDataSetChanged();
                        loadMoreButton.setText("加载更多");  //鎭㈠鎸夐挳鏂囧瓧
                    }
                },2000);

            }
        });


        listView.addFooterView(loadMoreView);    //璁剧疆鍒楄〃搴曢儴瑙嗗浘
        purchaseThread();



    }

    private void loadMoreData(){
        page=page+1;
        purchaseThread();
    }

    private void purchaseThread(){
        new Thread(new Runnable() {            //瀛愮嚎绋嬪垱寤?            @Override
            public void run() {
                String url="http://211.87.227.120:3000/admin/YearPlan";//涓夋棩閲囪喘璁″垝鎺ュ彛
                Map<String, String> params = new HashMap<String, String>();
                //params.put("input_time","");
                params.put("input_time",getIntent().getExtras().getString("release_date_select"));
                params.put("number","100121");
                params.put("from",""+page);
                //params.put("provider","");
                params.put("provider",getIntent().getExtras().getString("provider_select"));
                params.put("pageNumber","10");
                //params.put("material_number","");
                params.put("material_number",getIntent().getExtras().getString("material_number_select"));
                params.put("role","供应商");
                params.put("pageSize","10");

                try {
                    //璋冪敤鍐欏ソ鐨勫伐鍏风被LuoHttpRequest鏉ヨ繘琛孏ET鎿嶄綔
                    String result = LuoHttpRequest.sendGetRequest(url,params,"UTF-8");

                    //璋冪敤鍐欏ソ鐨凧son宸ュ叿绫昏繘琛屾暟鎹殑瑙ｆ瀽骞朵笖杩斿洖threeplanpurchases
                    yearplanpurchases = Plan_JsonTools.getyearplanpurchases("data",result);
                    total = JsonTools.getTotal("total",result);
                    System.out.println("***************" + result
                            + "******************");  //鎵撳嵃寰楀埌鐨勭粨鏋滐紙Json瀛楃涓?鏈澶勭悊杩囩殑锛?
                    Message message = mHandler.obtainMessage();
                    if(page==1){
                        message.what = 1;
                    }
                    else{
                        message.what = 0;
                    }
                    mHandler.sendMessage(message);//缃戠粶鎿嶄綔鎴愬姛鍚?瀛愮嚎绋嬬粰Handler鍙憁es

                }catch (Exception e){

                }


            }
        }).start();
    }

    private Handler mHandler = new Handler() {

        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:
                    /*testData = build_Stock_Data();*/
                    testData = build_Yearplan_Data();

                    ada = new MyAdapter(YearPurchasePlanListview.this);

                    listView.setAdapter(ada);

                    if(total == 0){
                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(YearPurchasePlanListview.this, "没有数据", Toast.LENGTH_LONG).show();
                    }
                    if(ada.getCount()==total){
                        listView.removeFooterView(loadMoreView);

                    }


                    break;
                case 0:
                    if(ada.getCount()<total) {
                        testData = build_Yearplan_Data();

                        ada.notifyDataSetChanged();
                    }

                    if(ada.getCount()==total){

                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(YearPurchasePlanListview.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
                    }

            }
        }
    };


    public void clickHandle(View view){
        onBackPressed();
    }

    private List<Map<String,Object>> build_Yearplan_Data(){
        for(int i = 0;i<yearplanpurchases.size();i++){
            yearplanpurchasesTotal.add(yearplanpurchases.get(i));
        }
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        for(int i=0;i<yearplanpurchasesTotal.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("excutive",yearplanpurchasesTotal.get(i).getExecetive());
            map.put("material_name",yearplanpurchasesTotal.get(i).getMaterial_name());
            map.put("day_stock",yearplanpurchasesTotal.get(i).getDay_stock());
            map.put("date",yearplanpurchasesTotal.get(i).getInput_time());
            map.put("first",yearplanpurchasesTotal.get(i).getYear_requir());
            data.add(map);
        }

        return data;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mcontext=null;
        private List<Map<String,Object>> adapterdata;

        public MyAdapter(Context context){
            mcontext=context;
            this.inflater=LayoutInflater.from(context);
        }

        public void addtestdataItem(Map<String,Object> data){
            adapterdata.add(data);
        }


        public  int getCount(){
            return testData.size();

        }

        @Override
        public Object getItem(int position){
            //TODO Auto-generated method stub
            return  testData.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }
        @Override

        public View getView(final int position, View convertView, ViewGroup parent){

            Holder holder ;          //鑷畾涔変竴涓狧older 鎶婁竴涓狪tem涓殑View缁戝畾璧锋潵
            View view = null;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mcontext);
                //鎶妚list layout杞崲鎴怴iew銆怢ayoutInflater鐨勪綔鐢ㄣ€?
                view = inflater.inflate(R.layout.year_purchase_plan_listview_item,null);
                holder = new Holder(view);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (Holder)view.getTag();
            }

            holder = new Holder(view);
            holder.year_purchase_plan_date_show.setText((String)testData.get(position).get("date"));
            holder.year_purchase_plan_excutive_show.setText((String)testData.get(position).get("excutive"));
            holder.year_purchase_plan_material_name_show.setText((String)testData.get(position).get("material_name"));
            holder.year_purchase_plan_day_stock_show.setText((String)testData.get(position).get("day_stock"));
            holder.year_purchase_plan_secondweek_show.setText((String)testData.get(position).get("first"));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();

                    intent.putExtra("provider", yearplanpurchasesTotal.get(position).getProvider());
                    intent.putExtra("material_number", yearplanpurchasesTotal.get(position).getMaterial_number());
                    intent.putExtra("material_name", yearplanpurchasesTotal.get(position).getMaterial_name());
                    intent.putExtra("release_year", yearplanpurchasesTotal.get(position).getInput_time());
                    intent.putExtra("unit", yearplanpurchasesTotal.get(position).getUnit());

                    intent.putExtra("day_stock", yearplanpurchasesTotal.get(position).getDay_stock());
                    intent.putExtra("year_require", yearplanpurchasesTotal.get(position).getYear_requir());
                    intent.putExtra("execetive", yearplanpurchasesTotal.get(position).getExecetive());

                    intent.setClass(YearPurchasePlanListview.this,YearPurchasePlanItemConcrete.class);
                    startActivity(intent);

                }
            });

            return view;
        }

        public class  Holder{
            public TextView year_purchase_plan_excutive_show;
            public TextView year_purchase_plan_material_name_show;
            public TextView year_purchase_plan_day_stock_show;
            public TextView year_purchase_plan_date_show;
            public TextView year_purchase_plan_secondweek_show;


            public  Holder(View view){

                year_purchase_plan_excutive_show=(TextView)view.findViewById((R.id.year_purchase_plan_excutive_show));
                year_purchase_plan_date_show=(TextView)view.findViewById((R.id.year_purchase_plan_date_show));
                year_purchase_plan_day_stock_show=(TextView)view.findViewById((R.id.year_purchase_plan_day_stock_show));
                year_purchase_plan_material_name_show=(TextView)view.findViewById((R.id.year_purchase_plan_material_name_show));

                year_purchase_plan_secondweek_show=(TextView)view.findViewById((R.id.year_purchase_plan_second_show));
            }
        }
    }

}

