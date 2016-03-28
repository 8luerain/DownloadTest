package test.bluerain.youku.com.downloadmechine.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ThreadPoolExecutor;

import test.bluerain.youku.com.downloadmechine.IDownloadService;
import test.bluerain.youku.com.downloadmechine.utils.FileUtils;
import test.bluerain.youku.com.downloadmechine.utils.NetworkFileEngine;

/**
 * Created by rain on 2016/3/28.
 */
public class DownloadAbleStatus implements IDownloadService {

    private NetworkFileEngine mNetworkFileEngine;

    private DownloadInfo mDownloadInfo;

    private ThreadPoolExecutor mThreadPoolExecutor;

    private DownloadTask mDownloadTask;

    public DownloadAbleStatus(DownloadInfo mDownloadInfo, ThreadPoolExecutor executor, DownloadTask task) {
        this.mDownloadInfo = mDownloadInfo;
        mThreadPoolExecutor = executor;
        mDownloadTask = task;
    }


    @Override
    public void startDownload() {
        mNetworkFileEngine = new NetworkFileEngine(
                mDownloadInfo.mDownloadUrl,
                mDownloadInfo.mFileCurrentLength,
                -1
        );
        mNetworkFileEngine.setmINetworkFileEngine(new NetworkFileEngine.INetworkFileEngine() {

            @Override
            public void onGetSuccessed(InputStream inputStream, int contentLength) {
                if (mDownloadInfo.getFileLength() == 0)
                    mDownloadInfo.setFileLength(contentLength);
                byte[] block = new byte[4096];
                BufferedInputStream bufferedInputStream = null;
                BufferedOutputStream bufferedOutputStream = null;
                int readCount = -1;
                try {
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    bufferedOutputStream = new BufferedOutputStream(
                            new FileOutputStream(FileUtils.getFileFromPath(mDownloadInfo.mSavedPath))
                    );
                    while ((readCount = bufferedInputStream.read(block)) != -1) {
                        int newLength = mDownloadInfo.getmFileCurrentLength() + readCount;
                        mDownloadInfo.setmFileCurrentLength(newLength);
                        bufferedOutputStream.write(block, 0, readCount);
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

    @Override
    public void pauseDownload() {
        if (null != mNetworkFileEngine) {
            mNetworkFileEngine.stopEngin();
        }
    }

    @Override
    public void cancelDownload() {
        pauseDownload();
        FileUtils.removeFileFromPath(mDownloadInfo.getmSavedPath());

    }

    @Override
    public void continueDownload() {
        if (null != mNetworkFileEngine) {
            startDownload();
        }
    }
}
