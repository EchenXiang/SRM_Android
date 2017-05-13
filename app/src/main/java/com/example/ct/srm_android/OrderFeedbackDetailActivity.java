package com.example.ct.srm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderFeedbackDetailActivity extends AppCompatActivity {
    private TextView company_code;
    private TextView order_id;
    private TextView material_number;
    private TextView material_name;
    private TextView order_unit;
    private TextView number;
    private TextView plan_supply_number;
    private TextView plan_ship_day;
    private TextView plan_delivery_day;
    private TextView order_generate_day;
    private TextView specified_delivery;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_feedback_detail);
        company_code = (TextView)findViewById(R.id.feedbacks_item_company_name);
        order_id = (TextView)findViewById(R.id.feedbacks_item_order_id);
        material_number = (TextView)findViewById(R.id.feedbacks_item_material_number);
        material_name = (TextView)findViewById(R.id.feedbacks_item_material_name);
        order_unit = (TextView)findViewById(R.id.feedbacks_item_order_unit);
        number= (TextView)findViewById(R.id.feedbacks_item_number);
        plan_supply_number = (TextView)findViewById(R.id.feedbacks_item_plan_supply_number);
        plan_ship_day = (TextView)findViewById(R.id.feedbacks_item_plan_ship_day);
        plan_delivery_day = (TextView)findViewById(R.id.feedbacks_item_plan_delivery_day);
        order_generate_day = (TextView)findViewById(R.id.feedbacks_item_order_generate_day);
        specified_delivery = (TextView)findViewById(R.id.feedbacks_item_specified_delivery);

        company_code.setText(getIntent().getExtras().getString("company_code"));
        order_id.setText(getIntent().getExtras().getString("order_id"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        number.setText(getIntent().getExtras().getString("number"));
        order_unit.setText(getIntent().getExtras().getString("order_unit"));
        plan_supply_number.setText(getIntent().getExtras().getString("plan_supply_number"));
        plan_ship_day.setText(getIntent().getExtras().getString("plan_ship_day"));
        plan_delivery_day.setText(getIntent().getExtras().getString("plan_delivery_day"));
        order_generate_day.setText(getIntent().getExtras().getString("order_generate_day"));
        specified_delivery.setText(getIntent().getExtras().getString("specified_delivery"));

        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderFeedbackDetailActivity.this.finish();
            }
        });

    }
}
