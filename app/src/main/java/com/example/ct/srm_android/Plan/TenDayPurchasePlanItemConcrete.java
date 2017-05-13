package com.example.ct.srm_android.Plan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;

/**
 * Created by acer on 2016/7/15.
 */
public class TenDayPurchasePlanItemConcrete extends Activity {
    private ImageView turnback;
    private TextView provider;
    private TextView material_number;
    private TextView material_name;
    private TextView unit;
    private TextView release_decad;
    private TextView day_stock;
    private TextView decad_require;
    private TextView execetive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenday_purchase_plan_item_concrete);
        turnback=(ImageView)findViewById(R.id.year_turnback);

        turnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
//                startActivity(intent);
                onBackPressed();
                //finish();

            }
        });

        provider = (TextView)findViewById(R.id.tenday_provider);
        material_number  = (TextView)findViewById(R.id.tenday_material_number);
        material_name = (TextView)findViewById(R.id.tenday_material_name);
        release_decad = (TextView)findViewById(R.id.release_decad);
        unit = (TextView)findViewById(R.id.tenday_unit);
        day_stock = (TextView)findViewById(R.id.tenday_day_stock);
        decad_require = (TextView)findViewById(R.id.decad_require);
        execetive = (TextView)findViewById(R.id.tenday_execetive);

        provider.setText(getIntent().getExtras().getString("provider"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        release_decad.setText(getIntent().getExtras().getString("release_decad"));
        unit.setText(getIntent().getExtras().getString("unit"));
        day_stock.setText(getIntent().getExtras().getString("day_stock"));
        decad_require.setText(getIntent().getExtras().getString("decad_require"));
        execetive.setText(getIntent().getExtras().getString("execetive"));



    }
}

