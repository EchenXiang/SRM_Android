package com.example.ct.srm_android.Performance;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ct.srm_android.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Object.MaterialScore;
import Object.ProviderHandScore;
import Object.ProviderScore;
import Tools.HttpRequest;
import Tools.JsonTool;


public class InfoListActivity extends ListActivity {
    private Button return_info;
    private List<Map<String, Object>> list;

    private int query;
    private int page=1;
    private Bundle bundle;
    private Button load_button;
    private View load_button_view;
    private Map<String, String> params;
    private List<MaterialScore> material_Score;
    private List<ProviderScore> provider_Score;
    private List<ProviderHandScore> provider_hand_Score;
    private static final int MATERIAL_SCORE_SUCCESS = 1;
    private static final int PROVIDER_SCORE_SUCCESS=2;
    private static final int PROVIDER_HAND_SCORE_SUCCESS=3;
    private static final int QUERY_MATERIAL_SCORE = 1;
    private static final int QUERY_PROVIDER_SCORE = 2;
    private static final int QUERY_PROVIDER_HAND_SCORE = 3;
    private static final String url_material_score = "http://211.87.227.120:3000/Performance/selectMaterialScoreSupply";
    private static final String url_provider_score = "http://211.87.227.120:3000/Performance/selectProviderScoreSupply";
    private static final String url_providerHand_score = "http://211.87.227.120:3000/Performance/proselectSupply";

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);

        bundle = this.getIntent().getExtras();
        query = bundle.getInt("query");
        listView=getListView();
        material_Score=new ArrayList<>();
        provider_Score=new ArrayList<>();
        provider_hand_Score=new ArrayList<>();

        initThread();
        ReturnButtonClick();
    }

    public void initThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                params = new HashMap<String, String>();
                list = new ArrayList<Map<String, Object>>();
                switch (query) {
                    case QUERY_MATERIAL_SCORE:
                        queryMaterialScore();
                        break;
                    case QUERY_PROVIDER_SCORE:
                        queryProviderScore();
                        break;
                    case QUERY_PROVIDER_HAND_SCORE:
                        queryProviderHandScore();
                        break;
                    default:
                        break;
                }
            }
        }).start();
    }

    public void obtainItemInfo(final int choose){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(InfoListActivity.this, DetailActivity.class);
            Bundle bundleTo = new Bundle();
                if(choose==MATERIAL_SCORE_SUCCESS){
                    if(position>=material_Score.size()) Toast.makeText(InfoListActivity.this,"没有更多内容",Toast.LENGTH_SHORT).show();
                    else {
                        bundleTo.putInt("choose", 1);
                        bundleTo.putString("material_number", material_Score.get(position).getMaterial_number().toString());
                        bundleTo.putString("order_number", material_Score.get(position).getOrder_number().toString());
                        bundleTo.putString("host", material_Score.get(position).getHost().toString());
                        bundleTo.putString("host_number", material_Score.get(position).getHost_number().toString());
                        bundleTo.putString("purchase_number", material_Score.get(position).getPurchase_number().toString());
                        bundleTo.putString("confirm_score", material_Score.get(position).getConfirm_score().toString());
                        bundleTo.putString("delivery_score", material_Score.get(position).getDelivery_score().toString());
                        bundleTo.putString("receive_score", material_Score.get(position).getReceive_score().toString());
                        bundleTo.putString("total_score", material_Score.get(position).getTotal_score().toString());
                        bundleTo.putString("score_time", material_Score.get(position).getScore_time().toString());
                        intent.putExtras(bundleTo);
                        startActivity(intent);
                    }
                }
                else if(choose==PROVIDER_SCORE_SUCCESS){
                    if(position>=provider_Score.size()) Toast.makeText(InfoListActivity.this,"没有更多内容",Toast.LENGTH_SHORT).show();
                    else{
                        bundleTo.putInt("choose",2);
                        bundleTo.putString("host_number",provider_Score.get(position).getHost_number().toString());
                        bundleTo.putString("host",provider_Score.get(position).getHost().toString());
                        bundleTo.putString("purchase_number",provider_Score.get(position).getPurchase_number().toString());
                        bundleTo.putString("score",provider_Score.get(position).getScore().toString());
                        bundleTo.putString("score_time",provider_Score.get(position).getScore_time().toString());
                        intent.putExtras(bundleTo);
                        startActivity(intent);
                    }
                }
                else if(choose==PROVIDER_HAND_SCORE_SUCCESS){
                    if(position>=provider_hand_Score.size()) Toast.makeText(InfoListActivity.this,"没有更多内容",Toast.LENGTH_SHORT).show();
                    else{
                        bundleTo.putInt("choose",3);
                        bundleTo.putString("host",provider_hand_Score.get(position).getHost().toString());
                        bundleTo.putString("host_number",provider_hand_Score.get(position).getHost_number().toString());
                        bundleTo.putString("access_start_time",provider_hand_Score.get(position).getAccess_start_time().toString());
                        bundleTo.putString("access_end_time",provider_hand_Score.get(position).getAccess_end_time().toString());
                        bundleTo.putString("score_time",provider_hand_Score.get(position).getScore_time().toString());
                        bundleTo.putString("access_charger",provider_hand_Score.get(position).getAccess_charger().toString());
                        bundleTo.putString("purchase_score",provider_hand_Score.get(position).getPurchase_score().toString());
                        bundleTo.putString("pro_manage_score",provider_hand_Score.get(position).getPro_manage_score().toString());
                        bundleTo.putString("service_score",provider_hand_Score.get(position).getService_score().toString());
                        bundleTo.putString("quality_score",provider_hand_Score.get(position).getQuality_score().toString());
                        bundleTo.putString("total_score",provider_hand_Score.get(position).getTotal_score().toString());
                        intent.putExtras(bundleTo);
                        startActivity(intent);
                    }
                }
        }});
    }

    public void initLoadButton(int choose){
        load_button_view=getLayoutInflater().inflate(R.layout.load_button,null);
        load_button=(Button)load_button_view.findViewById(R.id.load_button);

        View no_more_info_view=getLayoutInflater().inflate(R.layout.no_more_info,null);
        if(choose==1){
            listView.addFooterView(no_more_info_view);
        }
        else if(choose==2){
            listView.addFooterView(load_button_view);
            load_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    page++;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            queryMaterialScore();
                        }
                    }).start();
                }
            });
        }
    }

    public void queryMaterialScore(){
        params.put("from",page+"");
        params.put("pageNumber","10");
        params.put("order_number",bundle.getString("order_number"));
        params.put("material_number",bundle.getString("material_number"));
        params.put("host",bundle.getString("host"));
        params.put("purchase_number",bundle.getString("purchase_number"));
        params.put("start_time",bundle.getString("start_time"));
        params.put("end_time",bundle.getString("end_time"));
        params.put("supply","丹阳市中远车灯有限公司");

        try {
            String result=HttpRequest.sendGetRequest(url_material_score,params,"UTF-8");
            List<MaterialScore> temp=JsonTool.getMaterialScore("data",result);
            for(int i=0;i<temp.size();i++){
                material_Score.add(temp.get(i));
            }
            Message message = mHandler.obtainMessage();
            message.what = MATERIAL_SCORE_SUCCESS;
            mHandler.sendMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryProviderScore(){
        params.put("from",page+"");
        params.put("pageNumber","10");
        params.put("host",bundle.getString("host"));
        params.put("host_number",bundle.getString("host_number"));
        params.put("start_time",bundle.getString("start_time"));
        params.put("end_time",bundle.getString("end_time"));
        params.put("supply","丹阳市中远车灯有限公司");

        try {
            String result=HttpRequest.sendGetRequest(url_provider_score,params,"UTF-8");
            List<ProviderScore> temp=JsonTool.getProviderScore("data",result);
            for(int i=0;i<temp.size();i++){
                provider_Score.add(temp.get(i));
            }
            Message message = mHandler.obtainMessage();
            message.what = PROVIDER_SCORE_SUCCESS;
            mHandler.sendMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void queryProviderHandScore(){
        params.put("from","1");
        params.put("pageNumber","10");
        params.put("host",bundle.getString("host"));
        params.put("start_time",bundle.getString("start_time"));
        params.put("end_time",bundle.getString("end_time"));
        params.put("supply","丹阳市中远车灯有限公司");

        try {
            String result=HttpRequest.sendGetRequest(url_providerHand_score,params,"UTF-8");
            List<ProviderHandScore> temp=JsonTool.getProviderHandScore("data",result);
            for(int i=0;i<temp.size();i++){
                provider_hand_Score.add(temp.get(i));
            }
            Message message = mHandler.obtainMessage();
            message.what = PROVIDER_HAND_SCORE_SUCCESS;
            mHandler.sendMessage(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMaterialScore(){
        for (int i = (page-1)*10; i < material_Score.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("material_number",material_Score.get(i).getMaterial_number());
            item.put("order_number", material_Score.get(i).getOrder_number());
            item.put("total_score",material_Score.get(i).getTotal_score());
            list.add(item);
        }
        if(material_Score.size()<(10*page)&&page==1){
            initLoadButton(1);
        }else if(material_Score.size()==(10*page)&&page==1){
            initLoadButton(2);
        }else if(material_Score.size()<(10*page)&&page>1){
            listView.removeFooterView(load_button_view);
            initLoadButton(1);
        }
    }
    public void buildProviderScore(){
        for (int i = 10*(page-1); i < provider_Score.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("purchase_number",provider_Score.get(i).getPurchase_number());
            item.put("score_time", provider_Score.get(i).getScore_time());
            item.put("score",provider_Score.get(i).getScore());
            list.add(item);
        }
        if( provider_Score.size()<(10*page)&&page==1){
            initLoadButton(1);
        }else if( provider_Score.size()==(10*page)&&page==1){
            initLoadButton(2);
        }else if( provider_Score.size()<(10*page)&&page>1){
            initLoadButton(1);
        }
    }
    public void buildProviderHandScore(){
        for (int i = 10*(page-1); i < provider_hand_Score.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("score_time",provider_hand_Score.get(i).getScore_time());
            item.put("access_start_time", provider_hand_Score.get(i).getAccess_start_time());
            item.put("access_finish_time",provider_hand_Score.get(i).getAccess_end_time());
            item.put("access_charger",provider_hand_Score.get(i).getAccess_charger());
            item.put("total_score",provider_hand_Score.get(i).getTotal_score());
            list.add(item);
        }
        if( provider_hand_Score.size()<(10*page)&&page==1){
            initLoadButton(1);
        }else if( provider_hand_Score.size()==(10*page)&&page==1){
            initLoadButton(2);
        }else if(provider_hand_Score.size()<(10*page)&&page>1){
            initLoadButton(1);
        }
    }

    private Handler mHandler=new Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            SimpleAdapter simAdapter;
            switch (msg.what) {
                case MATERIAL_SCORE_SUCCESS:
                    buildMaterialScore();
                    simAdapter = new SimpleAdapter(InfoListActivity.this, list, R.layout.material_score_listitem, new String[]{ "order_number", "material_number", "total_score"}, new int[]{ R.id.order_num_material, R.id.matter_num_material, R.id.total_score_material});
                    listView.setAdapter(simAdapter);
                    obtainItemInfo(MATERIAL_SCORE_SUCCESS);
                    break;
                case PROVIDER_SCORE_SUCCESS:
                    buildProviderScore();
                    simAdapter = new SimpleAdapter(InfoListActivity.this, list, R.layout.provider_score_listitem, new String[]{"purchase_number", "score_time", "score"}, new int[]{ R.id.purchase_num_system, R.id.score_time_system, R.id.score_system});
                    listView.setAdapter(simAdapter);
                    obtainItemInfo(PROVIDER_SCORE_SUCCESS);
                    break;
                case PROVIDER_HAND_SCORE_SUCCESS:
                    buildProviderHandScore();
                    simAdapter = new SimpleAdapter(InfoListActivity.this, list, R.layout.provider_handscore_listitem, new String[]{ "score_time", "access_start_time","access_finish_time", "access_charger","total_score"}, new int[]{R.id.score_time_hand, R.id.access_starttime_hand, R.id.access_finishtime_hand, R.id.access_charger_hand,R.id.total_score_hand});
                    listView.setAdapter(simAdapter);
                    obtainItemInfo(PROVIDER_HAND_SCORE_SUCCESS);
                    break;
            }
        }
    };

    public void ReturnButtonClick() {
        return_info = (Button) findViewById(R.id.return_info);
        return_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
