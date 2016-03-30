package test.bluerain.youku.com.downloadmechine.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Project: DownloadTest.
 * Data: 2016/3/30.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class CommonUtils {

    private static Handler sHandler;

    static {
        sHandler = new Handler(Looper.getMainLooper());
    }


    public static void runInUIThread(Runnable runnable) {
        sHandler.post(runnable);
    }


}
