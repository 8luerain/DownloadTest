package test.bluerain.youku.com.downloadmechine.bean;

import java.util.concurrent.ThreadPoolExecutor;

import test.bluerain.youku.com.downloadmechine.IDownloadService;

/**
 * Created by rain on 2016/3/27.
 */
public class DownloadTask implements IDownloadService {


    private DownloadAbleStatus mDownloadAbleStatus;

    private DownloadingStatus mDownloadingStatus;

    private DownloadedStatus mDownloadedStatus;

    private IDownloadService mCurrentStatus;


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
        mDownloadingStatus = downloadingStatus;
    }

    public DownloadedStatus getDownloadedStatus() {
        return mDownloadedStatus;
    }

    public void setDownloadedStatus(DownloadedStatus downloadedStatus) {
        mDownloadedStatus = downloadedStatus;
    }

    public DownloadTask(DownloadInfo info, ThreadPoolExecutor executor) {
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
}
