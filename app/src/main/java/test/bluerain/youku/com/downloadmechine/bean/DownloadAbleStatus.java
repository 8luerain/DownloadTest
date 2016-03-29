package test.bluerain.youku.com.downloadmechine.bean;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import test.bluerain.youku.com.downloadmechine.IDownloadService;
import test.bluerain.youku.com.downloadmechine.utils.FileUtils;
import test.bluerain.youku.com.downloadmechine.utils.NetworkFileEngine;

/**
 * Created by rain on 2016/3/28.
 */
public class DownloadAbleStatus implements IDownloadService {

    private NetworkFileEngine mNetworkFileEngine;

    private DownloadInfo mDownloadInfo;

    private ExecutorService mThreadPoolExecutor;

    private DownloadTask mDownloadTask;

    public DownloadAbleStatus(DownloadInfo mDownloadInfo, ExecutorService executor, DownloadTask task) {
        this.mDownloadInfo = mDownloadInfo;
        mThreadPoolExecutor = executor;
        mDownloadTask = task;
        mNetworkFileEngine = task.getNetworkFileEngine();
    }


    @Override
    public void startDownload() {
        Log.d("TAG", "download start ......");
        mDownloadTask.setCurrentStatus(mDownloadTask.getDownloadingStatus());
        mNetworkFileEngine.setmINetworkFileEngine(new NetworkFileEngine.INetworkFileEngine() {

            @Override
            public void onGetSuccessed(InputStream inputStream, int contentLength) {
                if (mDownloadInfo.getFileLength() == 0)
                    mDownloadInfo.setFileLength(contentLength);
                byte[] block = new byte[4096];
                BufferedInputStream bufferedInputStream = null;
                BufferedOutputStream bufferedOutputStream = null;
                int readCount;
                try {
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    bufferedOutputStream = new BufferedOutputStream(
                            new FileOutputStream(FileUtils.getFileFromPath(mDownloadInfo.mSavedPath))
                    );
                    long lastTime = System.currentTimeMillis();
                    while ((readCount = bufferedInputStream.read(block)) != -1) {
                        int newLength = mDownloadInfo.getmFileCurrentLength() + readCount;
                        mDownloadInfo.setmFileCurrentLength(newLength);
                        bufferedOutputStream.write(block, 0, readCount);
                        long endTime = System.currentTimeMillis();
                        int velocity = caculateVelocity(lastTime, endTime, readCount);
                        mDownloadInfo.setmProgress(calculateProgress(newLength, mDownloadInfo.getFileLength()));
                        mDownloadInfo.setmVelocity(velocity);
                        lastTime = endTime;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    FileUtils.closeQuilty(bufferedInputStream);
                    FileUtils.closeQuilty(bufferedOutputStream);
                }

                mDownloadTask.setCurrentStatus(mDownloadTask.getDownloadedStatus());

            }

            @Override
            public void onGetError(String errorMsg) {

            }
        });
        if (null != mThreadPoolExecutor)
            mThreadPoolExecutor.execute(mNetworkFileEngine);
    }

    private int calculateProgress(int newLength, int fileLength) {
        return (int) ((newLength * 1.0 / fileLength) * 100);
    }

    @Override
    public void pauseDownload() {
//        if (null != mNetworkFileEngine) {
//            mNetworkFileEngine.stopEngin();
//        }
//        Log.d("TAG", "download pause ........");
    }

    @Override
    public void cancelDownload() {
//        pauseDownload();
//        FileUtils.removeFileFromPath(mDownloadInfo.getmSavedPath());
//        Log.d("TAG", "download cancel ........");
    }

    @Override
    public void continueDownload() {
//        if (null != mNetworkFileEngine) {
        startDownload();
//        }
        Log.d("TAG", "download continue ........");
    }


    private int caculateVelocity(long lastTime, long endTime, int downloadSize) {

        long during = (endTime - lastTime);
        long time = during == 0 ? 1 : during;
        long velocity = (long) (downloadSize / (time / 1000.0));

        return (int) velocity;

    }
}
