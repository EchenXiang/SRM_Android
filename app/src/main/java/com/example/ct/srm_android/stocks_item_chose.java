package com.example.ct.srm_android;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;



public class stocks_item_chose extends Activity {

    private ImageView btnReturn;
    private TextView host;
    private TextView host_number;
    private TextView charge_number;
    private TextView warehouse;
    private TextView material_name;
    private TextView specification;
    private TextView amount;
    private TextView unit;
    private TextView material_number;
    private TextView storage_time;
    private TextView remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks_item_chose);
        btnReturn = (ImageView) findViewById(R.id.btn_left);

        host = (TextView)findViewById(R.id.stocks_item_chose_title);
        host_number = (TextView)findViewById(R.id.stocks_item_chose_host_number);
        charge_number = (TextView)findViewById(R.id.stocks_item_chose_charge_number);
        warehouse = (TextView)findViewById(R.id.stocks_item_chose_warehouse);
        material_name = (TextView)findViewById(R.id.stocks_item_chose_material_name);
        specification = (TextView)findViewById(R.id.stocks_item_chose_specification);
        amount = (TextView)findViewById(R.id.stocks_item_chose_amount);
        unit = (TextView)findViewById(R.id.stocks_item_chose_unit);
        material_number = (TextView)findViewById(R.id.stocks_item_chose_material_number);
        storage_time = (TextView)findViewById(R.id.stocks_item_chose_storage_time);
        remarks = (TextView)findViewById(R.id.stocks_item_chose_remarks);

        host.setText(getIntent().getExtras().getString("host"));
        host_number.setText(getIntent().getExtras().getString("host_number"));
        charge_number.setText(getIntent().getExtras().getString("charge_number"));
        warehouse.setText(getIntent().getExtras().getString("warehouse"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        specification.setText(getIntent().getExtras().getString("specification"));
        amount.setText(getIntent().getExtras().getString("amount"));
        unit.setText(getIntent().getExtras().getString("unit"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        storage_time.setText(getIntent().getExtras().getString("storage_time"));
        remarks.setText(getIntent().getExtras().getString("remarks"));

        btnReturn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {onBackPressed();}
        });
    }
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        //设置返回数据
        stocks_item_chose.this.setResult(RESULT_OK, intent);
        //关闭Activity
        stocks_item_chose.this.finish();
    }

}
