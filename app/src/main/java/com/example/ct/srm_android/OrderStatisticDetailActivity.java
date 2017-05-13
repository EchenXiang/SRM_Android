package com.example.ct.srm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderStatisticDetailActivity extends AppCompatActivity {
    private TextView company_code;
    private TextView order_id;
    private TextView order_generate_day;
    private TextView provider_confirm;
    private TextView order_status;
    private TextView delivery_status;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_statistic);
        company_code = (TextView)findViewById(R.id.statistics_item_company_name);
        order_id = (TextView)findViewById(R.id.statistics_item_order_id);
        order_generate_day = (TextView)findViewById(R.id.statistics_item_order_generate_day);
        provider_confirm = (TextView)findViewById(R.id.statistics_item_provider_confirm);
        order_status = (TextView)findViewById(R.id.statistics_item_order_status);
        delivery_status = (TextView)findViewById(R.id.statistics_item_delivery_status);

        company_code.setText(getIntent().getExtras().getString("company_code"));
        order_id.setText(getIntent().getExtras().getString("order_id"));
        order_generate_day.setText(getIntent().getExtras().getString("order_generate_day"));
        provider_confirm.setText(getIntent().getExtras().getString("provider_confirm"));
        order_status.setText(getIntent().getExtras().getString("order_status"));
        delivery_status.setText(getIntent().getExtras().getString("delivery_status"));

        back = (ImageView)findViewById(R.id.order_statistic_detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderStatisticDetailActivity.this.finish();
            }
        });

    }
}
