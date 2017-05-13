package com.example.ct.srm_android.Logistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;

public class LogisticsDetailActivity extends AppCompatActivity {
    private TextView logistics_entname;
    private TextView logistics_number;
    private TextView logistics_company;
    private TextView logistics_company_tele;

    private TextView logistics_date;
    private TextView logistics_plan_date;
    private TextView logistics_status;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_detail);

        logistics_entname = (TextView)findViewById(R.id.delivery_item_logistics_entname);
        logistics_number = (TextView)findViewById(R.id.delivery_item_logistics_number);
        logistics_company = (TextView)findViewById(R.id.delivery_item_logistics_company);
        logistics_date = (TextView)findViewById(R.id.delivery_item_logistics_date);
        logistics_plan_date = (TextView)findViewById(R.id.delivery_item_logistics_plan_date);
        logistics_company_tele = (TextView)findViewById(R.id.delivery_item_logistics_company_tele);
        logistics_status = (TextView)findViewById(R.id.delivery_item_logistics_status);


        logistics_entname.setText(getIntent().getExtras().getString("logistics_entname"));
        logistics_number.setText(getIntent().getExtras().getString("logistics_number"));
        logistics_company.setText(getIntent().getExtras().getString("logistics_company"));
        logistics_date.setText(getIntent().getExtras().getString("logistics_date"));
        logistics_plan_date.setText(getIntent().getExtras().getString("logistics_plan_date"));
        logistics_company_tele.setText(getIntent().getExtras().getString("logistics_company_tele"));
        logistics_status.setText(getIntent().getExtras().getString("logistics_status"));

        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogisticsDetailActivity.this.finish();
            }
        });
    }
}
















