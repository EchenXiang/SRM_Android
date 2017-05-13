package com.example.ct.srm_android;

import android.app.ActivityGroup;
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
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import Adapter.*;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class stocks_MainActivity extends ActivityGroup implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRadioGrop;
    private RadioButton mRaioButton1;
//    private RadioButton mRaioButton2;
//    private RadioButton mRaioButton3;
//    private RadioButton mRaioButton4;
//    private RadioButton mRaioButton5;
    private ImageView mImageView;
    //当前被选中的RadioButton距离左侧的距离
    private float mCurrentCheckedRadioLeft;
    //上面的水平滚动控件
    private HorizontalScrollView mHorizontalScrollView;
    //下面可横向拖动的布局
    private ViewPager mViewPager;
    //用来存放下方滚动的layout
    private ArrayList<View> mViews;
    private ImageView btnReturn;

    private ViewPager vp;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3};
    private int  current=0;
    private ImageHandler mhandler = new ImageHandler(new WeakReference<stocks_MainActivity>(this));
    private static int currentItem = 0;
    private static boolean opr = false;
    private View stocks_view0,stocks_view1,stocks_view2,stocks_view3,stocks_view4,stocks_view5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stocks_activity_main);

        initViews();
        initDots();

        iniControlle();
        iniListener();
        iniVariable();

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        mRaioButton1.setWidth(width);

        mRaioButton1.setChecked(true);
        mViewPager.setCurrentItem(1);
        mCurrentCheckedRadioLeft = getCurrentCheckerRadioLeft();

        btnReturn = (ImageView) findViewById(R.id.btn_left);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(stocks_MainActivity.this, MainMenuActivity.class));
                finish();
            }
        });
    }

    private void iniVariable(){

        mViews = new ArrayList<View>();

        stocks_view0 = getLocalActivityManager().startActivity("stocks0",new Intent(getApplicationContext(),stocks.class)).getDecorView();
        stocks_view1 = getLocalActivityManager().startActivity("stocks1",new Intent(getApplicationContext(),stocks.class)).getDecorView();
        stocks_view2 = getLocalActivityManager().startActivity("stocks2",new Intent(getApplicationContext(),stocks.class)).getDecorView();
//        stocks_view3 = getLocalActivityManager().startActivity("stocks3",new Intent(getApplicationContext(),stocks.class)).getDecorView();
//        stocks_view4 = getLocalActivityManager().startActivity("stocks4",new Intent(getApplicationContext(),stocks.class)).getDecorView();
//        stocks_view5 = getLocalActivityManager().startActivity("stocks5",new Intent(getApplicationContext(),stocks.class)).getDecorView();
//

        mViews.add(stocks_view0);
        mViews.add(stocks_view1);
//        mViews.add(stocks_view2);
//        mViews.add(stocks_view3);
//        mViews.add(stocks_view4);
//        mViews.add(stocks_view5);
        mViews.add(stocks_view0);

        mViewPager.setAdapter(new MyPagerAdapter());
    }

    private void iniListener(){
        mRadioGrop.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    private void iniControlle(){
        mRadioGrop = (RadioGroup)findViewById(R.id.radioGroup);
        mRaioButton1 = (RadioButton)findViewById(R.id.btn1);
//        mRaioButton2 = (RadioButton)findViewById(R.id.btn2);
//        mRaioButton3 = (RadioButton)findViewById(R.id.btn3);
//        mRaioButton4 = (RadioButton)findViewById(R.id.btn4);
//        mRaioButton5 = (RadioButton)findViewById(R.id.btn5);

        mImageView = (ImageView)findViewById(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        mViewPager = (ViewPager)findViewById(R.id.pager);

    }

    //radiogroup点击监听
    public void onCheckedChanged(RadioGroup group,int checkedId){
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;

        if(checkedId == R.id.btn1) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo1), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);

            mImageView.startAnimation(animationSet);

            mViewPager.setCurrentItem(1);
        }
