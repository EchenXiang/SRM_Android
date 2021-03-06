package com.example.ct.srm_android.Plan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;

/**
 * Created by 贾子夏 on 2016/7/15.
 */
public class DoubleWeekPurchasePlanItemConcrete extends Activity {
    private ImageView turnback;
    private TextView provider;
    private TextView material_number;
    private TextView material_name;
    private TextView unit;
    private TextView release_week;
    private TextView day_stock;
    private TextView first_week_require;
    private TextView second_week_require;

    private TextView execetive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.double_week_purchase_plan_item_concrete);
        turnback=(ImageView)findViewById(R.id.double_week_turnback);

        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                startActivity(intent);
                onBackPressed();
                //finish();

            }
        });

        provider = (TextView)findViewById(R.id.double_week_provider);
        material_number  = (TextView)findViewById(R.id.double_week_material_number);
        material_name = (TextView)findViewById(R.id.double_week_material_name);
        release_week = (TextView)findViewById(R.id.double_week_release_date);
        unit = (TextView)findViewById(R.id.double_week_unit);
        day_stock = (TextView)findViewById(R.id.double_week_day_stock);
        first_week_require = (TextView)findViewById(R.id.first_week_require);
        second_week_require = (TextView)findViewById(R.id.second_week_require);
        execetive = (TextView)findViewById(R.id.double_week_execetive);

        provider.setText(getIntent().getExtras().getString("provider"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        release_week.setText(getIntent().getExtras().getString("release_week"));
        unit.setText(getIntent().getExtras().getString("unit"));
        day_stock.setText(getIntent().getExtras().getString("day_stock"));
        first_week_require.setText(getIntent().getExtras().getString("first_week_require"));
        second_week_require.setText(getIntent().getExtras().getString("second_week_require"));
        execetive.setText(getIntent().getExtras().getString("execetive"));



    }
}