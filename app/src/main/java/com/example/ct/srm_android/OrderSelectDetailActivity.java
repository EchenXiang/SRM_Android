package com.example.ct.srm_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderSelectDetailActivity extends AppCompatActivity {
    private TextView company_code;
    private TextView order_id;
    private TextView purchase_contact_name;
    private TextView contact;
    private TextView order_generate_day;
    private TextView provider_confirm;
    private TextView order_status;
    private TextView delivery_status;
    private TextView print_number;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select_detail);
        company_code = (TextView)findViewById(R.id.selects_item_company_name);
        order_id = (TextView)findViewById(R.id.selects_item_order_id);
        purchase_contact_name = (TextView)findViewById(R.id.selects_item_purchase_contact_name);
        contact = (TextView)findViewById(R.id.selects_item_contact);
        order_generate_day = (TextView)findViewById(R.id.selects_item_order_generate_day);
        provider_confirm = (TextView)findViewById(R.id.selects_item_provider_confirm);
        order_status = (TextView)findViewById(R.id.selects_item_order_status);
        print_number = (TextView)findViewById(R.id.selects_item_print_number);

        company_code.setText(getIntent().getExtras().getString("company_code"));
        order_id.setText(getIntent().getExtras().getString("order_id"));
        purchase_contact_name.setText(getIntent().getExtras().getString("purchase_contact_name"));
        contact.setText(getIntent().getExtras().getString("contact"));
        order_generate_day.setText(getIntent().getExtras().getString("order_generate_day"));
        provider_confirm.setText(getIntent().getExtras().getString("provider_confirm"));
        order_status.setText(getIntent().getExtras().getString("order_status"));
        print_number.setText(getIntent().getExtras().getString("print_number"));

        back = (ImageView)findViewById(R.id.order_listview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSelectDetailActivity.this.finish();
            }
        });

    }
}
