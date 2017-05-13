package com.example.ct.srm_android.Performance;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

import com.example.ct.srm_android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends ListActivity {
    private Button return_detail;
    private List<Map<String, Object>> listitems;
    private Bundle bundle;

    String[] material_score_titles = new String[]{"订单号","物料号","主机厂代号","主机厂名称","采购组代号","确认及时性得分","发货及时性得分","到货及时性得分","总分","打分时间"};
    String[] provider_score_titles = new String[]{"主机厂代号","主机厂名称","采购组代号","得分","打分时间"};
    String[] providerHand_score_titles=new String[]{"主机厂代号","主机厂名称","评估开始时间","评估结束时间","打分时间","评估负责人","采购评分","供管评分","质量评分","服务评分","总得分"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        bundle= this.getIntent().getExtras();

        initListView();
        ReturnButton();
    }

    public void initListView(){
        listitems = new ArrayList<Map<String, Object>>();
        if(bundle.getInt("choose")==1){
            for (int i = 0; i < material_score_titles.length; i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("name", material_score_titles[i]);
                if(i==0) item.put("content", bundle.getString("order_number"));
                else if(i==1) item.put("content", bundle.getString("material_number"));
                else if(i==2) item.put("content",bundle.getString("host_number"));
                else if(i==3) item.put("content",bundle.getString("host"));
                else if(i==4) item.put("content",bundle.getString("purchase_number"));
                else if(i==5) item.put("content",bundle.getString("confirm_score"));
                else if(i==6) item.put("content",bundle.getString("delivery_score"));
                else if(i==7) item.put("content",bundle.getString("receive_score"));
                else if(i==8) item.put("content",bundle.getString("total_score"));
                else if(i==9) item.put("content",bundle.getString("score_time"));
                listitems.add(item);
            }
        }
        else if(bundle.getInt("choose")==2){
            for (int i = 0; i < provider_score_titles.length; i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("name", provider_score_titles[i]);
                if(i==0) item.put("content", bundle.getString("host_number"));
                else if(i==1) item.put("content", bundle.getString("host"));
                else if(i==2) item.put("content",bundle.getString("purchase_number"));
                else if(i==3) item.put("content",bundle.getString("score"));
                else if(i==4) item.put("content",bundle.getString("score_time"));
                listitems.add(item);
            }
        }
        else if(bundle.getInt("choose")==3){
            for (int i = 0; i < providerHand_score_titles.length; i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("name", providerHand_score_titles[i]);
                if(i==0) item.put("content", bundle.getString("host_number"));
                else if(i==1) item.put("content", bundle.getString("host"));
                else if(i==2) item.put("content",bundle.getString("access_start_time"));
                else if(i==3) item.put("content",bundle.getString("access_end_time"));
                else if(i==4) item.put("content",bundle.getString("score_time"));
                else if(i==5) item.put("content",bundle.getString("access_charger"));
                else if(i==6) item.put("content",bundle.getString("purchase_score"));
                else if(i==7) item.put("content",bundle.getString("pro_manage_score"));
                else if(i==8) item.put("content",bundle.getString("service_score"));
                else if(i==9) item.put("content",bundle.getString("quality_score"));
                else if(i==10) item.put("content",bundle.getString("total_score"));
                listitems.add(item);
            }
        }


        final SimpleAdapter simAdapter = new SimpleAdapter(this, listitems, R.layout.detail_list_item, new String[]{"name", "content"},
                new int[]{R.id.itemName, R.id.itemContent});
        getListView().setAdapter(simAdapter);
    }

    public void ReturnButton(){
        return_detail = (Button) findViewById(R.id.return_detail);
        return_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
