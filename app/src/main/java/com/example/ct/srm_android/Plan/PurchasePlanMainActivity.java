package com.example.ct.srm_android.Plan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ct.srm_android.R;

import Adapter.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class PurchasePlanMainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    //下面五个界面的按钮
    private RadioGroup purchasePlanRadioGrop;
    private RadioButton threeDayPurchasePlanRaioButton;
    private RadioButton doubleWeekPruchasePlanRaioButton;
    private RadioButton tenDayPurchasePlanRaioButton;
    private RadioButton monthPurchasePlanRaioButton;
    private RadioButton yearPurchasePlanRaioButton;
    private ImageView selectedButtonImageView;

    //当前被选中的RadioButton距离左侧的距离
    private float purchasePlanCurrentCheckedRadioLeft;
    //上面的水平滚动控件
    private HorizontalScrollView purchasePlanLayoutHorizontalScrollView;
    //下面可横向拖动的布局
    private ViewPager purchasePlanLayoutViewPager;
    //用来存放下方滚动的layout
    private ArrayList<View> purchasePlanLayoutViews;

    private ViewPager purchasePlanPictureViewPager;
    private PictureViewPagerAdapter pictureAdapter;

    private List<View> purchasePlanPictureDotsViews;
    private ImageView[] purchasePlanPictureDots;
    private int[] dotsNumber = {R.id.picturedot1,R.id.picturedot2,R.id.picturedot3};

    private ImageHandler pictureHandler = new ImageHandler(new WeakReference<PurchasePlanMainActivity>(this));
    private static int currentPurchasePlanPicture = 0;
    //是否在滑动图片
    private static boolean pictureOperation = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_plan_activity_main);

        //图片的viewpager初始化
        initPictureViews();
        initPictureDots();

        //下方五个界面的viewpager初始化
        iniControlle();
        iniListener();
        iniVariable();

        threeDayPurchasePlanRaioButton.setChecked(true);
        purchasePlanLayoutViewPager.setCurrentItem(1);
        purchasePlanCurrentCheckedRadioLeft = getCurrentCheckerRadioLeft();
    }

    private void iniVariable(){

        purchasePlanLayoutViews = new ArrayList<View>();
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.null_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.threeday_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.double_week_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.tenday_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.month_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.year_purchase_plan_viewpager,null));
        purchasePlanLayoutViews.add(getLayoutInflater().inflate(R.layout.null_purchase_plan_viewpager, null));

        purchasePlanLayoutViewPager.setAdapter(new PurchasePlanViewPagerAdapter());
    }

    private void iniListener(){
        purchasePlanRadioGrop.setOnCheckedChangeListener(this);
        purchasePlanLayoutViewPager.addOnPageChangeListener(new PurchasePlanLayoutOnPageChangeListener());
    }

    private void iniControlle(){
        purchasePlanRadioGrop = (RadioGroup)findViewById(R.id.purchasePlanRadioGroup);
        threeDayPurchasePlanRaioButton = (RadioButton)findViewById(R.id.threeDayPurchasePlanRainButton);
        doubleWeekPruchasePlanRaioButton = (RadioButton)findViewById(R.id.doubleWeekPurchasePlanRainButton);
        tenDayPurchasePlanRaioButton = (RadioButton)findViewById(R.id.tenDayPurchasePlanRainButton);
        monthPurchasePlanRaioButton = (RadioButton)findViewById(R.id.monthPurchasePlanRaioButton);
        yearPurchasePlanRaioButton = (RadioButton)findViewById(R.id.yearPurchasePlanRaioButton);

        selectedButtonImageView = (ImageView)findViewById(R.id.selectedButtonImageView);
        purchasePlanLayoutHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.purchasePlanLayoutHorizontalScrollView);
        purchasePlanLayoutViewPager = (ViewPager)findViewById(R.id.purchasePlanLayoutViewPager);

    }

    //radiogroup点击监听
    public void onCheckedChanged(RadioGroup group,int checkedId){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;

        if(checkedId == R.id.threeDayPurchasePlanRainButton){
            translateAnimation = new TranslateAnimation(purchasePlanCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo1),0f,0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            selectedButtonImageView.startAnimation(animationSet);

            purchasePlanLayoutViewPager.setCurrentItem(1);
        }else if(checkedId == R.id.doubleWeekPurchasePlanRainButton){
            translateAnimation = new TranslateAnimation(purchasePlanCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo2),0f,0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            selectedButtonImageView.startAnimation(animationSet);

            purchasePlanLayoutViewPager.setCurrentItem(2);
        }else if(checkedId == R.id.tenDayPurchasePlanRainButton){
            translateAnimation = new TranslateAnimation(purchasePlanCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo3),0f,0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            selectedButtonImageView.startAnimation(animationSet);

            purchasePlanLayoutViewPager.setCurrentItem(3);
        }else if(checkedId == R.id.monthPurchasePlanRaioButton){
            translateAnimation = new TranslateAnimation(purchasePlanCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo4),0f,0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            selectedButtonImageView.startAnimation(animationSet);

            purchasePlanLayoutViewPager.setCurrentItem(4);
        }else if(checkedId == R.id.yearPurchasePlanRaioButton){
            translateAnimation = new TranslateAnimation(purchasePlanCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo5),0f,0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);

            selectedButtonImageView.startAnimation(animationSet);

            purchasePlanLayoutViewPager.setCurrentItem(5);
        }

        purchasePlanCurrentCheckedRadioLeft = getCurrentCheckerRadioLeft();

        purchasePlanLayoutHorizontalScrollView.smoothScrollTo((int) purchasePlanCurrentCheckedRadioLeft -(int)getResources().getDimension(R.dimen.rdo2),0);

    }

    //获得当前被选中的RadioButton距离左侧的距离
    private float getCurrentCheckerRadioLeft(){
        if(threeDayPurchasePlanRaioButton.isChecked()){
            return getResources().getDimension(R.dimen.rdo1);
        }else if(doubleWeekPruchasePlanRaioButton.isChecked()){
            return getResources().getDimension(R.dimen.rdo2);
        }else if(tenDayPurchasePlanRaioButton.isChecked()){
            return getResources().getDimension(R.dimen.rdo3);
        }else if(monthPurchasePlanRaioButton.isChecked()){
            return getResources().getDimension(R.dimen.rdo4);
        }else if(yearPurchasePlanRaioButton.isChecked()){
            return getResources().getDimension(R.dimen.rdo5);
        }
        return 0f;
    }

    //下方五个界面的viewpager的适配器
    private class PurchasePlanViewPagerAdapter extends PagerAdapter{
        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager)v).removeView(purchasePlanLayoutViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return purchasePlanLayoutViews.size();
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager)v).addView(purchasePlanLayoutViews.get(position));
            return purchasePlanLayoutViews.get(position);
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

    //转换界面的监听器
    private class PurchasePlanLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }
        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            //Log.i("zj", "position="+position);

            if (position == 0) {
                purchasePlanLayoutViewPager.setCurrentItem(1);
            }else if (position == 1) {
                threeDayPurchasePlanRaioButton.performClick();
            }else if (position == 2) {
                doubleWeekPruchasePlanRaioButton.performClick();
            }else if (position == 3) {
                tenDayPurchasePlanRaioButton.performClick();
            }else if (position == 4) {
                monthPurchasePlanRaioButton.performClick();
            }else if (position == 5) {
                yearPurchasePlanRaioButton.performClick();
            }else if (position == 6) {
                purchasePlanLayoutViewPager.setCurrentItem(5);
            }
        }
    }

    //图片viewpager的初始化
    private void initPictureViews(){
        LayoutInflater inflater = LayoutInflater.from(this);

        purchasePlanPictureDotsViews = new ArrayList<View>();
        purchasePlanPictureDotsViews.add(inflater.inflate(R.layout.purchase_plan_pictureone, null));
        purchasePlanPictureDotsViews.add(inflater.inflate(R.layout.purchase_plan_picturetwo, null));
        purchasePlanPictureDotsViews.add(inflater.inflate(R.layout.purchase_plan_picturethree, null));

        pictureAdapter = new PictureViewPagerAdapter(purchasePlanPictureDotsViews,this);
        purchasePlanPictureViewPager = (ViewPager)findViewById(R.id.purchasePlanPictureViewPager);
        purchasePlanPictureViewPager.setAdapter(pictureAdapter);
        purchasePlanPictureViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {
                if(pictureOperation) {
                    for (int i = 0; i < dotsNumber.length; i++) {
                        if (arg0 % dotsNumber.length == i) {
                            purchasePlanPictureDots[i].setImageResource(R.drawable.node_select);
                        } else {
                            purchasePlanPictureDots[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                }else {
                    if (arg0 == currentPurchasePlanPicture) {
                        purchasePlanPictureDots[currentPurchasePlanPicture % dotsNumber.length].setImageResource(R.drawable.node_select);
                    }
                    for (int i = 0; i < dotsNumber.length; i++) {
                        if (i != currentPurchasePlanPicture % dotsNumber.length) {
                            purchasePlanPictureDots[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                }

                pictureHandler.sendMessage(Message.obtain(pictureHandler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch(arg0){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        pictureHandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        pictureHandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });

        //vp.setCurrentItem(Integer.MAX_VALUE / 2);
        pictureHandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }

    //图片下方圆点的初始化
    private void initPictureDots(){
        purchasePlanPictureDots = new ImageView[purchasePlanPictureDotsViews.size()];
        for(int i = 0; i< purchasePlanPictureDotsViews.size(); i++){
            purchasePlanPictureDots[i] =(ImageView)findViewById(dotsNumber[i]);
        }
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

        private WeakReference<PurchasePlanMainActivity> weakReference;
        //private int currentItem = 0;

        protected ImageHandler(WeakReference<PurchasePlanMainActivity> wk){
            weakReference = wk;
        }


       //控制图片自动轮播的线程
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //Log.d(LOG_TAG, "receive message" + msg.what);
            PurchasePlanMainActivity activity = weakReference.get();

            if(activity == null){
                return;
            }

            if(activity.pictureHandler.hasMessages(MSG_UPDATE_IMAGE))  {
                if(currentPurchasePlanPicture>0){
                    activity.pictureHandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }

            switch(msg.what){

                case MSG_UPDATE_IMAGE:
                    currentPurchasePlanPicture++;
                    activity.purchasePlanPictureViewPager.setCurrentItem(currentPurchasePlanPicture);
                    activity.pictureHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                    break;

                case MSG_KEEP_SILENT:
                    pictureOperation = true;
                    break;

                case MSG_BREAK_SILENT:
                    activity.pictureHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                    pictureOperation = false;
                    break;

                case MSG_PAGE_CHANGED:
                    currentPurchasePlanPicture = msg.arg1;
                    break;

                default:
                    break;
            }
        }
    }


    //日历和查询按钮的监听器
    public void onClick(View view){

        switch (view.getId()){

            case R.id.threeDayPurchasePlan:

                EditText threeDayProviderName = (EditText)findViewById(R.id.threedayProviderName);
                EditText threeDayMaterialNumber = (EditText)findViewById(R.id.threedayMaterialNumber);
                EditText threeDayReaseDate = (EditText)findViewById(R.id.threedayReaseDate);

                Intent intent1 = new Intent();
                intent1.putExtra("provider_select", threeDayProviderName.getText().toString());
                intent1.putExtra("material_number_select", threeDayMaterialNumber.getText().toString());
                intent1.putExtra("release_date_select", threeDayReaseDate.getText().toString());

                intent1.setClass(PurchasePlanMainActivity.this,ThreeDayPurchasePlanListview.class);
                startActivity(intent1);
                break;

            case R.id.double_week_purchase_plan:
                EditText doubleWeekProviderName = (EditText)findViewById(R.id.doubleWeekProviderName);
                EditText doubleWeekMaterialNumber = (EditText)findViewById(R.id.doubleWeekMaterialNumber);
                EditText doubleWeekReaseDate = (EditText)findViewById(R.id.doubleWeekReaseDate);

                Intent intent2 = new Intent();
                intent2.putExtra("provider_select", doubleWeekProviderName.getText().toString());
                intent2.putExtra("material_number_select", doubleWeekMaterialNumber.getText().toString());
                intent2.putExtra("release_date_select", doubleWeekReaseDate.getText().toString());

                intent2.setClass(PurchasePlanMainActivity.this,DoubleWeekPurchasePlanListview.class);
                startActivity(intent2);
                break;

            case R.id.tenday_purchase_plan:
                EditText tendayProviderName = (EditText)findViewById(R.id.tendayProviderName);
                EditText tendayMaterialNumber = (EditText)findViewById(R.id.tendayMaterialNumber);
                EditText tendayReaseDate = (EditText)findViewById(R.id.tendayReaseDate);

                Intent intent3 = new Intent();
                intent3.putExtra("provider_select", tendayProviderName.getText().toString());
                intent3.putExtra("material_number_select", tendayMaterialNumber.getText().toString());
                intent3.putExtra("release_date_select", tendayReaseDate.getText().toString());

                intent3.setClass(PurchasePlanMainActivity.this,TenDayPurchasePlanListview.class);
                startActivity(intent3);
                break;

            case R.id.month_purchase_plan:
                EditText monthProviderName = (EditText)findViewById(R.id.monthProviderName);
                EditText monthMaterialNumber = (EditText)findViewById(R.id.monthMaterialNumber);
                EditText monthReaseDate = (EditText)findViewById(R.id.monthReaseDate);

                Intent intent4 = new Intent();
                intent4.putExtra("provider_select", monthProviderName.getText().toString());
                intent4.putExtra("material_number_select", monthMaterialNumber.getText().toString());
                intent4.putExtra("release_date_select", monthReaseDate.getText().toString());

                intent4.setClass(PurchasePlanMainActivity.this,MonthPurchasePlanListview.class);
                startActivity(intent4);
                break;

            case R.id.year_purchase_plan:
                EditText yearProviderName = (EditText)findViewById(R.id.yearProviderName);
                EditText yearMaterialNumber = (EditText)findViewById(R.id.yearMaterialNumber);
                EditText yearReaseDate = (EditText)findViewById(R.id.yearReaseDate);

                Intent intent5 = new Intent();
                intent5.putExtra("provider_select", yearProviderName.getText().toString());
                intent5.putExtra("material_number_select", yearMaterialNumber.getText().toString());
                intent5.putExtra("release_date_select", yearReaseDate.getText().toString());

                intent5.setClass(PurchasePlanMainActivity.this,YearPurchasePlanListview.class);
                startActivity(intent5);
                break;

            //日历监听器
            case R.id.threeDayCalendar:
                DatePickerDialog datePicker1=new DatePickerDialog(PurchasePlanMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    String monthOfYearString=null;
                    String dayOfMonthString=null;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText threeDayReaseDate = (EditText) purchasePlanLayoutViews.get(1).findViewById(R.id.threedayReaseDate);
                        if(monthOfYear<10){
                            monthOfYearString="0"+(monthOfYear+i);
                        }else{
                            monthOfYearString=monthOfYear+"";
                        }
                        if(dayOfMonth<10){
                            dayOfMonthString="0"+(dayOfMonth);
                        }else{
                            dayOfMonthString=dayOfMonth+"";
                        }

                        threeDayReaseDate.setText(year+"-"+monthOfYearString+"-"+dayOfMonthString);
                    }
                }, 2016, 6, 7);
                datePicker1.show();
                break;

            case R.id.doubleWeekCalendar:
                DatePickerDialog datePicker2=new DatePickerDialog(PurchasePlanMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    String monthOfYearString=null;
                    String dayOfMonthString=null;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText doubleWeekReaseDate = (EditText) purchasePlanLayoutViews.get(2).findViewById(R.id.doubleWeekReaseDate);
                        if(monthOfYear<10){
                            monthOfYearString="0"+(monthOfYear+i);
                        }else{
                            monthOfYearString=monthOfYear+"";
                        }
                        if(dayOfMonth<10){
                            dayOfMonthString="0"+(dayOfMonth);
                        }else{
                            dayOfMonthString=dayOfMonth+"";
                        }

                        doubleWeekReaseDate.setText(year+"-"+monthOfYearString+"-"+dayOfMonthString);
                    }
                }, 2016, 6, 7);
                datePicker2.show();
                break;

            case R.id.tenDayCalendar:
                DatePickerDialog datePicker3=new DatePickerDialog(PurchasePlanMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    String monthOfYearString=null;
                    String dayOfMonthString=null;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText tendayReaseDate = (EditText) purchasePlanLayoutViews.get(3).findViewById(R.id.tendayReaseDate);
                        if(monthOfYear<10){
                            monthOfYearString="0"+(monthOfYear+i);
                        }else{
                            monthOfYearString=monthOfYear+"";
                        }
                        if(dayOfMonth<10){
                            dayOfMonthString="0"+(dayOfMonth);
                        }else{
                            dayOfMonthString=dayOfMonth+"";
                        }

                        tendayReaseDate.setText(year+"-"+monthOfYearString+"-"+dayOfMonthString);
                    }
                }, 2016, 6, 7);
                datePicker3.show();
                break;

            case R.id.monthCalendar:
                DatePickerDialog datePicker4=new DatePickerDialog(PurchasePlanMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    String monthOfYearString=null;
                    String dayOfMonthString=null;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText monthReaseDate = (EditText) purchasePlanLayoutViews.get(4).findViewById(R.id.monthReaseDate);
                        if(monthOfYear<10){
                            monthOfYearString="0"+(monthOfYear+i);
                        }else{
                            monthOfYearString=monthOfYear+"";
                        }
                        if(dayOfMonth<10){
                            dayOfMonthString="0"+(dayOfMonth);
                        }else{
                            dayOfMonthString=dayOfMonth+"";
                        }

                        monthReaseDate.setText(year+"-"+monthOfYearString+"-"+dayOfMonthString);
                    }
                }, 2016, 6, 7);
                datePicker4.show();
                break;

            case R.id.yearCalendar:
                DatePickerDialog datePicker5=new DatePickerDialog(PurchasePlanMainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    String monthOfYearString=null;
                    String dayOfMonthString=null;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText yearReaseDate = (EditText) purchasePlanLayoutViews.get(5).findViewById(R.id.yearReaseDate);
                        if(monthOfYear<10){
                            monthOfYearString="0"+(monthOfYear+i);
                        }else{
                            monthOfYearString=monthOfYear+"";
                        }
                        if(dayOfMonth<10){
                            dayOfMonthString="0"+(dayOfMonth);
                        }else{
                            dayOfMonthString=dayOfMonth+"";
                        }

                        yearReaseDate.setText(year+"-"+monthOfYearString+"-"+dayOfMonthString);
                    }
                }, 2016, 6, 7);
                datePicker5.show();
                break;

        }
    }

}
