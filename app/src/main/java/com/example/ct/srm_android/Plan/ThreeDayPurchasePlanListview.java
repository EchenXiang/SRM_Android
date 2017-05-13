package com.example.ct.srm_android.Plan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
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
import Object.ThreeDayPurchasePlan;
import Tools.*;

public class ThreeDayPurchasePlanListview extends Activity  {
    private List<Map<String,Object>> testData;
    private ListView listView;
    private List<ThreeDayPurchasePlan> threeplanpurchases;
    private List<ThreeDayPurchasePlan> threeplanpurchasesTotal ;
    private View loadMoreView;
    private Button loadMoreButton;
    int page = 1;
    private Handler handler = new Handler();
    MyAdapter ada ;
    private int total = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threeday_purchase_plan_listview);
        listView = (ListView)findViewById(R.id.Three_purechase_plan_listView);

        threeplanpurchasesTotal = new ArrayList<ThreeDayPurchasePlan>();

        loadMoreView = getLayoutInflater().inflate(R.layout.threeday_purchase_plan_loadmore, null);
        loadMoreButton = (Button)loadMoreView.findViewById(R.id.three_purchase_plan_loadMoreButton);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadMoreButton.setText("正在加载中...");   //设置按钮文字
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        loadMoreData();
                        ada.notifyDataSetChanged();
                        loadMoreButton.setText("查看更多...");  //恢复按钮文字
                    }
                },1000);

            }
        });


        listView.addFooterView(loadMoreView);    //设置列表底部视图

        PurchasePlanThread();

    }

    private void loadMoreData(){
        page=page+1;
        PurchasePlanThread();
    }

    private void PurchasePlanThread(){

            new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                String url="http://211.87.227.120:3000/admin/ThreedayPlan";//三日采购计划接口
                Map<String, String> params = new HashMap<String, String>();
                //params.put("input_time","");
                params.put("number","100121");
                params.put("from", ""+page);
                  //params.put("provider","");
                params.put("provider",getIntent().getExtras().getString("provider_select"));
                params.put("pageNumber","10");
                  //params.put("material_number","");
                params.put("material_number",getIntent().getExtras().getString("material_number_select"));
                params.put("role","供应商");
                params.put("pageSize","10");
                params.put("input_time",getIntent().getExtras().getString("release_date_select"));

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url,params,"UTF-8");

                    //调用写好的Json工具类进行数据的解析并且返回threeplanpurchases
                    threeplanpurchases = Plan_JsonTools.getThreeplanpurchases("data",result);
                    total = JsonTools.getTotal("total",result);
                    //maxDataNumber =Integer.valueOf(JsonTools.getThreeplanTotal("total",result)) ;

                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    //System.out.println("*********************"+maxDataNumber);
                    Message message = mHandler.obtainMessage();
                    if(page==1){
                        message.what = 1;
                    }
                    else{
                        message.what = 0;
                    }
                    mHandler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes

                }catch (Exception e){

                }


            }
        }).start();
    }

    private Handler mHandler = new Handler() {

        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:

                    testData = build_Threedayplan_Data();

                    ada = new MyAdapter(ThreeDayPurchasePlanListview.this);

                    listView.setAdapter(ada);

                    if(ada.getCount()==0){
                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(ThreeDayPurchasePlanListview.this, "没有数据", Toast.LENGTH_LONG).show();
                    }

                    else if(ada.getCount()==total){
                        listView.removeFooterView(loadMoreView);

                    }

                    break;
                case 0:
                    if(ada.getCount()<total) {
                        testData = build_Threedayplan_Data();

                        ada.notifyDataSetChanged();
                    }

                    if(ada.getCount()==total){

                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(ThreeDayPurchasePlanListview.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
                    }

            }
        }
    };


    public void clickHandle(View view){
        onBackPressed();
    }


    private List<Map<String,Object>> build_Threedayplan_Data(){

        for(int i = 0;i<threeplanpurchases.size();i++){
            threeplanpurchasesTotal.add(threeplanpurchases.get(i));
        }

        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        for(int i=0;i<threeplanpurchasesTotal.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();

            map.put("excutive",threeplanpurchasesTotal.get(i).getExecetive());
            map.put("material_name",threeplanpurchasesTotal.get(i).getMaterial_name());
            map.put("day_stock",threeplanpurchasesTotal.get(i).getDay_stock());
            map.put("date",threeplanpurchasesTotal.get(i).getInput_time());
            map.put("first",threeplanpurchasesTotal.get(i).getFirst_day_require());
            map.put("second",threeplanpurchasesTotal.get(i).getSecond_day_require());
            map.put("third",threeplanpurchasesTotal.get(i).getThird_day_require());
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

            Holder holder ;          //自定义一个Holder 把一个Item中的View绑定起来
            View view = null;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mcontext);
                //把vlist layout转换成View【LayoutInflater的作用】
                view = inflater.inflate(R.layout.threeday_purchase_plan_listview_item,null);
                holder = new Holder(view);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (Holder)view.getTag();
            }

            holder = new Holder(view);

            holder.threeday_purchase_plan_date_show.setText((String)testData.get(position).get("date"));
            holder.threeday_purchase_plan_excutive_show.setText((String)testData.get(position).get("excutive"));
            holder.threeday_purchase_plan_material_name_show.setText((String)testData.get(position).get("material_name"));
            holder.threeday_purchase_plan_day_stock_show.setText((String)testData.get(position).get("day_stock"));
            holder.threeday_purchase_plan_first_show.setText((String)testData.get(position).get("first"));
            holder.threeday_purchase_plan_second_show.setText((String)testData.get(position).get("second"));
            holder.threeday_purchase_plan_third_show.setText((String)testData.get(position).get("third"));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();

                    intent.putExtra("provider", threeplanpurchasesTotal.get(position).getProvider());
                    intent.putExtra("material_number", threeplanpurchasesTotal.get(position).getMaterial_number());
                    intent.putExtra("material_name", threeplanpurchasesTotal.get(position).getMaterial_name());
                    intent.putExtra("release_date", threeplanpurchasesTotal.get(position).getInput_time());
                    intent.putExtra("unit", threeplanpurchasesTotal.get(position).getUnit());

                    intent.putExtra("day_stock", threeplanpurchasesTotal.get(position).getDay_stock());
                    intent.putExtra("first_day_require", threeplanpurchasesTotal.get(position).getFirst_day_require());
                    intent.putExtra("second_day_require",threeplanpurchasesTotal.get(position).getSecond_day_require());
                    intent.putExtra("third_day_require", threeplanpurchasesTotal.get(position).getThird_day_require());
                    intent.putExtra("execetive", threeplanpurchasesTotal.get(position).getExecetive());


                    intent.setClass(ThreeDayPurchasePlanListview.this,ThreeDayPurchasePlanItemConcrete.class);
                    startActivity(intent);

                }
            });

            return view;
        }

        public class  Holder{
            public TextView threeday_purchase_plan_excutive_show;
            public TextView threeday_purchase_plan_material_name_show;
            public TextView threeday_purchase_plan_day_stock_show;
            public TextView threeday_purchase_plan_date_show;
            public TextView threeday_purchase_plan_first_show;
            public TextView threeday_purchase_plan_second_show;
            public TextView threeday_purchase_plan_third_show;


            public  Holder(View view){

                threeday_purchase_plan_excutive_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_excutive_show));
                threeday_purchase_plan_date_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_date_show));
                threeday_purchase_plan_day_stock_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_day_stock_show));
                threeday_purchase_plan_material_name_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_material_name_show));
                threeday_purchase_plan_first_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_first_show));
                threeday_purchase_plan_second_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_second_show));
                threeday_purchase_plan_third_show=(TextView)view.findViewById((R.id.threeday_purchase_plan_third_show));
            }
        }
    }

}


