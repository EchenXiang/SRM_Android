package com.example.ct.srm_android.Logistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;
public class DeliveryDetailActivity extends AppCompatActivity {
    private TextView sgoods_entname;
    private TextView sgoods_number;
    private TextView sgoods_logistics_number;
    private TextView sgoods_sdate;
    private TextView sgoods_plan_date;
    private TextView sgoods_sender;
    private TextView sgoods_receiver;
    private TextView sgoods_re_tele;
    private TextView sgoods_re_addr;
    private TextView sgoods_status;

    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        sgoods_entname = (TextView)findViewById(R.id.delivery_item_sgoods_entname);
        sgoods_number = (TextView)findViewById(R.id.delivery_item_sgoods_number);
        sgoods_logistics_number = (TextView)findViewById(R.id.delivery_item_sgoods_logistics_number);

        sgoods_sdate= (TextView)findViewById(R.id.delivery_item_sgoods_date);;
        sgoods_plan_date= (TextView)findViewById(R.id.delivery_item_sgoods_plan_date);;
        sgoods_sender= (TextView)findViewById(R.id.delivery_item_sgoods_sender);;
        sgoods_receiver= (TextView)findViewById(R.id.delivery_item_sgoods_receiver);;
        sgoods_re_tele= (TextView)findViewById(R.id.delivery_item_sgoods_re_tele);;
        sgoods_re_addr= (TextView)findViewById(R.id.delivery_item_sgoods_re_addr);;
        sgoods_status = (TextView)findViewById(R.id.delivery_item_sgoods_status);


        sgoods_entname.setText(getIntent().getExtras().getString("sgoods_entname"));
        sgoods_number.setText(getIntent().getExtras().getString("sgoods_number"));
        sgoods_logistics_number.setText(getIntent().getExtras().getString("sgoods_logistics_number"));
        sgoods_sdate.setText(getIntent().getExtras().getString("sgoods_sdate"));
        sgoods_plan_date.setText(getIntent().getExtras().getString("sgoods_plan_date"));
        sgoods_sender.setText(getIntent().getExtras().getString("sgoods_sender"));
        sgoods_receiver.setText(getIntent().getExtras().getString("sgoods_receiver"));
        sgoods_re_tele.setText(getIntent().getExtras().getString("sgoods_re_tele"));
        sgoods_re_addr.setText(getIntent().getExtras().getString("sgoods_re_addr"));
        sgoods_status.setText(getIntent().getExtras().getString("sgoods_status"));


        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryDetailActivity.this.finish();
            }
        });
    }
}








