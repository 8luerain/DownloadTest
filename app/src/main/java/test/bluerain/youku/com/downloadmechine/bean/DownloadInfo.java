package test.bluerain.youku.com.downloadmechine.bean;

import test.bluerain.youku.com.downloadmechine.IDownloadService;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class DownloadInfo {

    public String mDownloadUrl;

    public int mProgress;

    public int mVelocity;

    public int mFileCurrentLength;

    public String mSavedPath;

    public IDownloadInfoChangeListener mIDownloadInfoChangeListener;




    public String getmDownloadUrl() {
        return mDownloadUrl;
    }

    public void setmDownloadUrl(String mDownloadUrl) {
        this.mDownloadUrl = mDownloadUrl;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        if (isNullListener())
            mIDownloadInfoChangeListener.onProgressChange(mProgress);
        this.mProgress = mProgress;
    }

    public int getmVelocity() {
        return mVelocity;
    }

    public void setmVelocity(int mVelocity) {
        if (isNullListener())
            mIDownloadInfoChangeListener.onVelocityChange(mVelocity);
        this.mVelocity = mVelocity;
    }

    public int getmFileCurrentLength() {
        return mFileCurrentLength;
    }

    public void setmFileCurrentLength(int mFileCurrentLength) {
        if (isNullListener())
            mIDownloadInfoChangeListener.onFileCurrentLengthChangeListener(mFileCurrentLength);
        this.mFileCurrentLength = mFileCurrentLength;
    }

    public String getmSavedPath() {
        return mSavedPath;
    }

    public void setmSavedPath(String mSavedPath) {
        this.mSavedPath = mSavedPath;
    }



    public void setIDownloadService(IDownloadInfoChangeListener mIDownloadInfoChangeListener) {

        this.mIDownloadInfoChangeListener = mIDownloadInfoChangeListener;
    }

    private boolean isNullListener() {

        return mIDownloadInfoChangeListener == null ;

    }
    interface IDownloadInfoChangeListener {

        void onProgressChange(int progress);

        void onVelocityChange(int velocity);

        void onFileCurrentLengthChangeListener(int fileLength);

    }

}
