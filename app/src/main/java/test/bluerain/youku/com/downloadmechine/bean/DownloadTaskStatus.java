package test.bluerain.youku.com.downloadmechine.bean;

import java.util.concurrent.ExecutorService;

import test.bluerain.youku.com.downloadmechine.IDownloadService;
import test.bluerain.youku.com.downloadmechine.utils.NetworkFileLoader;

/**
 * 代表下载完成的状态
 * Project: DownloadTest.
 * Data: 2016/3/28.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public abstract class DownloadTaskStatus implements IDownloadService, NetworkFileLoader.LoaderCallback {

    protected NetworkFileLoader mNetworkFileLoader;

    protected DownloadInfo mDownloadInfo;

    protected ExecutorService mExecutor;

    protected DownloadTask mDownloadTask;

    public DownloadTaskStatus(DownloadInfo mDownloadInfo, ExecutorService executor, DownloadTask task) {
        this.mDownloadInfo = mDownloadInfo;
        mExecutor = executor;
        mDownloadTask = task;
        initLoader();
    }

    private void initLoader() {
        mNetworkFileLoader = new NetworkFileLoader(mDownloadInfo.getDownloadUrl(), mDownloadInfo.mSavedPath);
    }

    public NetworkFileLoader getNetworkFileLoader() {
        return mNetworkFileLoader;
    }
}
