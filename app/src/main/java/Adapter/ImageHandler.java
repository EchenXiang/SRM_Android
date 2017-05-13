package Adapter;

import android.os.Handler;
import android.os.Message;

import com.example.ct.srm_android.Performance.*;

import java.lang.ref.WeakReference;

public class ImageHandler extends Handler {
    protected static final int MSG_UPDATE_IMAGE = 1;//请求更新显示的view
    protected static final int MSG_KEEP_SILENT = 2;//请求暂停轮播
    protected static final int MSG_BREAK_SILENT = 3;//请求恢复轮播
    protected static final int MSG_PAGE_CHANGED = 4;//记录用户要求播放的页面
    protected static final int MSG_DELAY = 3000;//轮播间隔为3秒

    private WeakReference<PerformanceActivity> weakReference;//？？弱引用

    public ImageHandler(WeakReference<PerformanceActivity> weakReference) {
        this.weakReference = weakReference;
    }



    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        PerformanceActivity activity=weakReference.get();
        if(activity==null) return ;//当activity已经收回
        if(activity.getHandler().hasMessages(MSG_UPDATE_IMAGE)){
             activity.getHandler().removeMessages(MSG_UPDATE_IMAGE);//移除队列中未发送的消息
        }//????
        switch (msg.what){
            case MSG_UPDATE_IMAGE:
                activity.setCurrentItem(activity.getCurrentItem()+1);
                activity.getViewPager_graph().setCurrentItem(activity.getCurrentItem());
                activity.getHandler().sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);//准备下次播放
                break;
            case MSG_KEEP_SILENT:
                activity.setStop(true);
                //不发送消息，就会暂停？？
                break;
            case MSG_BREAK_SILENT:
                //发送就会恢复
                activity.setStop(false);
                activity.getHandler().sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);//准备下次播放
                break;
            case MSG_PAGE_CHANGED:
               activity.setCurrentItem(msg.arg1);//记录当前页号
                break;
            default:
                break;
        }
    }
}
