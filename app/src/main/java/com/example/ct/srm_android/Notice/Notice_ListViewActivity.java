package com.example.ct.srm_android.Notice;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import Tools.*;
import com.example.ct.srm_android.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Object.Notice;
import Tools.JsonTools;

public class Notice_ListViewActivity extends Activity  {
    private List<Map<String,Object>> testData;
    List<Notice> notices ;
    List<Notice> notice ;
    private static final int NOTICE_SUCCESS = 1;
    private static final int NOTICE_NULL = 0;
    private ListView notice_listView;
    private ProgressBar notice_moredata_pb;
    private Button notice_moredata_load;
    private MyAdapter adapter;
    String result;
    int total;
    private ImageView activity_list_view_back;

    private View moreView;
    private Handler handler;
    private int MaxDateNum= total;// 设置一个最大的数据条数，超过即不再加载
    private int visibleLastIndex = 0;   //最后的可视项索引
    private int visibleItemCount; // 当前窗口可见项总数

    private String sender_ent;
    private String title;
    private String time_from;
    private String time_to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list_view);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        sender_ent = getIntent().getExtras().getString("sender_ent");
        title = getIntent().getExtras().getString("title");
        time_from = getIntent().getExtras().getString("time_from");
        time_to = getIntent().getExtras().getString("time_to");



        // 实例化底部布局
        moreView = getLayoutInflater().inflate(R.layout.notice_moredata, null);
        notice_moredata_load = (Button) moreView.findViewById(R.id.notice_moredata_load);
        notice_moredata_pb = (ProgressBar) moreView.findViewById(R.id.notice_moredata_pb);
        activity_list_view_back = (ImageView)findViewById(R.id.activity_list_view_back);
        handler = new Handler();

        activity_list_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {onBackPressed();}
        });

        //子线程创建
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://211.87.227.120:3000/notice/selectInformation";//通知查询接口
                Map<String, String> params = new HashMap<String, String>();
                /**通知公告查询参数*/
                params.put("pageSize", "10");
                params.put("currentPage", "1");
                params.put("receiver_ent", "丹阳市中远车灯有限公司");
                params.put("sender_ent", sender_ent);
                params.put("title", title);
                params.put("time_from", time_from);
                params.put("time_to", time_to);
                params.put("role", "供应商");
                try {
                    //调用写好的工具类Notice_HttpRequest来进行GET操作
                    result = Notice_HttpRequest.sendGetRequest(url, params, "UTF-8");
                    notices = JsonTools.getNotices("data", result);
                    System.out.println("result:"+ result);
                    JSONObject jsonObject = new JSONObject(result);
                    String s1 = jsonObject.getString("total");
                    total = Integer.parseInt(s1);
                    if(total != 0) {
                        Message message = mHandler.obtainMessage();
                        message.what = 1;
                        mHandler.sendMessage(message);    //网络操作成功后 子线程给Handler发mes
                    }
                    else{
                        Message message = mHandler.obtainMessage();
                        message.what = 0;
                        mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                }
            }
        }).start();


        notice_moredata_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice_moredata_pb.setVisibility(View.VISIBLE);// 将进度条可见
                notice_moredata_load.setVisibility(View.GONE);// 按钮不可见
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notice_moredata_loadMoreDate();// 加载更多数据
                        notice_moredata_load.setVisibility(View.VISIBLE);
                        notice_moredata_pb.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();// 通知notice_listView刷新数据
                    }
                }, 2000);
            }
        });

        notice_listView = (ListView) findViewById(R.id.notice_listview);
        // 加上底部View，注意要放在setAdapter方法前
        notice_listView.addFooterView(moreView);
        // 绑定监听器
       /*notice_listView.setOnScrollListener(this);*/
        adapter = new MyAdapter(getApplicationContext());
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        //设置返回数据
        Notice_ListViewActivity.this.setResult(RESULT_OK, intent);
        //关闭Activity
        Notice_ListViewActivity.this.finish();
    }




    //     根据服务器发送的返回数据，生成通知的数据
