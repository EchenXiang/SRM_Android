package com.example.ct.srm_android;

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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.angmarch.views.NiceSpinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.ViewPagerAdapter;

public class OrderSelectActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButton3;
    private RadioButton mRadioButton4;
    private RadioButton mRadioButton5;
    private ImageView mImageView;
    private ImageView back;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//按钮的水平滚动控件
    private ViewPager mViewPager;//下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(order_condition_layout1)
    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1, R.id.iv2, R.id.iv3};
    private int current = 0;
    private ImageHandler mhandler = new ImageHandler(new WeakReference<OrderSelectActivity>(this));
    private static int currentItem = 0;
    private static boolean opr = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select);
        back = (ImageView)findViewById(R.id.order_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSelectActivity.this.finish();
            }
        });

        initViews();
        initDots();
        iniController();
        iniListener();
        iniVariable();
        mRadioButton1.setChecked(true);
        mViewPager.setCurrentItem(1);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

    }


    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.one, null));
        views.add(inflater.inflate(R.layout.two, null));
        views.add(inflater.inflate(R.layout.three, null));
        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout0, null));
        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout1, null));
        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout2, null));
        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout3, null));
        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout4, null));

        mViews.add(getLayoutInflater().inflate(R.layout.order_condition_layout0, null));
        mViewPager.setAdapter(new MyPagerAdapter());//设置ViewPager的适配器
    }
    private void iniController() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        mRadioButton1 = (RadioButton) findViewById(R.id.button1);
        mRadioButton2 = (RadioButton) findViewById(R.id.button2);
        mRadioButton3 = (RadioButton) findViewById(R.id.button3);
        mRadioButton4 = (RadioButton) findViewById(R.id.button4);

        mImageView = (ImageView) findViewById(R.id.imageview1);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalscrollview1);
        mViewPager = (ViewPager) findViewById(R.id.viewpager1);
    }
    private void iniListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.addOnPageChangeListener(new MyPagerChangeListener());
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
        private WeakReference<OrderSelectActivity> weakReference;
        protected ImageHandler(WeakReference<OrderSelectActivity> wk) {
            weakReference = wk;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            OrderSelectActivity activity = weakReference.get();
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
                    activity.vp.setCurrentItem(currentItem);
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
    //RadioGroup点击CheckedChanged监听事件
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation;
        if (checkedId == R.id.button1) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo1), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
            mViewPager.setCurrentItem(1);//让下方ViewPager跟随上面的HorizontalScrollView切换
        } else if (checkedId == R.id.button2) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo2), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            mImageView.startAnimation(animationSet);
            mViewPager.setCurrentItem(2);
        } else if (checkedId == R.id.button3) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo3), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            mImageView.startAnimation(animationSet);
            mViewPager.setCurrentItem(3);
        } else if (checkedId == R.id.button4) {
            translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo4), 0f, 0f);
            animationSet.addAnimation(translateAnimation);
            animationSet.setFillBefore(false);
            animationSet.setFillAfter(true);
            animationSet.setDuration(100);
            mImageView.startAnimation(animationSet);
            mViewPager.setCurrentItem(4);
        }
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
        mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);
    }
    //获得当前被选中的RadioButton距离左侧的距离
    private float getCurrentCheckedRadioLeft() {
        if (mRadioButton1.isChecked()) {
            return getResources().getDimension(R.dimen.rdo1);
        } else if (mRadioButton2.isChecked()) {
            return getResources().getDimension(R.dimen.rdo2);
        } else if (mRadioButton3.isChecked()) {
            return getResources().getDimension(R.dimen.rdo3);
        } else if (mRadioButton4.isChecked()) {
            return getResources().getDimension(R.dimen.rdo4);
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
                mViewPager.setCurrentItem(1);
            } else if (position == 1) {
                mRadioButton1.performClick();
            } else if (position == 2) {
                mRadioButton2.performClick();
            } else if (position == 3) {
                mRadioButton3.performClick();
            } else if (position == 4) {
                mRadioButton4.performClick();
            }
            else if (position == 5) {
                mViewPager.setCurrentItem(4);
            }

        }
    }
    //获取日期
    public void onclick(View view){
        switch (view.getId()) {
            case R.id.order_select_date_from:
                DatePickerDialog datePicker = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(1).findViewById(R.id.order_select_order_generate_day_from);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker.show();
                break;
            case R.id.order_select_date_to:
                DatePickerDialog datePicker1 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(1).findViewById(R.id.order_select_order_generate_day_to);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker1.show();
                break;
            case R.id.order_track_date_from:
                DatePickerDialog datePicker2 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(2).findViewById(R.id.order_track_specified_delivery_from);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker2.show();
                break;
            case R.id.order_track_date_to:
                DatePickerDialog datePicker3 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(2).findViewById(R.id.order_track_specified_delivery_to);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker3.show();
                break;

            case R.id.order_statistic_date_from:
                DatePickerDialog datePicker4 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(3).findViewById(R.id.order_statistic_order_generate_day_from);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker4.show();
                break;
            case R.id.order_statistic_date_to:
                DatePickerDialog datePicker5 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(3).findViewById(R.id.order_statistic_order_generate_day_to);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker5.show();
                break;

            case R.id.order_date_from:
                DatePickerDialog datePicker6 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(4).findViewById(R.id.order_feedback_order_generate_day_from);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker6.show();
                break;
            case R.id.order_date_to:
                DatePickerDialog datePicker7 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(4).findViewById(R.id.order_feedback_order_generate_day_to);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker7.show();
                break;

            case R.id.plan_date_from:
                DatePickerDialog datePicker8 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(4).findViewById(R.id.order_feedback_plan_ship_day_from);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker8.show();
                break;
            case R.id.plan_date_to:
                DatePickerDialog datePicker9 = new DatePickerDialog(OrderSelectActivity.this, new DatePickerDialog.OnDateSetListener() {
                    int i = 1;
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        EditText edrili = (EditText) mViews.get(4).findViewById(R.id.order_feedback_plan_ship_day_to);
                        edrili.setText(year + "." + (monthOfYear + i) + "." + dayOfMonth);
                    }

                }, 2016, 6, 7);
                datePicker9.show();
                break;
        }


    }

    public void SelectOnClick(View view){
        switch (view.getId()){
            case R.id.order_select_select:
                EditText company_name1 = (EditText)findViewById(R.id.order_select_company_name);
                EditText order_id1 = (EditText)findViewById(R.id.order_select_order_id) ;
                EditText generate_day_from = (EditText)findViewById(R.id.order_select_order_generate_day_from);
                EditText generate_day_to = (EditText)findViewById(R.id.order_select_order_generate_day_to) ;
                Spinner order_select_order_status_spinner = (Spinner)findViewById(R.id.order_select_order_status_spinner);
                Spinner order_select_provider_confirm_spinner = (Spinner)findViewById(R.id.order_select_provider_confirm_spinner);
                Spinner order_select_delivery_status_spinner= (Spinner)findViewById(R.id.order_select_delivery_status_spinner);

                Intent intent1 = new Intent(OrderSelectActivity.this, OrderSelectListviewActivity.class);
                Bundle bundle1=new Bundle();


                bundle1.putString("order_select_company_name",company_name1.getText().toString());
                bundle1.putString("order_select_order_id",order_id1.getText().toString());
                bundle1.putString("order_select_order_generate_day_from",generate_day_from.getText().toString());
                bundle1.putString("order_select_order_generate_day_to",generate_day_to.getText().toString());
                bundle1.putString("order_select_order_status",order_select_order_status_spinner.getSelectedItem().toString());
                bundle1.putString("order_select_provider_confirm",order_select_provider_confirm_spinner.getSelectedItem().toString());
                bundle1.putString("order_select_delivery_status",order_select_delivery_status_spinner.getSelectedItem().toString());
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.order_track_select:
                EditText order_track_company_name = (EditText)findViewById(R.id.order_track_company_name);
                EditText order_track_order_id = (EditText)findViewById(R.id.order_track_order_id) ;
                EditText order_track_material_number = (EditText)findViewById(R.id.order_track_material_number);
                EditText order_track_specified_delivery_from = (EditText)findViewById(R.id.order_track_specified_delivery_from) ;
                EditText order_track_specified_delivery_to = (EditText)findViewById(R.id.order_track_specified_delivery_to) ;
                Spinner order_track_delivery_status_spinner = (Spinner)findViewById(R.id.order_track_delivery_status_spinner);

                Intent intent2 = new Intent(OrderSelectActivity.this, OrderTrackListviewActivity.class);
                Bundle bundle2=new Bundle();
                bundle2.putString("order_track_company_name",order_track_company_name.getText().toString());
                bundle2.putString("order_track_order_id",order_track_order_id.getText().toString());
                bundle2.putString("order_track_material_number",order_track_material_number.getText().toString());
                bundle2.putString("order_track_specified_delivery_from",order_track_specified_delivery_from.getText().toString());
                bundle2.putString("order_track_specified_delivery_to",order_track_specified_delivery_to.getText().toString());
                bundle2.putString("order_track_delivery_status_spinner",order_track_delivery_status_spinner.getSelectedItem().toString());
                intent2.putExtras(bundle2);
                startActivity(intent2);
                /*EditText company_name2 = (EditText)findViewById(R.id.order_select_company_name);
                EditText order_id2 = (EditText)findViewById(R.id.order_select_order_id) ;
                EditText generate_day_from2 = (EditText)findViewById(R.id.order_select_order_generate_day_from);
                EditText generate_day_to2 = (EditText)findViewById(R.id.order_select_order_generate_day_to) ;
                Spinner order_select_order_status_spinner2 = (Spinner)findViewById(R.id.order_select_order_status_spinner);
                Spinner order_select_provider_confirm_spinner2 = (Spinner)findViewById(R.id.order_select_provider_confirm_spinner);
                Spinner order_select_delivery_status_spinner2= (Spinner)findViewById(R.id.order_select_delivery_status_spinner);

                Intent intent2 = new Intent(OrderSelectActivity.this, OrderSelectListviewActivity.class);
                Bundle bundle2=new Bundle();


                bundle2.putString("order_select_company_name",company_name2.getText().toString());
                bundle2.putString("order_select_order_id",order_id2.getText().toString());
                bundle2.putString("order_select_order_generate_day_from",generate_day_from2.getText().toString());
                bundle2.putString("order_select_order_generate_day_to",generate_day_to2.getText().toString());
                bundle2.putString("order_select_order_status",order_select_order_status_spinner2.getSelectedItem().toString());
                bundle2.putString("order_select_provider_confirm",order_select_provider_confirm_spinner2.getSelectedItem().toString());
                bundle2.putString("order_select_delivery_status",order_select_delivery_status_spinner2.getSelectedItem().toString());
                intent2.putExtras(bundle2);
                startActivity(intent2);


                *//*Intent intent_two = new Intent(OrderSelectActivity.this, OrderTrackListviewActivity.class);
                Bundle bundle2=new Bundle();*//*



                startActivity(intent_two);*/

                break;
            case R.id.order_statistic_select:
                EditText order_statistic_company_name = (EditText)findViewById(R.id.order_statistic_company_name);
                EditText order_statistic_order_generate_day_from = (EditText)findViewById(R.id.order_statistic_order_generate_day_from) ;
                EditText order_statistic_order_generate_day_to = (EditText)findViewById(R.id.order_statistic_order_generate_day_to);
                Spinner order_statistic_provider_confirm_spinner = (Spinner)findViewById(R.id.order_statistic_provider_confirm_spinner);
                Spinner order_statistic_order_status_spinner = (Spinner)findViewById(R.id.order_statistic_order_status_spinner);
                Spinner order_statistic_delivery_status_spinner = (Spinner)findViewById(R.id.order_statistic_delivery_status_spinner);


                Intent intent3 = new Intent(OrderSelectActivity.this,OrderStatisticListviewActivity.class);
                Bundle bundle3=new Bundle();
                bundle3.putString("order_statistic_company_name",order_statistic_company_name.getText().toString());
                bundle3.putString("order_statistic_order_generate_day_from",order_statistic_order_generate_day_from.getText().toString());
                bundle3.putString("order_statistic_order_generate_day_to",order_statistic_order_generate_day_to.getText().toString());
                bundle3.putString("order_statistic_provider_confirm_spinner",order_statistic_provider_confirm_spinner.getSelectedItem().toString());
                bundle3.putString("order_statistic_order_status_spinner",order_statistic_order_status_spinner.getSelectedItem().toString());
                bundle3.putString("order_statistic_delivery_status_spinner",order_statistic_delivery_status_spinner.getSelectedItem().toString());
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
            case R.id.order_feedback_select:




                EditText order_feedback_company_name = (EditText)findViewById(R.id.order_feedback_company_name);
                EditText order_feedback_material_number = (EditText)findViewById(R.id.order_feedback_material_number) ;
                EditText order_feedback_order_id = (EditText)findViewById(R.id.order_feedback_order_id);
                EditText order_feedback_order_generate_day_from = (EditText)findViewById(R.id.order_feedback_order_generate_day_from) ;
                EditText order_feedback_order_generate_day_to = (EditText)findViewById(R.id.order_feedback_order_generate_day_to);
                EditText order_feedback_plan_ship_day_from = (EditText)findViewById(R.id.order_feedback_plan_ship_day_from) ;
                EditText order_feedback_plan_ship_day_to = (EditText)findViewById(R.id.order_feedback_plan_ship_day_to);

                Intent intent4 = new Intent(OrderSelectActivity.this,OrderFeedbackListviewActivity.class);
                Bundle bundle4=new Bundle();
                bundle4.putString("order_feedback_company_name",order_feedback_company_name.getText().toString());
                bundle4.putString("order_feedback_material_number",order_feedback_material_number.getText().toString());
                bundle4.putString("order_feedback_order_id",order_feedback_order_id.getText().toString());
                bundle4.putString("order_feedback_order_generate_day_from",order_feedback_order_generate_day_from.getText().toString());
                bundle4.putString("order_feedback_order_generate_day_to",order_feedback_order_generate_day_to.getText().toString());
                bundle4.putString("order_feedback_plan_ship_day_from",order_feedback_plan_ship_day_from.getText().toString());
                bundle4.putString("order_feedback_plan_ship_day_to",order_feedback_plan_ship_day_to.getText().toString());
                intent4.putExtras(bundle4);
                startActivity(intent4);
                break;
        }
    }


}
