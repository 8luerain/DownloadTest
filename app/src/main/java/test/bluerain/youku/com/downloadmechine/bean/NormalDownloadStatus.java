package test.bluerain.youku.com.downloadmechine.bean;

import android.util.Log;

import java.util.concurrent.ExecutorService;

import test.bluerain.youku.com.downloadmechine.utils.FileUtils;

/**
 * Project: DownloadTest.
 * Data: 2016/3/30.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class NormalDownloadStatus extends DownloadTaskStatus {

    private long lastUpdateTime = 0; //记录上次更新时间

    public NormalDownloadStatus(DownloadInfo mDownloadInfo, ExecutorService executor, DownloadTask task) {
        super(mDownloadInfo, executor, task);
    }

    @Override
    public void startDownload() {
        Log.d("TAG", "download start & fileLength is " + mDownloadInfo.mFileLength + "......");
        mDownloadInfo.start();
        mNetworkFileLoader.setFileOffset(mDownloadInfo.mFileDownloadedLength);
        mExecutor.execute(mNetworkFileLoader);
    }

    @Override
    public void pauseDownload() {
        mDownloadInfo.stop();
        mNetworkFileLoader.stopLoader();
    }

    @Override
    public void cancelDownload() {
        mDownloadInfo.cancel();
        mNetworkFileLoader.stopLoader();
        FileUtils.removeFileFromPath(mDownloadInfo.getSavedPath());
        Log.d("TAG", "clear dataed ...... file length is " + mDownloadInfo.getFileLength());
    }

    @Override
    public void continueDownload() {

    }

    @Override
    public void onGetNetworkFileInfo(String URL, int fileTotalLength, String fileSavePath) {
        mDownloadInfo.setFileLength(fileTotalLength);
    }

    @Override
    public void onProgressUpdate(byte[] downloadData, int downloadLength, long updateTime) {
        mDownloadInfo.setFileDownloadedLength(mDownloadInfo.getFileDownloadedLength() + downloadLength);
        mDownloadInfo.setVelocity(FileUtils.caculateDownloadVelocity(lastUpdateTime, updateTime, downloadLength));
        mDownloadInfo.setProgress(FileUtils.calculateDownloadProgress(mDownloadInfo.getFileDownloadedLength(), mDownloadInfo.getFileLength()));
        lastUpdateTime = updateTime;
    }

    @Override
    public void onFileDownloaded(String URL, int fileTotalLength, String fileSavePath) {
        mDownloadInfo.finish();
        Log.d("TAG", "Filedownload...........");
        String replace = mDownloadInfo.mSavedPath.replace(".tmp", "");
        FileUtils.renameFile(mDownloadInfo.mSavedPath, replace);
        mDownloadInfo.setSavedPath(replace);
    }

    @Override
    public void onGetError(String errorMsg) {

    }
}