//    @return 返回的是供适配器绑定的数据
//
    private List<Map<String, Object>> buildData() {

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        for (int i = 0; i < notices.size(); i++) {
            map = new HashMap<String, Object>();
          /*  map.put("notice_listview_item_image", R.drawable.tongzhi);
            map.put("notice_listview_item_title",  notices.get(i).gettitle());*/
            map.put("notice_listview_item_date", notices.get(i).getdate());
            map.put("notice_listview_item_sender_ent", "来自企业"+notices.get(i).getsender_ent());
            map.put("notice_listview_item_notice_status", notices.get(i).getstatus());
            map.put("notice_listview_item_content", notices.get(i).getcontent());
            data.add(map);
        }
        return data;
    }

    private void notice_moredata_loadMoreDate() {
        int count = adapter.getCount();
        if (count + 5 <= notices.size()/*MaxDateNum*/) {
            // 每次加载5条
            for (int i = count + 1; i <= count + 5; i++) {
                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = new HashMap<String, Object>();
                /*map.put("notice_listview_item_image", R.drawable.tongzhi);
                map.put("notice_listview_item_title", notices.get(i).gettitle());*/
                map.put("notice_listview_item_date", notices.get(i).getdate());
                map.put("notice_listview_item_sender_ent", notices.get(i).getsender_ent());
                map.put("notice_listview_item_notice_status", notices.get(i).getstatus());
                map.put("notice_listview_item_content", notices.get(i).getcontent());
                data.add(map);
            }
        } else {
            // 数据已经不足5条
            for (int i = count + 1; i <= notices.size()/*MaxDateNum*/; i++) {
                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = new HashMap<String, Object>();
              /*  map.put("notice_listview_item_image", R.drawable.tongzhi);
                map.put("notice_listview_item_title", notices.get(i).gettitle());*/
                map.put("notice_listview_item_date", notices.get(i).getdate());
                map.put("notice_listview_item_sender_ent", notices.get(i).getsender_ent());
                map.put("notice_listview_item_notice_status", notices.get(i).getstatus());
                map.put("notice_listview_item_content", notices.get(i).getcontent());

                data.add(map);
            }
        }
    }

    //这个ListViewAdapter是我们自定义适配器，它继承自BaseAdapter，
    // 实例化此适配器需要一个Context对象来获取LayoutInflater实例和一个集合对象来充当适配器的数据集；
    // 在getView方法中我们填充list_item.xml布局文件，完成列表每一项的数据显示；
    // addItem方法用来在加载数据时向数据集中添加新数据;addItem方法用来在加载数据时向数据集中添加新数据。
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private Context mContext = null;

        public MyAdapter(Context context) {
            mContext = context;
            this.inflater = LayoutInflater.from(context);
        }

        //这里的getCount方法是程序在加载显示到ui上时就要先读取的，这里获得的值决定了listview显示多少行
        @Override
        public int getCount() {
            return testData.size();
        }

        //根据ListView所在位置返回View
        @Override
        public Object getItem(int position) {
            //TODO Auto-generated method stub
            return testData.get(position);
        }

        //根据ListView位置得到数据源集合中的Id
        @Override
        public long getItemId(int position) {
            return position;
        }

        //决定listview界面的样式的
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Holder holder;          //自定义一个Holder 把一个Item中的View绑定起来
            View view;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.notice_listview_item, null);
                holder = new Holder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (Holder) view.getTag();
            }
            //这里testData.get(position).get("title1"))，其实就是从list集合(testData)中取出对应索引的map，然后再根据键值对取值
            holder.notice_listview_item_sender_ent.setText((String) testData.get(position).get("notice_listview_item_sender_ent"));
            holder.notice_listview_item_date.setText((String) testData.get(position).get("notice_listview_item_date"));
            holder.notice_listview_item_notice_status.setText((String) testData.get(position).get("notice_listview_item_notice_status"));
            holder.notice_listview_item_content.setText((String) testData.get(position).get("notice_listview_item_content"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", notices.get(position).gettitle());
                    intent.putExtra("sender_ent", notices.get(position).getsender_ent());
                    intent.putExtra("date", notices.get(position).getdate());
                    intent.putExtra("content", notices.get(position).getcontent());
                    intent.setClass(Notice_ListViewActivity.this, Notice_XQActivity.class);
                    startActivity(intent);
                }
            });
            String s = holder.notice_listview_item_notice_status.getText().toString();
            if (holder.notice_listview_item_notice_status.getText().toString().equals("2")) {
                holder.notice_listview_item_notice_status.setText("未查看");
                holder.notice_listview_item_notice_status.setTextColor(Color.RED);
            } else {
                holder.notice_listview_item_notice_status.setText("已查看");
                holder.notice_listview_item_notice_status.setTextColor(Color.BLACK);
            }
            return view;
        }

        public class Holder {
            /* public ImageView notice_listview_item_image;
             public TextView notice_listview_item_title;*/
            public TextView notice_listview_item_date;
            public TextView notice_listview_item_sender_ent;
            public TextView notice_listview_item_notice_status;
            public TextView notice_listview_item_content;
            public TextView notice_moredata_load;

            public Holder(View view) {
               /* notice_listview_item_image = (ImageView) view.findViewById(R.id.notice_listview_item_image);
                notice_listview_item_title = (TextView) view.findViewById(R.id.notice_listview_item_title);*/
                notice_listview_item_date = (TextView) view.findViewById(R.id.notice_listview_item_date);
                notice_listview_item_sender_ent = (TextView) view.findViewById(R.id.notice_listview_item_sender_ent);
                notice_listview_item_notice_status = (TextView) view.findViewById(R.id.notice_listview_item_notice_status);
                notice_listview_item_content = (TextView) view.findViewById(R.id.notice_listview_item_content);

                notice_moredata_load = (TextView) view.findViewById(R.id.notice_moredata_load);

              /*  TextPaint textPaint = notice_listview_item_title.getPaint();
                textPaint.setFakeBoldText(true);
*/
            }
        }
    }

    /*     *
           * UI界面的Handler 可以接收子线程的消息 并对UI界面进行控制和操作
          * 子线程和UI线程的桥梁
          */
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {//此方法在ui线程运行
            System.out.println("msg0" +msg);
            switch (msg.what) {
                case NOTICE_SUCCESS:
                    System.out.println("msg1" +msg);
                    testData = buildData();
                    MyAdapter adapter = new MyAdapter(Notice_ListViewActivity.this);
                    notice_listView = (ListView) findViewById(R.id.notice_listview);
                    notice_listView.setAdapter(adapter);
                    break;
                case NOTICE_NULL:
                    System.out.println("msg" +msg);
                    AlertDialog.Builder builder = new  AlertDialog.Builder(Notice_ListViewActivity.this)
                            .setTitle("提示")
                            .setMessage("没有相应的数据！");
                    builder.setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    arg0.dismiss();
                                }
                            });
                    builder.create().show();
                    break;
            }

        }
    };
}