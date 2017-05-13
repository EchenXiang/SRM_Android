package com.example.ct.srm_android.Notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ct.srm_android.R;

public class Notice_XQActivity extends AppCompatActivity {
    private TextView title;
    private TextView sender_ent;
    private TextView date;
    private TextView content;
    private ImageView notice_xq_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_xq);
        notice_xq_back= (ImageView) findViewById(R.id.notice_xq_back);
        notice_xq_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}
        });

        title = (TextView) findViewById(R.id.notice_xq_title);
        sender_ent = (TextView) findViewById(R.id.notice_xq_sender_ent);
        date = (TextView) findViewById(R.id.notice_xq__date);
        content = (TextView) findViewById(R.id.notice_xq_content);


        title.setText(getIntent().getExtras().getString("title"));
        sender_ent.setText(getIntent().getExtras().getString("sender_ent"));
        date.setText(getIntent().getExtras().getString("date"));
        content.setText(getIntent().getExtras().getString("content"));

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        //设置返回数据
        Notice_XQActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        Notice_XQActivity.this.finish();
    }

}

