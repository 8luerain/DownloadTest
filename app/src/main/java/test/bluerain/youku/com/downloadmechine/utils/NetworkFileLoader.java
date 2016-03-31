package test.bluerain.youku.com.downloadmechine.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: DownloadMachine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class NetworkFileLoader implements Runnable {


    private int mFileOffset = 0;

    private int mLength = -1;

    private int mTotalReadCount = 0;  //本次下载总字节数

    private int mFileSize = 0;

    protected String mFilePath;

    private String mFileURL;

    private boolean mSwitcher;

    private HttpURLConnection mHttpURLConnection;

    private LoaderCallback mLoaderCallback;

    public void setLoaderCallback(LoaderCallback mLoaderCallback) {
        this.mLoaderCallback = mLoaderCallback;
    }

    public NetworkFileLoader(String fileURL, String filePath) {
        mFileURL = fileURL;
        mFilePath = filePath;
    }

    public void stopLoader() {
        mSwitcher = false;
    }

    public int getFileOffset() {
        return mFileOffset;
    }

    public void setFileOffset(int fileOffset) {
        mFileOffset = fileOffset;
    }

    public int getLength() {
        return mLength;
    }

    public void setLength(int length) {
        mLength = length;
    }

    private void initConnection() {
        try {
            URL url = new URL(mFileURL);
            mHttpURLConnection = (HttpURLConnection) url.openConnection();
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setRequestProperty("Accept-Encoding",
                    "identity");
            mHttpURLConnection.addRequestProperty("Connection", "keep-alive");
            mHttpURLConnection.setConnectTimeout(5000);
            mHttpURLConnection.setReadTimeout(10000);
            mHttpURLConnection.setRequestProperty("Range", "bytes="
                    + mFileOffset + "-" + (mLength != -1 ? mLength + "" : ""));
            if (mHttpURLConnection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                mSwitcher = true;
                mFileSize = mHttpURLConnection.getContentLength() + mFileOffset;
                if (null != mLoaderCallback)
                    mLoaderCallback.onGetNetworkFileInfo(mFileURL, mFileSize, mFilePath);
            } else {
                if (null != mLoaderCallback)
                    mLoaderCallback.onGetError("service error response code is " + mHttpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (null != mLoaderCallback)
                mLoaderCallback.onGetError("service error response code is " + e.toString());
        }
    }

    @Override
    public void run() {
        Log.d("TAG", "startEngine start........");
        initConnection();
        startEngine(mHttpURLConnection);
        checkOrSetFileDownloaded();
//        clearCallBacks();
    }


    private void checkOrSetFileDownloaded() {
        Log.d("TAG", "plus is " + mFileOffset + "totle is " + mFileSize);
        if ((mFileOffset) == mFileSize) {
            if (null != mLoaderCallback)
                mLoaderCallback.onFileDownloaded(mFileURL, mFileSize, mFilePath);
        }
    }

    /**
     * 由connect开始下载文件
     *
     * @param connection
     */
    private void startEngine(HttpURLConnection connection) {
        byte[] block = new byte[4096];
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        int readCount;
        try {
            bufferedInputStream = new BufferedInputStream(connection.getInputStream());
            bufferedOutputStream = new BufferedOutputStream(
                    new FileOutputStream(FileUtils.getFileFromPath(mFilePath), true)
            );
            while ((readCount = bufferedInputStream.read(block)) != -1 && mSwitcher) {
                bufferedOutputStream.write(block, 0, readCount);
                mFileOffset += readCount;
                if (null != mLoaderCallback) {
                    mLoaderCallback.onProgressUpdate(block, readCount, System.currentTimeMillis());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (null != mLoaderCallback)
                mLoaderCallback.onGetError(e.toString());
        } finally {
            FileUtils.closeQuilty(bufferedInputStream);
            FileUtils.closeQuilty(bufferedOutputStream);
        }

    }

    private void clearCallBacks() {
        mLoaderCallback = null;
    }

    public interface LoaderCallback {

        /**
         * 当获取到服务器返回头信息时回调
         *
         * @param URL             文件URL地址
         * @param fileTotalLength 读取到的文件长度
         * @param fileSavePath    文件本地保存路径
         */

        void onGetNetworkFileInfo(String URL, int fileTotalLength, String fileSavePath);

        /**
         * 当下载的文件块更新时回调
         *
         * @param downloadData   本次下载内容
         * @param downloadLength 本次下载长度
         * @param updateTime     本次更新时间
         * @param updateTime     本次更新时间
         */
        void onProgressUpdate(byte[] downloadData, int downloadLength, long updateTime);


        /**
         * 当文件下载完成后回调
         *
         * @param URL
         * @param fileTotalLength
         * @param fileSavePath
         */
        void onFileDownloaded(String URL, int fileTotalLength, String fileSavePath);

        /**
         * 当下载出错时回调
         *
         * @param errorMsg
         */
        void onGetError(String errorMsg);

    }

}

