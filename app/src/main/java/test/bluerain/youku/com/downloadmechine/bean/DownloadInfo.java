package test.bluerain.youku.com.downloadmechine.bean;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadInfo {

    public String mDownloadUrl;

    public int mProgress;

    public int mVelocity;  //下载速度，单位KB

    public int mFileLength;

    public int mFileDownloadedLength;

    public boolean isStop;

    public String mSavedPath;

    public IDownloadInfoChangeListener mIDownloadInfoChangeListener;

    public DownloadInfo() {
        initParams();
    }

    private void initParams() {
        setFileLength(0);
        setFileDownloadedLength(0);
        setProgress(0);
        setVelocity(0);
        isStop = false;
    }


    public void start() {
        isStop = false;
        if (!isNullListener())
            mIDownloadInfoChangeListener.onStartDownload();
    }

    public void stop() {
        isStop = true;
        if (!isNullListener())
            mIDownloadInfoChangeListener.onStopDownload();
    }

    public void cancel() {
        initParams();
        if (!isNullListener())
            mIDownloadInfoChangeListener.onCancelDownload();
    }

    public void finish() {
        if (!isNullListener())
            mIDownloadInfoChangeListener.onFinishDownload();
    }


    public void resetDownloadInfo() {
        initParams();
    }

    public int getFileLength() {
        return mFileLength;
    }

    public void setFileLength(int fileLength) {
        mFileLength = fileLength;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        if (!isNullListener())
            mIDownloadInfoChangeListener.onProgressChange(mProgress);
        this.mProgress = mProgress;
    }

    public int getVelocity() {
        return mVelocity;
    }

    public void setVelocity(int mVelocity) {
        if (!isNullListener())
            mIDownloadInfoChangeListener.onVelocityChange(mVelocity);
        this.mVelocity = mVelocity;
    }

    public int getFileDownloadedLength() {
        return mFileDownloadedLength;
    }

    public void setFileDownloadedLength(int length) {
        if (!isNullListener())
            mIDownloadInfoChangeListener.onFileCurrentLengthChangeListener(length);
        this.mFileDownloadedLength = length;
    }

    public String getSavedPath() {
        return mSavedPath;
    }

    public void setSavedPath(String mSavedPath) {
        this.mSavedPath = mSavedPath;
    }


    public void setDownloadInfoChangeListener(IDownloadInfoChangeListener downloadInfoChangeListener) {

        this.mIDownloadInfoChangeListener = downloadInfoChangeListener;
    }

    private boolean isNullListener() {

        return mIDownloadInfoChangeListener == null;

    }

    public interface IDownloadInfoChangeListener {

        void onStartDownload();

        void onStopDownload();

        void onCancelDownload();

        void onFinishDownload();

        void onProgressChange(int progress);

        void onVelocityChange(int velocity);

        void onFileCurrentLengthChangeListener(int fileLength);

    }

}
