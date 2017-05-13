package Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * Created by ct on 2016/7/7.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context){

        this.views = views;
        this.context = context;
    }

    //当滑动时销毁目前的view
    @Override
    public void destroyItem(View container,int position,Object object){
        //((ViewPager)container).removeView(views.get(position));
    }

    //当滑动时，初始化新的view并展示
    @Override
    public Object instantiateItem(View container,int position){

        position %=views.size();
        if(position<0){
            position = views.size()+position;
        }

        View view = views.get(position);
        ViewParent viewParent = view.getParent();

        if(viewParent!=null){
            ViewGroup parent = (ViewGroup)viewParent;
            parent.removeView(view);
        }

        ((ViewPager)container).addView(view);

        //((ViewPager) container).addView(views.get(position));
        return view;
    }

    //获取当前窗体的界面数目
    @Override
    public int getCount(){
        return Integer.MAX_VALUE;
    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0,Object arg1){
        return (arg0 == arg1);
    }

}