package Adapter;

/**
 * Created by 贾子夏 on 2016/7/7.
 */
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;


public class PictureViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private Context context;

    public PictureViewPagerAdapter(List<View> views, Context context) {

        this.views = views;
        this.context = context;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        //((ViewPager)container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {

        position %= views.size();
        if (position < 0) {
            position = views.size() + position;
        }

        View view = views.get(position);
        ViewParent viewParent = view.getParent();

        if (viewParent != null) {
            ViewGroup parent = (ViewGroup) viewParent;
            parent.removeView(view);
        }

        ((ViewPager) container).addView(view);

        //((ViewPager) container).addView(views.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}