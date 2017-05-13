package com.example.ct.srm_android;

import android.content.Context;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;




public class stocks extends Activity {
    private Button stocks_find;
    private EditText et1,et2,et3;
    Intent intent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks);
        stocks_find = (Button) findViewById(R.id.stocks_find);
        et1 = (EditText)findViewById(R.id.et1) ;
        et2 = (EditText)findViewById(R.id.et2) ;
        et3 = (EditText)findViewById(R.id.et3) ;


        stocks_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("host:"+et1.getText().toString());
                System.out.println("material_number:"+et2.getText().toString());
                System.out.println("material_name:"+et3.getText().toString());

                intent.putExtra("host",et1.getText().toString());
                intent.putExtra("material_number",et2.getText().toString());
                intent.putExtra("material_name",et3.getText().toString());


                intent.setClass(stocks.this, stocks_listView.class);
                    startActivity(intent);

            }
        });

    }
}
