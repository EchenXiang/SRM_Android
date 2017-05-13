package com.example.ct.srm_android.Logistics;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.ct.srm_android.OrderSelectListviewActivity;
import com.example.ct.srm_android.R;
import Adapter.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyichen on 2017/3/23.
 */
public class Logistics_MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup Logistics_Inquire_RadioGroup;//RadioGroup
    private RadioButton Logistics_Inquire_RadioButton1;
    private RadioButton Logistics_Inquire_RadioButton2;
    private ImageView Logistics_Inquire_ImageView;//RadioButton上面蓝色滚动条
    private ImageView back;

    private float Logistics_Inquire_CurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//按钮的水平滚动控件
    private ViewPager Logistics_Inquire_ViewPager_layout;//下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(logistics_layout_delivery)

    private ViewPager Logistics_Inquire_ViewPager_picture;//上方可循环播放的图片
    private Logistics_ViewPagerAdapter vpAdapter;

    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.activity_logistics_main_imageview_dot1, R.id.activity_logistics_main_imageview_dot2, R.id.activity_logistics_main_imageview_dot3};
    private ImageHandler mhandler = new ImageHandler(new WeakReference<Logistics_MainActivity>(this));
    private static int currentItem = 0;//图片的当前页

    private static boolean opr = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_main);

        back = (ImageView)findViewById(R.id.logistics_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logistics_MainActivity.this.finish();
            }
        });

        initViews();
        initDots();
        iniController();
        iniListener();
        iniVariable ();
        Logistics_Inquire_RadioButton1.setChecked(true);
        Logistics_Inquire_ViewPager_layout.setCurrentItem(1);
        Logistics_Inquire_CurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();


    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one, null));
        views.add(inflater.inflate(R.layout.two, null));
        views.add(inflater.inflate(R.layout.three, null));
        vpAdapter = new Logistics_ViewPagerAdapter(views, this);
        Logistics_Inquire_ViewPager_picture = (ViewPager) findViewById(R.id.activity_logistics_main_viewpager_picture);
        Logistics_Inquire_ViewPager_picture.setAdapter(vpAdapter);
        Logistics_Inquire_ViewPager_picture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageSelected(int arg0) {
                if (opr) {
                    for (int i = 0; i < ids.length; i++) {
                        if (arg0 % ids.length == i) {
                            dots[i].setImageResource(R.drawable.node_select);
                        } else {
                            dots[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                } else {
                    if (arg0 == currentItem) {
                        dots[currentItem % ids.length].setImageResource(R.drawable.node_select);
                    }
                    for (int i = 0; i < ids.length; i++) {
                        if (i != currentItem % ids.length) {
                            dots[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                }
                mhandler.sendMessage(Message.obtain(mhandler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    //正在拖动页面时执行该语句
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    //未拖动页面时执行该语句
                    case ViewPager.SCROLL_STATE_IDLE:
                        mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
        mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }
    private void iniVariable() {
        mViews = new ArrayList<View>();
        mViews.add(getLayoutInflater().inflate(R.layout.logistics_layout_0, null));
        mViews.add(getLayoutInflater().inflate(R.layout.logistics_layout_delivery, null));
        mViews.add(getLayoutInflater().inflate(R.layout.logistics_layout_logistics, null));
        mViews.add(getLayoutInflater().inflate(R.layout.logistics_layout_0, null));

        Logistics_Inquire_ViewPager_layout.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
    }
    private void iniController() {
        Logistics_Inquire_RadioGroup = (RadioGroup) findViewById(R.id.activity_logistics_main_radiogroup);
        Logistics_Inquire_RadioButton1 = (RadioButton) findViewById(R.id.activity_logistics_main_radiobutton_delivery);
        Logistics_Inquire_RadioButton2 = (RadioButton) findViewById(R.id.activity_logistics_main_radiobutton_logistics);

        Logistics_Inquire_ImageView = (ImageView) findViewById(R.id.activity_logistics_main_imageview_line);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.activity_logistics_main_horizontalscrollview_radiobutton);
        Logistics_Inquire_ViewPager_layout = (ViewPager) findViewById(R.id.activity_logistics_main_viewpager_layout);
    }
    private void iniListener() {
        Logistics_Inquire_RadioGroup.setOnCheckedChangeListener(this);
        Logistics_Inquire_ViewPager_layout.addOnPageChangeListener(new MyPagerChangeListener());
    }


    private static class ImageHandler extends Handler {
        //请求更新显示的View
        protected static final int MSG_UPDATE_IMAGE = 1;
        //请求暂停轮播
        protected static final int MSG_KEEP_SILENT = 2;
        //请求恢复轮播
        protected static final int MSG_BREAK_SILENT = 3;
        //记录最新页号
        protected static final int MSG_PAGE_CHANGED = 4;
        //轮播间隔时间
        protected static final int MSG_DELAY = 3000;
        private WeakReference<Logistics_MainActivity> weakReference;
        protected ImageHandler(WeakReference<Logistics_MainActivity> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Logistics_MainActivity activity = weakReference.get();
            if (activity == null) {
                return;
            }
            if (activity.mhandler.hasMessages(MSG_UPDATE_IMAGE)) {
                if (currentItem > 0) {
                    activity.mhandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.Logistics_Inquire_ViewPager_picture.setCurrentItem(currentItem);
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    opr = true;
                    break;
                case MSG_BREAK_SILENT:
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    opr = false;
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    //RadioGroup点击CheckedChanged监听事件
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        if (checkedId == R.id.activity_logistics_main_radiobutton_delivery) {
            translateAnimation = new TranslateAnimation(Logistics_Inquire_CurrentCheckedRadioLeft, getResources().getDimension(R.dimen.r1), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            Logistics_Inquire_ImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
            Logistics_Inquire_ViewPager_layout.setCurrentItem(1);//让下方ViewPager跟随上面的HorizontalScrollView切换
        } else if (checkedId == R.id.activity_logistics_main_radiobutton_logistics) {
            translateAnimation = new TranslateAnimation(Logistics_Inquire_CurrentCheckedRadioLeft, getResources().getDimension(R.dimen.r2), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            Logistics_Inquire_ImageView.startAnimation(animationSet);
            Logistics_Inquire_ViewPager_layout.setCurrentItem(2);
        }
        Logistics_Inquire_CurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
        mHorizontalScrollView.smoothScrollTo((int) Logistics_Inquire_CurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.r2), 0);
    }
    //获得当前被选中的RadioButton距离左侧的距离
    private float getCurrentCheckedRadioLeft() {
        if (Logistics_Inquire_RadioButton1.isChecked()) {
            return getResources().getDimension(R.dimen.r1);
        } else if (Logistics_Inquire_RadioButton2.isChecked()) {
            return getResources().getDimension(R.dimen.r2);
        }
        return 0f;
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager) v).removeView(mViews.get(position));
        }
        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mViews.size();
        }
        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager) v).addView(mViews.get(position));
            return mViews.get(position);
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub
        }
        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub
        }

    }


    private class  MyPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }
        //滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            if (position == 0) {
                Logistics_Inquire_ViewPager_layout.setCurrentItem(1);
            } else if (position == 1) {
                Logistics_Inquire_RadioButton1.performClick();
            } else if (position == 2) {
                Logistics_Inquire_RadioButton2.performClick();
            }
        }
    }

    //获取日期
    public void onclick_logistics_layout_delivery_button_calendar1(View view){
        DatePickerDialog datePicker=new DatePickerDialog(Logistics_MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            int i = 1;
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                EditText edrili = (EditText)mViews.get(1).findViewById(R.id.logistics_layout_delivery_edittext_sgoods_sdate);
                edrili.setText(year+"-0"+(monthOfYear+i)+"-0"+dayOfMonth);
            }
        }, 2016, 6, 7);
        datePicker.show();
    }
    public void onclick_logistics_layout_delivery_button_calendar2(View view){
        DatePickerDialog datePicker=new DatePickerDialog(Logistics_MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            int i = 1;
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                EditText edrili = (EditText)mViews.get(1).findViewById(R.id.logistics_layout_delivery_edittext_sgoods_edate);
                edrili.setText(year+"-0"+(monthOfYear+i)+"-0"+dayOfMonth);
            }
        }, 2016, 6, 7);
        datePicker.show();
    }
    public void onclick_logistics_layout_logistics_button_calendar1(View view){
        DatePickerDialog datePicker=new DatePickerDialog(Logistics_MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            int i = 1;
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                EditText edrili = (EditText)mViews.get(2).findViewById(R.id.logistics_layout_logistics_edittext_logistics_sdate);
                edrili.setText(year+"-0"+(monthOfYear+i)+"-0"+dayOfMonth);
            }
        }, 2016, 6, 7);
        datePicker.show();
    }
    public void onclick_logistics_layout_logistics_button_calendar2(View view){
        DatePickerDialog datePicker=new DatePickerDialog(Logistics_MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            int i = 1;
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                EditText edrili = (EditText)mViews.get(2).findViewById(R.id.logistics_layout_logistics_edittext_logistics_edate);
                edrili.setText(year+"-0"+(monthOfYear+i)+"-0"+dayOfMonth);
            }
        }, 2016, 6, 7);
        datePicker.show();
    }

    public void SelectOnClick(View view) {
        switch (view.getId()) {
            case R.id.logistics_layout_delivery_button_inquire:
                EditText sgoods_entname = (EditText)findViewById(R.id.logistics_layout_delivery_edittext_sgoods_entname);
                EditText sgoods_snumber = (EditText)findViewById(R.id.logistics_layout_delivery_edittext_sgoods_snumber) ;
                EditText sgoods_sdate = (EditText)findViewById(R.id.logistics_layout_delivery_edittext_sgoods_sdate);
                EditText sgoods_edate = (EditText)findViewById(R.id.logistics_layout_delivery_edittext_sgoods_edate) ;
                EditText sgoods_status_spinner = (EditText)findViewById(R.id.logistics_layout_delivery_edittext_sgoods_status);

                Intent intent1 = new Intent(Logistics_MainActivity.this, Delivery_ListViewActivity.class);
                Bundle bundle1=new Bundle();


                bundle1.putString("logistics_layout_delivery_edittext_sgoods_entname",sgoods_entname.getText().toString());
                bundle1.putString("logistics_layout_delivery_edittext_sgoods_snumber",sgoods_snumber.getText().toString());
                bundle1.putString("logistics_layout_delivery_edittext_sgoods_sdate",sgoods_sdate.getText().toString());
                bundle1.putString("logistics_layout_delivery_edittext_sgoods_edate",sgoods_edate.getText().toString());
                bundle1.putString("logistics_layout_delivery_edittext_sgoods_status",sgoods_status_spinner.getText().toString());

                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.logistics_layout_logistics_button_inquire:
                EditText logistics_entname = (EditText)findViewById(R.id.logistics_layout_logistics_edittext_logistics_entname);
                EditText logistics_number = (EditText)findViewById(R.id.logistics_layout_logistics_edittext_logistics_number) ;
                EditText logistics_sdate = (EditText)findViewById(R.id.logistics_layout_logistics_edittext_logistics_sdate);
                EditText logistics_edate = (EditText)findViewById(R.id.logistics_layout_logistics_edittext_logistics_edate) ;
                EditText logistics_status_spinner = (EditText)findViewById(R.id.logistics_layout_logistics_edittext_logistics_status);

                Intent intent2 = new Intent(Logistics_MainActivity.this, Logistics_ListViewActivity.class);
                Bundle bundle2=new Bundle();


                bundle2.putString("logistics_layout_logistics_edittext_logistics_entname",logistics_entname.getText().toString());
                bundle2.putString("logistics_layout_logistics_edittext_logistics_number",logistics_number.getText().toString());
                bundle2.putString("logistics_layout_logistics_edittext_logistics_sdate",logistics_sdate.getText().toString());
                bundle2.putString("logistics_layout_logistics_edittext_logistics_edate",logistics_edate.getText().toString());
                bundle2.putString("logistics_layout_logistics_edittext_logistics_status",logistics_status_spinner.getText().toString());

                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
        }
    }





}






