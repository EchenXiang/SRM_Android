package com.example.ct.srm_android;

import android.app.ActivityGroup;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import Class.NewsClassify;
import Adapter.ViewPagerAdapter;
import Tools.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class OrderSelectViewActivity extends ActivityGroup  implements ViewPager.OnPageChangeListener{
    private List<View> views;
    private ArrayList<View> pageview;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private ImageHandler mhandler = new ImageHandler(new WeakReference<OrderSelectViewActivity>(this));
    private static int currentItem = 0;
    private int mScreenWidth = 0;
    private int mItemWidth = 0;
    private int columnSelectIndex = 0;
    public ImageView shade_left;

    public ImageView shade_right;
    private ViewPager mViewPager;

    RelativeLayout rl_column;
    private static boolean opr = false;
    private ImageView[] dots;
    LinearLayout mRadioGroup_content;
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private ArrayList<NewsClassify> newsClassify=new ArrayList<NewsClassify>();
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select_view);
        mScreenWidth = BaseTools.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 3;
        initViews();
        initDots();
        initTabColumn();
    }

    private void initViews(){
        mColumnHorizontalScrollView =  (ColumnHorizontalScrollView)findViewById(R.id.mColumnHorizontalScrollView);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);

        mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);

        LayoutInflater inflater = LayoutInflater.from(this);

        //用来存放展示图片的3个view
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.view_page_one,null));
        views.add(inflater.inflate(R.layout.view_page_two,null));
        views.add(inflater.inflate(R.layout.view_page_three, null));

        pageview = new ArrayList<View>();
        pageview.add(inflater.inflate(R.layout.order_select_page_view,null));
        pageview.add(inflater.inflate(R.layout.order_track_page_view,null));

        viewPagerAdapter = new ViewPagerAdapter(views,this);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        //vp.setCurrentItem(Integer.MAX_VALUE / 2);
        mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,ImageHandler.MSG_DELAY);
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter(){

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0==arg1;
            }
            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1){
                ((ViewPager)arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }


        };
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(pageListener);
    }

    //下方圆点
    private void initDots(){
        dots = new ImageView[views.size()];
        for(int i = 0;i<views.size();i++){
            dots[i] =(ImageView)findViewById(ids[i]);
        }
    }


    //处理在图片更换过程中
    @Override
    public void onPageScrollStateChanged(int arg0){
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

    @Override
    public void onPageScrolled(int arg0,float arg1,int arg2){

    }

    //主要处理图片被选中时下方小圆点的展示
    @Override
    public void onPageSelected(int arg0){

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

        mhandler.sendMessage(Message.obtain(mhandler, ImageHandler.MSG_PAGE_CHANGED,arg0,0));
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

        private WeakReference<OrderSelectViewActivity> weakReference;
        //private int currentItem = 0;

        protected ImageHandler(WeakReference<OrderSelectViewActivity> wk){
            weakReference = wk;
        }





        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            //Log.d(LOG_TAG, "receive message" + msg.what);
            OrderSelectViewActivity activity = weakReference.get();

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
                    activity.viewPager.setCurrentItem(currentItem);
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

    private void initTabColumn() {
        newsClassify = getData();
        mRadioGroup_content.removeAllViews();
        int count =  newsClassify.size();
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, mRadioGroup_content, shade_left, shade_right,  rl_column);
        for(int i = 0; i< count; i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth , ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
//			TextView localTextView = (TextView) mInflater.inflate(R.layout.column_radio_item, null);
            TextView columnTextView = new TextView(this);
            columnTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
//			localTextView.setBackground(getResources().getDrawable(R.drawable.top_category_scroll_text_view_bg));
            columnTextView.setBackgroundResource(R.drawable.radio_buttong_bg);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(5, 5, 5, 5);
            columnTextView.setId(i);
            columnTextView.setText(newsClassify.get(i).getTitle());
            columnTextView.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
            if(columnSelectIndex == i){
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for(int i = 0;i < mRadioGroup_content.getChildCount();i++){
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else{
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                    Toast.makeText(getApplicationContext(), newsClassify.get(v.getId()).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            mRadioGroup_content.addView(columnTextView, i ,params);
        }
    }

    public static ArrayList<NewsClassify> getData() {
        ArrayList<NewsClassify> newsClassify = new ArrayList<NewsClassify>();
        NewsClassify classify = new NewsClassify();
        classify.setId(0);
        classify.setTitle("供应商订单查询");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(1);
        classify.setTitle("供应商订单追踪");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(2);
        classify.setTitle("供应商订单统计");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(3);
        classify.setTitle("订单反馈");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(4);
        classify.setTitle("�");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(5);
        classify.setTitle("�ͷ�");
        newsClassify.add(classify);
        classify = new NewsClassify();
//
        return newsClassify;
    }


    public ViewPager.OnPageChangeListener pageListener= new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            mViewPager.setCurrentItem(position);
            selectTab(position);
        }
    };

    private void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            // rg_nav_content.getParent()).smoothScrollTo(i2, 0);
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
            // mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
            // mItemWidth , 0);
        }
        //�ж��Ƿ�ѡ��
        for (int j = 0; j <  mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
            } else {
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

}