//        }else if(checkedId == R.id.btn2){
//            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo2),0f,0f);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillBefore(false);
//            animationSet.setFillAfter(true);
//            animationSet.setDuration(100);
//
//            mImageView.startAnimation(animationSet);
//
//            mViewPager.setCurrentItem(2);
//        }else if(checkedId == R.id.btn3){
//            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo3),0f,0f);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillBefore(false);
//            animationSet.setFillAfter(true);
//            animationSet.setDuration(100);
//
//            mImageView.startAnimation(animationSet);
//
//            mViewPager.setCurrentItem(3);
//        }else if(checkedId == R.id.btn4){
//            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo4),0f,0f);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillBefore(false);
//            animationSet.setFillAfter(true);
//            animationSet.setDuration(100);
//
//            mImageView.startAnimation(animationSet);
//
//            mViewPager.setCurrentItem(4);
//        }else if(checkedId == R.id.btn5){
//            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft,getResources().getDimension(R.dimen.rdo5),0f,0f);
//            animationSet.addAnimation(translateAnimation);
//            animationSet.setFillBefore(false);
//            animationSet.setFillAfter(true);
//
//            mImageView.startAnimation(animationSet);
//
//            mViewPager.setCurrentItem(5);
//        }

        mCurrentCheckedRadioLeft = getCurrentCheckerRadioLeft();

        mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.rdo2),0);

    }

    //获得当前被选中的RadioButton距离左侧的距离
    private float getCurrentCheckerRadioLeft(){
        if(mRaioButton1.isChecked()){
            return getResources().getDimension(R.dimen.rdo1);
        }
//        else if(mRaioButton2.isChecked()){
//            return getResources().getDimension(R.dimen.rdo2);
//        }else if(mRaioButton3.isChecked()){
//            return getResources().getDimension(R.dimen.rdo3);
//        }else if(mRaioButton4.isChecked()){
//            return getResources().getDimension(R.dimen.rdo4);
//        }else if(mRaioButton5.isChecked()){
//            return getResources().getDimension(R.dimen.rdo5);
//        }
        return 0f;
    }


    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public void destroyItem(View v, int position, Object obj) {
            // TODO Auto-generated method stub
            ((ViewPager)v).removeView(mViews.get(position));
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
            ((ViewPager)v).addView(mViews.get(position));
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

    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener{
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
                mViewPager.setCurrentItem(1);
            }
            else if (position == 1) {
                mRaioButton1.performClick();
            }
//            else if (position == 2) {
//                mRaioButton2.performClick();
//            }else if (position == 3) {
//                mRaioButton3.performClick();
//            }else if (position == 4) {
//                mRaioButton4.performClick();
//            }else if (position == 5) {
//                mRaioButton5.performClick();
//            }
            else if (position == 2) {
                mViewPager.setCurrentItem(1);
            }
        }
    }

    private void initViews(){
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.view_page_one, null));
        views.add(inflater.inflate(R.layout.view_page_two, null));
        views.add(inflater.inflate(R.layout.view_page_three, null));

        stocks_viewPagerAdapter vpAdapter = new stocks_viewPagerAdapter(views,this);
        vp = (ViewPager)findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int arg0) {
                if(opr) {
                    for (int i = 0; i < ids.length; i++) {
                        if (arg0 % ids.length == i) {
                            dots[i].setImageResource(R.drawable.node_select);
                        } else {
                            dots[i].setImageResource(R.drawable.node_normal);
                        }
                    }
                }else {
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
                switch(arg0){
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;

                    case ViewPager.SCROLL_STATE_IDLE:
                        mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });

        //vp.setCurrentItem(Integer.MAX_VALUE / 2);
        mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
    }

    //下方圆点
    private void initDots(){
        dots = new ImageView[views.size()];
        for(int i = 0;i<views.size();i++){
            dots[i] =(ImageView)findViewById(ids[i]);
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

        private WeakReference<stocks_MainActivity> weakReference;
        //private int currentItem = 0;

        protected ImageHandler(WeakReference<stocks_MainActivity> wk){
            weakReference = wk;
        }



        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //Log.d(LOG_TAG, "receive message" + msg.what);
            stocks_MainActivity activity = weakReference.get();

            if(activity == null){
                return;
            }

            if(activity.mhandler.hasMessages(MSG_UPDATE_IMAGE))  {
                if(currentItem>0){
                    activity.mhandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }

            switch(msg.what){

                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.vp.setCurrentItem(currentItem);
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                    break;

                case MSG_KEEP_SILENT:
                    opr = true;
                    break;

                case MSG_BREAK_SILENT:
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
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
}
