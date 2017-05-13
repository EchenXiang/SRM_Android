package com.example.ct.srm_android.Performance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ct.srm_android.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Adapter.ImageAdapter;

public class PerformanceActivity extends AppCompatActivity {
    private List<View> views_graph;
    private List<View> views_info;
    private ViewPager viewPager_info;
    private ViewPager viewPager_graph;
    private ImageAdapter imageAdapter;
    private ImageHandler handler;

    private TextView title_matter;
    private TextView title_system;
    private TextView title_hand;
    private ImageView choose_note;
//    private HorizontalScrollView horizonScrollView;
//    private List<String> titles;
//    private PagerTabStrip tabStrip;

    private int currentItem=0;//graph
    private int currentIndex=1;//info
    private ImageView[] nodes;
    private int[] ids={R.id.node_First,R.id.node_Second,R.id.node_Third};
    private boolean stop=false;

    private int ScreenWidth;

    private Button queryMatterButton;
    private Button querySystemScoreButton;
    private Button queryHandScoreButton;

//    属性的get和set
    public ImageHandler getHandler() {
        return handler;
    }
    public ViewPager getViewPager_graph() {
        return viewPager_graph;
    }
    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }
    public int getCurrentItem() {
        return currentItem;
    }
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);
        initGraphView();
        initNodes();
        initInfoView();//view+button
        initListener();
    }

    public void initGraphView(){
        LayoutInflater inflater=LayoutInflater.from(this);
        views_graph=new ArrayList<View>();
        views_graph.add( inflater.inflate(R.layout.graph_first, null));
        views_graph.add(inflater.inflate(R.layout.graph_second, null));
        views_graph.add( inflater.inflate(R.layout.graph_third, null));
        imageAdapter=new ImageAdapter(views_graph);
        viewPager_graph=(ViewPager)findViewById(R.id.graph_viewpager);
        viewPager_graph.setAdapter(imageAdapter);

        handler=new ImageHandler(new WeakReference<PerformanceActivity>(this));

        viewPager_graph.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (stop) {
//                    Toast.makeText(PerformanceActivity.this, "用户滑动", Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < ids.length; i++) {
                        if (position % ids.length == i) {
                            nodes[i].setImageResource(R.drawable.node_selected);
                        } else {
                            nodes[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                    handler.sendEmptyMessage(ImageHandler.MSG_BREAK_SILENT);
                }
               else{
                    if(position==currentItem)
                        nodes[currentItem % ids.length].setImageResource(R.drawable.node_selected);
                    for (int i = 0; i < ids.length; i++) {
                        if (i != currentItem % ids.length) {
                        nodes[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                }
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, position, 0));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
//        viewPager_graph.setCurrentItem(Integer.MAX_VALUE/2);//默认在中间，使用户看不到边界???
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);//开始轮播效果
    }
    public void initNodes(){
       nodes = new ImageView[views_graph.size()];
       for (int i = 0; i < views_graph.size(); i++) {
           nodes[i] = (ImageView) findViewById(ids[i]);
       }
   }

    public void initInfoView(){
        title_matter=(TextView)findViewById(R.id.title_matter);
        title_system=(TextView)findViewById(R.id.title_system);
        title_hand=(TextView)findViewById(R.id.title_hand);
        WindowManager wm =  this.getWindowManager();
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        title_matter.setWidth(ScreenWidth/3);
        title_system.setWidth(ScreenWidth/3);
        title_hand.setWidth(ScreenWidth/3);

        choose_note=(ImageView)findViewById(R.id.choose_note);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ScreenWidth/3,10);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        choose_note.setLayoutParams(layoutParams);
//        horizonScrollView=(HorizontalScrollView)findViewById(R.id.scrollView);

        viewPager_info=(ViewPager)findViewById(R.id.info_viewpager);
        views_info=new ArrayList<View>();
        views_info.add(getLayoutInflater().inflate(R.layout.null_info, null));
        views_info.add(getLayoutInflater().inflate(R.layout.query_matter, null));
        views_info.add(getLayoutInflater().inflate(R.layout.system_score, null));
        views_info.add(getLayoutInflater().inflate(R.layout.hand_score, null));
        views_info.add(getLayoutInflater().inflate(R.layout.null_info, null));

        viewPager_info.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views_info.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView(views_info.get(position));
            }
            //每次滑动的时候生成的组件
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager) container).addView(views_info.get(position));
                return views_info.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return  view==object;
            }
        });
        viewPager_info.setCurrentItem(1);
    }
    public void initListener(){
        title_matter.setOnClickListener(new MyClickListener(1));
        title_system.setOnClickListener(new MyClickListener(2));
        title_hand.setOnClickListener(new MyClickListener(3));
        viewPager_info.addOnPageChangeListener(new MyPageChangeListener());
    }

    public void MyOnClick(final View v) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        Date myDate=new Date();
        d.setTime(myDate);

        int year=d.get(Calendar.YEAR);
        int month=d.get(Calendar.MONTH);
        int day=d.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker=new DatePickerDialog(PerformanceActivity.this, new DatePickerDialog.OnDateSetListener() {
            int i = 1;
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                TextView editBegin=null;
                TextView editFinish=null;
                if(v.getId()==R.id.beginCalendar_matter||v.getId()==R.id.finishCalendar_matter) {
                    editBegin= (TextView) views_info.get(1).findViewById(R.id.beginTime_matter);
                    editFinish=(TextView) views_info.get(1).findViewById(R.id.finishTime_matter);
                    if(v.getId()==R.id.beginCalendar_matter) {
                        if (!VarifyDate(year + "-" + (monthOfYear + i) + "-" + dayOfMonth, editFinish.getText().toString())){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editBegin.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editBegin.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                    else if(v.getId()==R.id.finishCalendar_matter) {
                        if (!VarifyDate(editBegin.getText().toString(),year + "-" + (monthOfYear + i) + "-" + dayOfMonth)){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editFinish.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editFinish.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                }
                else if(v.getId()==R.id.beginCalendar_system||v.getId()==R.id.finishCalendar_system) {
                    editBegin= (TextView) views_info.get(2).findViewById(R.id.beginTime_system);
                    editFinish=(TextView) views_info.get(2).findViewById(R.id.finishTime_system);
                    if(v.getId()==R.id.beginCalendar_system) {
                        if (!VarifyDate(year + "-" + (monthOfYear + i) + "-" + dayOfMonth, editFinish.getText().toString())){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editBegin.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editBegin.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                    else if(v.getId()==R.id.finishCalendar_system) {
                        if (!VarifyDate(editBegin.getText().toString(),year + "-" + (monthOfYear + i) + "-" + dayOfMonth)){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editFinish.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editFinish.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                }
                else if(v.getId()==R.id.beginCalendar_hand||v.getId()==R.id.finishCalendar_hand) {
                    editBegin= (TextView) views_info.get(3).findViewById(R.id.beginTime_hand);
                    editFinish=(TextView) views_info.get(3).findViewById(R.id.finishTime_hand);
                    if(v.getId()==R.id.beginCalendar_hand) {
                        if (!VarifyDate(year + "-" + (monthOfYear + i) + "-" + dayOfMonth, editFinish.getText().toString())){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editBegin.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editBegin.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                    else if(v.getId()==R.id.finishCalendar_hand) {
                        if (!VarifyDate(editBegin.getText().toString(),year + "-" + (monthOfYear + i) + "-" + dayOfMonth)){
                            Toast.makeText(PerformanceActivity.this, "日期设置有误", Toast.LENGTH_SHORT).show();
                            editFinish.setText(null);
                        }
                        else{
                            String setMonth;
                            String setDay;
                            if(monthOfYear+i<10)
                                setMonth="0"+(monthOfYear+i);
                            else setMonth=""+(monthOfYear+i);
                            if(dayOfMonth<10)
                                setDay="0"+dayOfMonth;
                            else setDay=""+dayOfMonth;
                            editFinish.setText(year + "-" + setMonth+ "-" + setDay);
                        }
                    }
                }
            }
        }, year, month, day);
        datePicker.show();
    }
    public boolean VarifyDate(String beginTime,String finishTime) {
        if ( beginTime.equals("") || finishTime.equals("")) {
            return true;
        }
        else {
            String[] begin = beginTime.split("-");
            String[] finish = finishTime.split("-");
            for (int i = 0; i < begin.length; i++) {
                if (Integer.parseInt(begin[i]) < Integer.parseInt(finish[i]))
                    return true;
                else if(Integer.parseInt(begin[i]) > Integer.parseInt(finish[i]))
                    return false;
            }
        }
        return true;
    }

    public void QueryClick(View v){
        Intent intent=new Intent(PerformanceActivity.this,InfoListActivity.class);
        Bundle bundle=new Bundle();
        if(v.getId()==R.id.query_matter_button){
            bundle.putInt("query",1);
            bundle.putString("order_number",((EditText)findViewById(R.id.orderNum_matter)).getText().toString());
            bundle.putString("material_number",((EditText)findViewById(R.id.matterNum_matter)).getText().toString());
            bundle.putString("host",((EditText)findViewById(R.id.factoryName_matter)).getText().toString());
            bundle.putString("purchase_number",((EditText)findViewById(R.id.groupNum_matter)).getText().toString());
            bundle.putString("start_time",((TextView)findViewById(R.id.beginTime_matter)).getText().toString());
            bundle.putString("end_time",((TextView)findViewById(R.id.finishTime_matter)).getText().toString());
        }
        else if(v.getId()==R.id.query_systemScore_button){
            bundle.putInt("query",2);
            bundle.putString("host",((EditText)findViewById(R.id.factoryName_system)).getText().toString());
            bundle.putString("host_number",((EditText)findViewById(R.id.factoryNum_system)).getText().toString());
            bundle.putString("start_time",((TextView)findViewById(R.id.beginCalendar_system)).getText().toString());
            bundle.putString("end_time",((TextView)findViewById(R.id.finishCalendar_system)).getText().toString());
        }

        else if(v.getId()==R.id.query_handScore_button){
            bundle.putInt("query",3);
            bundle.putString("host",((EditText)findViewById(R.id.factoryName_hand)).getText().toString());
            bundle.putString("start_time",((TextView)findViewById(R.id.beginCalendar_hand)).getText().toString());
            bundle.putString("end_time",((TextView)findViewById(R.id.finishCalendar_hand)).getText().toString());
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class MyClickListener implements View.OnClickListener {
        private int index=0;
        public MyClickListener(int index){
            this.index=index;
        }
        @Override
        public void onClick(View v) {
            viewPager_info.setCurrentItem(index);
        }
    }
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if(arg0==0) viewPager_info.setCurrentItem(1);
            else if(arg0==4) viewPager_info.setCurrentItem(3);
            else{
            TextView[] titles={title_matter,title_system,title_hand};
            for(int i=0;i<views_info.size()-2;i++){
                if(viewPager_info.getCurrentItem()==i+1){
                    titles[i].setTextColor(getResources().getColor(R.color.black));
                }
                else
                    titles[i].setTextColor(getResources().getColor(R.color.gray));
            }

            AnimationSet animationSet=new AnimationSet(true);
            TranslateAnimation transelateAnimation=new TranslateAnimation(ScreenWidth/3*(viewPager_info.getCurrentItem()-1),ScreenWidth/3*(arg0-1),0,0);
            currentIndex=arg0;
            viewPager_info.setCurrentItem(currentIndex);
            animationSet.addAnimation(transelateAnimation);
            animationSet.setDuration(300);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            choose_note.startAnimation(animationSet);
        }}
    }

}
