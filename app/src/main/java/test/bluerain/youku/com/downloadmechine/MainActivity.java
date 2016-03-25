package test.bluerain.youku.com.downloadmechine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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

        initView();
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

        }
    }

    class CancelDownloadListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }
}
