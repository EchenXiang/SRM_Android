package com.example.ct.srm_android.Plan;

/**
 * Created by Administrator on 2016/7/15.
 */
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import com.example.ct.srm_android.R;

public class YearPurchasePlanItemConcrete extends Activity {

    private ImageView turnback;
    private TextView provider;
    private TextView material_number;
    private TextView material_name;
    private TextView unit;
    private TextView release_year;
    private TextView day_stock;
    private TextView year_require;
    private TextView execetive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.year_purchase_plan_item_concrete);
//

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

        provider = (TextView)findViewById(R.id.year_provider);
        material_number  = (TextView)findViewById(R.id.year_material_number);
        material_name = (TextView)findViewById(R.id.year_material_name);
        release_year = (TextView)findViewById(R.id.year_release_year);
        unit = (TextView)findViewById(R.id.year_unit);
        day_stock = (TextView)findViewById(R.id.year_day_stock);
        year_require = (TextView)findViewById(R.id.year_require);
        execetive = (TextView)findViewById(R.id.year_execetive);

        provider.setText(getIntent().getExtras().getString("provider"));
        material_number.setText(getIntent().getExtras().getString("material_number"));
        material_name.setText(getIntent().getExtras().getString("material_name"));
        release_year.setText(getIntent().getExtras().getString("release_year"));
        unit.setText(getIntent().getExtras().getString("unit"));
        day_stock.setText(getIntent().getExtras().getString("day_stock"));
        year_require.setText(getIntent().getExtras().getString("year_require"));
        execetive.setText(getIntent().getExtras().getString("execetive"));

    }
}

