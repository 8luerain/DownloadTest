package test.bluerain.youku.com.downloadmechine;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.concurrent.Executors;

import test.bluerain.youku.com.downloadmechine.bean.DownloadInfo;
import test.bluerain.youku.com.downloadmechine.bean.DownloadTask;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSetNormal;
    private Button mButtonSetSilent;

    private Button mButtonStart;
    private Button mButtonCancel;

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {
        mButtonSetNormal = (Button) findViewById(R.id.id_btn_main_normal);
        mButtonSetNormal.setOnClickListener(new SetNormalListener());
        mButtonSetSilent = (Button) findViewById(R.id.id_btn_main_silent);
        mButtonSetSilent.setOnClickListener(new SetSilentListener());
        mButtonStart = (Button) findViewById(R.id.id_btn_main_start_download);
        mButtonStart.setOnClickListener(new StartDownloadListener());
        mButtonCancel = (Button) findViewById(R.id.id_btn_main_cancel);
        mButtonCancel.setOnClickListener(new CancelDownloadListener());
        mProgressBar = (ProgressBar) findViewById(R.id.id_pgb_main);
    }


    class SetNormalListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    class SetSilentListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    class StartDownloadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            DownloadInfo info = new DownloadInfo();
            info.setmDownloadUrl("http://dl.aliyun.game.youku.com/1458181427_dfh2_youku_160(1).apk");
            info.setmSavedPath(Environment.getExternalStorageDirectory() + "/" + info.getmDownloadUrl() + ".tmp");
            info.setIDownloadService(new DownloadInfo.IDownloadInfoChangeListener() {
                @Override
                public void onProgressChange(int progress) {
                    Log.d("TAG", "onProgressChange ....... now is..." + progress + "%");
                }

                @Override
                public void onVelocityChange(int velocity) {
                    Log.d("TAG", "onVelocityChange ....... now is..." + velocity + "KB/S");
                }

                @Override
                public void onFileCurrentLengthChangeListener(int fileLength) {
                    Log.d("TAG", "onFileCurrentLengthChangeListener ....... now is..." + fileLength + "KB");
                }
            });
            DownloadTask task = new DownloadTask(info, Executors.newFixedThreadPool(2));
            task.startDownload();
        }
    }

    class CancelDownloadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
