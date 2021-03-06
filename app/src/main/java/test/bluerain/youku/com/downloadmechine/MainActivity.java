package test.bluerain.youku.com.downloadmechine;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executors;

import test.bluerain.youku.com.downloadmechine.bean.DownloadInfo;
import test.bluerain.youku.com.downloadmechine.bean.DownloadTask;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSetNormal;
    private Button mButtonSetSilent;

    private Button mButtonStart;
    private Button mButtonCancel;

    private TextView mTextViewSpeed;
    private ProgressBar mProgressBar;

    private DownloadTask mDownloadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        DownloadInfo info = new DownloadInfo();
        info.setDownloadUrl("http://test.gamex.mobile.youku.com/apkdowload/1457516569_1385103059_com.ecapycsw.onetouchdrawing.1381221038291.apk");
        info.setSavedPath(Environment.getExternalStorageDirectory() + "/downloadTest/" + "test" + ".apk.tmp");
        info.setDownloadInfoChangeListener(new DownloadInfo.IDownloadInfoChangeListener() {
            @Override
            public void onProgressChange(final int progress) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setMax(100);
                        mProgressBar.setProgress(progress);
                    }
                });
                Log.d("TAG", "onProgressChange ....... now is..." + progress + "%");
            }

            @Override
            public void onVelocityChange(final int velocity) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewSpeed.setText(velocity + "KB/S");
                    }
                });
                Log.d("TAG", "onVelocityChange ....... now is..." + velocity + "KB/S");
            }

            @Override
            public void onFileCurrentLengthChangeListener(int fileLength) {
//                    Log.d("TAG", "onFileCurrentLengthChangeListener ....... now is..." + fileLength + "KB");
            }

            @Override
            public void onStartDownload() {
                setButtonToStopStatus();
            }

            @Override
            public void onStopDownload() {
                setButtonToStartStatus();
            }

            @Override
            public void onCancelDownload() {
                setButtonToStartStatus();
            }

            @Override
            public void onFinishDownload() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mDownloadTask = new DownloadTask(info, Executors.newFixedThreadPool(2));
    }

    private void initView() {
        mTextViewSpeed = (TextView) findViewById(R.id.id_txv_main_download_speed);
        mButtonSetNormal = (Button) findViewById(R.id.id_btn_main_normal);
        mButtonSetNormal.setOnClickListener(new SetNormalListener());
        mButtonSetSilent = (Button) findViewById(R.id.id_btn_main_silent);
        mButtonSetSilent.setOnClickListener(new SetSilentListener());
        mButtonStart = (Button) findViewById(R.id.id_btn_main_start_download);
        setButtonToStartStatus();
        mButtonCancel = (Button) findViewById(R.id.id_btn_main_cancel);
        mButtonCancel.setOnClickListener(new CancelDownloadListener());
        mProgressBar = (ProgressBar) findViewById(R.id.id_pgb_main);
    }


    private void setButtonToStartStatus() {
        mButtonStart.setText("开始下载");
        mButtonStart.setOnClickListener(new StartDownloadListener());
    }

    private void setButtonToStopStatus() {
        mButtonStart.setText("暂停下载");
        mButtonStart.setOnClickListener(new PauseDownloadListener());
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
            Log.d("TAG", "start button click......");
            mDownloadTask.startDownload();
        }
    }


    class PauseDownloadListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Log.d("TAG", "stop button click......");
            mDownloadTask.pauseDownload();
        }
    }

    class CancelDownloadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d("TAG", "cancel button click......");
            mDownloadTask.cancelDownload();
        }
    }
}
