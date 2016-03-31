package test.bluerain.youku.com.downloadmechine.bean;

import java.util.concurrent.ExecutorService;

/**
 * Created by rain on 2016/3/27.
 */
public class DownloadTask extends DownloadTaskStatus {

    private String mKey;

    private NormalDownloadStatus mNormalDownloadStatus;

    private DownloadTaskStatus mCurrentStatus;

    public DownloadTask(DownloadInfo info, ExecutorService executor) {
        super(info, executor, null);
        mDownloadInfo = info;
        mKey = mDownloadInfo.getDownloadUrl();
        initStatus();
        setCurrentStatus(mNormalDownloadStatus);
    }

    private void initStatus() {
        mNormalDownloadStatus = new NormalDownloadStatus(mDownloadInfo, mExecutor, this);
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

    @Override
    public void onGetNetworkFileInfo(String URL, int fileTotalLength, String fileSavePath) {
        mCurrentStatus.onGetNetworkFileInfo(URL, fileTotalLength, fileSavePath);
    }

    @Override
    public void onProgressUpdate(byte[] downloadData, int downloadLength, long updateTime) {
        mCurrentStatus.onProgressUpdate(downloadData, downloadLength, updateTime);
    }

    @Override
    public void onFileDownloaded(String URL, int fileTotalLength, String fileSavePath) {
        mCurrentStatus.onFileDownloaded(URL, fileTotalLength, fileSavePath);
    }

    @Override
    public void onGetError(String errorMsg) {
        mCurrentStatus.onGetError(errorMsg);
    }


    //--------------getter & setter  method ---------------------------start---------------------


    public void setCurrentStatus(DownloadTaskStatus status) {
        mCurrentStatus = status;
    }

    public String getKey() {
        return mKey;
    }

    public DownloadTaskStatus getCurrentStatus() {
        return mCurrentStatus;
    }

    public NormalDownloadStatus getNormalDownloadStatus() {
        return mNormalDownloadStatus;
    }

    //--------------getter & setter  method ---------------------------end---------------------

}
