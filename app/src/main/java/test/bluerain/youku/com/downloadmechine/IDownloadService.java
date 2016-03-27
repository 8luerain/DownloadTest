package test.bluerain.youku.com.downloadmechine;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public interface IDownloadService {


    void startDownload();

    void pauseDownload();

    void cancelDownload();

    void continueDownload();

}
