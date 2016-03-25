package test.bluerain.youku.com.downloadmechine.utils;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadThread extends Thread {

    private int mErrorCode;

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }

    @Override
    public void run() {
        super.run();

    }
}
