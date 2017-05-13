package com.example.ct.srm_android.Plan;

/**
 * Created by Administrator on 2016/7/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;

public class MonthPurchasePlanItemConcrete extends Activity {
    private ImageView turnback;
    private TextView provider;
    private TextView material_number;
    private TextView material_name;
    private TextView unit;
    private TextView release_month;
    private TextView day_stock;
    private TextView month_require;
    private TextView execetive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_purchase_plan_item_concrete);
        turnback=(ImageView)findViewById(R.id.month_turnback);

        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                startActivity(intent);
                onBackPressed();
                //finish();

            }
        });

        provider = (TextView)findViewById(R.id.month_provider);
        material_number  = (TextView)findViewById(R.id.month_material_number);
        material_name = (TextView)findViewById(R.id.month_material_name);
        release_month = (TextView)findViewById(R.id.month_release_date);
        unit = (TextView)findViewById(R.id.month_unit);
        day_stock = (TextView)findViewById(R.id.month_day_stock);
        month_require = (TextView)findViewById(R.id.year_require);
        execetive = (TextView)findViewById(R.id.month_execetive);

        provider.setText(getIntent().getExtras().getString("provider"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        release_month.setText(getIntent().getExtras().getString("release_month"));
        unit.setText(getIntent().getExtras().getString("unit"));
        day_stock.setText(getIntent().getExtras().getString("day_stock"));
        month_require.setText(getIntent().getExtras().getString("month_require"));
        execetive.setText(getIntent().getExtras().getString("execetive"));



    }
}

