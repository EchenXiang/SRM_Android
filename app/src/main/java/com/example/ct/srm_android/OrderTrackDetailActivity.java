package com.example.ct.srm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderTrackDetailActivity extends AppCompatActivity {
    private TextView order_status;
    private TextView company_code;
    private TextView provider_name;
    private TextView order_id;
    private TextView material_number;
    private TextView material_name;
    private TextView order_generate_day;
    private TextView specified_delivery;
    private TextView order_unit;
    private TextView number;
    private TextView had_ship_number;
    private TextView number2;
    private TextView number3;
    private TextView delivery_complete;
    private TextView company_name;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_track_detail);
        company_name = (TextView)findViewById(R.id.tracks_item_company_name);
        order_id = (TextView)findViewById(R.id.tracks_item_order_id);
        material_number = (TextView)findViewById(R.id.tracks_item_material_number);
        material_name = (TextView)findViewById(R.id.tracks_item_material_name);
        order_generate_day = (TextView)findViewById(R.id.tracks_item_order_generate_day);
        specified_delivery = (TextView)findViewById(R.id.tracks_item_specified_delivery);
        number = (TextView)findViewById(R.id.tracks_item_number);
        had_ship_number = (TextView)findViewById(R.id.tracks_item_had_ship_number);
        number2 = (TextView)findViewById(R.id.tracks_item_number2);
        number3 = (TextView)findViewById(R.id.tracks_item_number3);
        delivery_complete = (TextView)findViewById(R.id.tracks_item_delivery_complete);


        company_name.setText(getIntent().getExtras().getString("company_code"));
        order_id.setText(getIntent().getExtras().getString("order_id"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        order_generate_day.setText(getIntent().getExtras().getString("order_generate_day"));
        specified_delivery.setText(getIntent().getExtras().getString("specified_delivery"));
        number.setText(getIntent().getExtras().getString("number"));
        had_ship_number.setText(getIntent().getExtras().getString("had_ship_number"));
        number2.setText(getIntent().getExtras().getString("number"));
        number3.setText(getIntent().getExtras().getString("had_ship_number"));
        delivery_complete.setText(getIntent().getExtras().getString("order_status"));

        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderTrackDetailActivity.this.finish();
            }
        });

    }
}
