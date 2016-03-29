package test.bluerain.youku.com.downloadmechine.bean;

import android.util.Log;

import java.util.concurrent.ExecutorService;

import test.bluerain.youku.com.downloadmechine.IDownloadService;
import test.bluerain.youku.com.downloadmechine.utils.NetworkFileEngine;

/**
 * Created by rain on 2016/3/27.
 */
public class DownloadTask implements IDownloadService {


    private String mKey;

    private DownloadInfo mDownloadInfo;

    private NetworkFileEngine mNetworkFileEngine;

    private DownloadAbleStatus mDownloadAbleStatus;

    private DownloadingStatus mDownloadingStatus;

    private DownloadedStatus mDownloadedStatus;

    private IDownloadService mCurrentStatus;


    public DownloadTask(DownloadInfo info, ExecutorService executor) {
        mDownloadInfo = info;
        setTaskKey(mDownloadInfo);
        mNetworkFileEngine = new NetworkFileEngine(mDownloadInfo.mDownloadUrl, mDownloadInfo.getmFileCurrentLength(), -1);
        mDownloadAbleStatus = new DownloadAbleStatus(info, executor, this);
        mCurrentStatus = mDownloadAbleStatus;
    }


    @Override
    public void startDownload() {

        mCurrentStatus.startDownload();
    }

    @Override
    public void pauseDownload() {

        mCurrentStatus.pauseDownload();
    }

    @Override
    public void cancelDownload() {

        mCurrentStatus.cancelDownload();
    }

    @Override
    public void continueDownload() {

        mCurrentStatus.continueDownload();

    }

    public void setCurrentStatus(IDownloadService status) {
        mCurrentStatus = status;
    }


//--------------getter & setter  method ---------------------------start---------------------

    public DownloadInfo getDownloadInfo() {
        return mDownloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        mDownloadInfo = downloadInfo;
    }

    public NetworkFileEngine getNetworkFileEngine() {
        return mNetworkFileEngine;
    }

    public void setNetworkFileEngine(NetworkFileEngine networkFileEngine) {
        mNetworkFileEngine = networkFileEngine;
    }

    public IDownloadService getCurrentStatus() {
        return mCurrentStatus;
    }

    public DownloadAbleStatus getDownloadAbleStatus() {
        return mDownloadAbleStatus;
    }

    public void setDownloadAbleStatus(DownloadAbleStatus downloadAbleStatus) {
        mDownloadAbleStatus = downloadAbleStatus;
    }

    public DownloadingStatus getDownloadingStatus() {
        return mDownloadingStatus;
    }

    public void setDownloadingStatus(DownloadingStatus downloadingStatus) {
        Log.d("TAG", "task change to downloading status ......");
        mDownloadingStatus = downloadingStatus;
    }

    public DownloadedStatus getDownloadedStatus() {
        return mDownloadedStatus;
    }

    public void setDownloadedStatus(DownloadedStatus downloadedStatus) {
        mDownloadedStatus = downloadedStatus;
    }

    public void setTaskKey(DownloadInfo info) {
        if (null != info)
            mKey = info.getmDownloadUrl();
    }
//--------------getter & setter  method ---------------------------end---------------------

}
