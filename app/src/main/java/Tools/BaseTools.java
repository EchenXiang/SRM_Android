package Tools;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by ct on 2016/7/8.
 */
public class BaseTools {

    /** ��ȡ��Ļ�Ŀ�� */
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}