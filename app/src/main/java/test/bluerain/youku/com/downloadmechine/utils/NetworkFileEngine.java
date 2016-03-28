package test.bluerain.youku.com.downloadmechine.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: DownloadMachine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class NetworkFileEngine implements Runnable {


    private int mFileOffset;

    private boolean mSwither;

    private String mFileURL;

    private INetworkFileEngine mINetworkFileEngine;

    private int mLengeth;

    public void setmINetworkFileEngine(INetworkFileEngine mINetworkFileEngine) {
        this.mINetworkFileEngine = mINetworkFileEngine;
    }

    public NetworkFileEngine(String fileURL, int mFileOffset, int length) {
        this.mFileOffset = mFileOffset;
        this.mFileURL = fileURL;
        this.mLengeth = length;
    }

    public void setmFileOffset(int offset) {
        mFileOffset = offset;
    }

    public String getmFileURL() {
        return mFileURL;
    }

    public void setmFileURL(String mFileURL) {
        this.mFileURL = mFileURL;
    }

    public void startEngin() {
        mSwither = true;
    }

    public void stopEngin() {
        mSwither = false;
    }


    @Override
    public void run() {
        InputStream inputStream = null;
        while (mSwither) {
            try {
                URL url = new URL(mFileURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url
                        .openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Accept-Encoding",
                        "identity");
                httpURLConnection.addRequestProperty("Connection", "keep-alive");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setRequestProperty("Range", "bytes="
                        + mFileOffset + "-" + (mLengeth != -1 ? mLengeth + "" : ""));
                httpURLConnection.connect();
                inputStream = httpURLConnection.getInputStream();
                if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    if (null != mINetworkFileEngine)
                        mINetworkFileEngine.onGetSuccessed(inputStream, httpURLConnection.getContentLength());
                } else {
                    if (null != mINetworkFileEngine)
                        mINetworkFileEngine.onGetError("response error code is " + httpURLConnection.getResponseCode());

                }
            } catch (IOException e) {
                mINetworkFileEngine.onGetError("IO error");
                e.printStackTrace();
            } finally {
                if (null != inputStream) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    public interface INetworkFileEngine {

        void onGetSuccessed(InputStream inputStream, int contentLength);

        void onGetError(String errorMsg);

    }

}

