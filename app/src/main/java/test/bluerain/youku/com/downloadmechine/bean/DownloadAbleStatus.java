package test.bluerain.youku.com.downloadmechine.bean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import test.bluerain.youku.com.downloadmechine.IDownloadService;
import test.bluerain.youku.com.downloadmechine.utils.FileUtils;
import test.bluerain.youku.com.downloadmechine.utils.NetworkFileEngine;

/**
 * Created by rain on 2016/3/28.
 */
public class DownloadAbleStatus implements IDownloadService {

    private NetworkFileEngine mNetworkFileEngine;

    private DownloadInfo mDownloadInfo;

    public DownloadAbleStatus(DownloadInfo mDownloadInfo) {
        this.mDownloadInfo = mDownloadInfo;
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
            public void onGetSuccessed(InputStream inputStream) {
                byte[] block = new byte[4096];
                BufferedInputStream inputStream1 = null;
                BufferedOutputStream outputStream = null;
                int readCount = -1;
                try {
                    inputStream1 = new BufferedInputStream(inputStream);
                    outputStream = new BufferedOutputStream(
                            new FileOutputStream(FileUtils.getFileFromPath(mDownloadInfo.mSavedPath))
                    );
                    while ((readCount = inputStream1.read(block)) != -1) {
                        int newLength = mDownloadInfo.getmFileCurrentLength() + readCount;
                        mDownloadInfo.setmFileCurrentLength(newLength);
                        outputStream.write(block, 0, readCount);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    FileUtils.closeQuilty(inputStream1);
                    FileUtils.closeQuilty(outputStream);
                }

            }

            @Override
            public void onGetError(String errorMsg) {

            }
        });
        mNetworkFileEngine.startEngin();

    }

    @Override
    public void pauseDownload() {

    }

    @Override
    public void cancelDownload() {

    }

    @Override
    public void continueDownload() {

    }
}
