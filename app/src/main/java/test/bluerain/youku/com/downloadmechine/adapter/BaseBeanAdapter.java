package test.bluerain.youku.com.downloadmechine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Project: DownloadTest.
 * Data: 2016/3/30.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public abstract class BaseBeanAdapter<TBean> {

    private Context mContext;
    private TBean mBean;

    private View mView;

    public BaseBeanAdapter(Context context) {
        mContext = context;
        mView = getView(LayoutInflater.from(mContext));
    }


    abstract View getView(LayoutInflater inflater);

    abstract void bindView(View view , TBean bean);

}
