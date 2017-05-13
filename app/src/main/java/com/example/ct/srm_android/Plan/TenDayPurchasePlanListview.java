package com.example.ct.srm_android.Plan;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import Tools.*;

import Object.TenDayPurchasePlan;

/**
 * Created by acer on 2016/7/15.
 */
public class TenDayPurchasePlanListview extends Activity  {
    private List<Map<String,Object>> testData;
    private ListView listView;
    private List<TenDayPurchasePlan> tenplanpurchases;
    private List<TenDayPurchasePlan> tenplanpurchasesTotal;
    private int page=1;
    private View loadMoreView;
    private Button loadMoreButton;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount;// 当前窗口可见项总数
    private Handler handler = new Handler();
    MyAdapter ada ;
    private int total=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenday_purchase_plan_listview);
        listView = (ListView)findViewById(R.id.tenday_listView);

        tenplanpurchasesTotal = new ArrayList<TenDayPurchasePlan>();

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
                },2000);

            }
        });


        listView.addFooterView(loadMoreView);    //设置列表底部视图

        purchaseplanThread();

    }

    private void loadMoreData(){
        page=page+1;
        purchaseplanThread();
    }

    private void purchaseplanThread(){
        new Thread(new Runnable() {            //子线程创建
            @Override
            public void run() {
                String url="http://211.87.227.120:3000/admin/DecadPlan";//旬采购计划接口
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("input_time","");
                params.put("number","100121");
                params.put("from",""+page);
                // params.put("provider","");
                params.put("provider",getIntent().getExtras().getString("provider_select"));
                params.put("pageNumber","10");
                // params.put("material_number","");
                params.put("material_number",getIntent().getExtras().getString("material_number_select"));
                params.put("role","供应商");
                params.put("pageSize","10");
                params.put("input_time",getIntent().getExtras().getString("release_date_select"));

                try {
                    //调用写好的工具类LuoHttpRequest来进行GET操作
                    String result = LuoHttpRequest.sendGetRequest(url,params,"UTF-8");

                    //调用写好的Json工具类进行数据的解析并且返回tenplanpurchases
                    tenplanpurchases = Plan_JsonTools.getTenplanpurchases("data",result);
                    total = JsonTools.getTotal("total",result);
                    System.out.println("***************" + result
                            + "******************");  //打印得到的结果（Json字符串 未被处理过的）
                    Message message = mHandler.obtainMessage();
                    if(page==1){
                        message.what = 1;
                    }
                    else{
                        message.what = 0;
                    }
                    mHandler.sendMessage(message);//网络操作成功后 子线程给Handler发mes

                }catch (Exception e){

                }


            }
        }).start();
    }


    private Handler mHandler = new Handler() {

        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:
                    testData = build_Tendayplan_Data();
                    ada = new MyAdapter(TenDayPurchasePlanListview.this);

                    listView.setAdapter(ada);

                    if(total == 0){
                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(TenDayPurchasePlanListview.this, "没有数据", Toast.LENGTH_LONG).show();
                    }
                    if(ada.getCount()==total){
                        listView.removeFooterView(loadMoreView);

                    }

                    break;
                case 0:
                    if(ada.getCount()<total) {
                        testData = build_Tendayplan_Data();

                        ada.notifyDataSetChanged();
                    }

                    if(ada.getCount()==total){

                        listView.removeFooterView(loadMoreView);
                        Toast.makeText(TenDayPurchasePlanListview.this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
                    }

            }
        }
    };



    public void clickHandle(View view){
        onBackPressed();
    }

    private List<Map<String,Object>> build_Tendayplan_Data(){

        for(int i = 0;i<tenplanpurchases.size();i++){
            tenplanpurchasesTotal.add(tenplanpurchases.get(i));
        }

        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        for(int i=0;i<tenplanpurchasesTotal.size();i++){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("excutive",tenplanpurchasesTotal.get(i).getExecetive());
            map.put("material_name",tenplanpurchasesTotal.get(i).getMaterial_name());
            map.put("day_stock",tenplanpurchasesTotal.get(i).getDay_stock());
            map.put("date",tenplanpurchasesTotal.get(i).getInput_time());
            map.put("first",tenplanpurchasesTotal.get(i).getDecad_require());
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
                view = inflater.inflate(R.layout.tenday_purchase_plan_listview_item,null);
                holder = new Holder(view);
                view.setTag(holder);
            }else {
                view = convertView;
                holder = (Holder)view.getTag();
            }

            holder = new Holder(view);
            holder.tenday_purchase_plan_date_show.setText((String)testData.get(position).get("date"));
            holder.tenday_purchase_plan_excutive_show.setText((String)testData.get(position).get("excutive"));
            holder.tenday_purchase_plan_material_name_show.setText((String)testData.get(position).get("material_name"));
            holder.tenday_purchase_plan_day_stock_show.setText((String)testData.get(position).get("day_stock"));
            holder.tenday_purchase_plan_secondweek_show.setText((String)testData.get(position).get("first"));


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
//                    startActivity(intent);

                /*finish();*/

                    Intent intent = new Intent();

                    intent.putExtra("provider", tenplanpurchasesTotal.get(position).getProvider());
                    intent.putExtra("material_number", tenplanpurchasesTotal.get(position).getMaterial_number());
                    intent.putExtra("material_name", tenplanpurchasesTotal.get(position).getMaterial_name());
                    intent.putExtra("release_decad", tenplanpurchasesTotal.get(position).getInput_time());
                    intent.putExtra("unit", tenplanpurchasesTotal.get(position).getUnit());

                    intent.putExtra("day_stock", tenplanpurchasesTotal.get(position).getDay_stock());
                    intent.putExtra("decad_require", tenplanpurchasesTotal.get(position).getDecad_require());
                    intent.putExtra("execetive", tenplanpurchasesTotal.get(position).getExecetive());


                    intent.setClass(TenDayPurchasePlanListview.this,TenDayPurchasePlanItemConcrete.class);
                    startActivity(intent);

                }
            });

            return view;
        }

        public class  Holder{

            public TextView tenday_purchase_plan_excutive_show;
            public TextView tenday_purchase_plan_material_name_show;
            public TextView tenday_purchase_plan_day_stock_show;
            public TextView tenday_purchase_plan_date_show;
            public TextView tenday_purchase_plan_secondweek_show;


            public  Holder(View view){

                tenday_purchase_plan_excutive_show=(TextView)view.findViewById((R.id.tenday_purchase_plan_excutive_show));
                tenday_purchase_plan_date_show=(TextView)view.findViewById((R.id.tenday_purchase_plan_date_show));
                tenday_purchase_plan_day_stock_show=(TextView)view.findViewById((R.id.tenday_purchase_plan_day_stock_show));
                tenday_purchase_plan_material_name_show=(TextView)view.findViewById((R.id.tenday_purchase_plan_material_name_show));

                tenday_purchase_plan_secondweek_show=(TextView)view.findViewById((R.id.tenday_purchase_plan_second_show));
            }
        }
    }
}

