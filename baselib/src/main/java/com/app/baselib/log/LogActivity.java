package com.app.baselib.log;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.app.baselib.R;
import com.app.baselib.adapter.ListBaseAdapter;
import com.app.baselib.holder.ListViewHolder;
import com.app.baselib.impl.OnSuspendClickListener;
import com.app.baselib.ui.AppActivity;
import com.app.baselib.view.SuspendView;

import java.util.LinkedList;
import java.util.List;

/**
 * @name： FingerprintLoader
 * @package： com.evil.com.dgtle.baselib.log
 * @author: Noah.冯 QQ:1066537317
 * @time: 14:36
 * @version: 1.1
 * @desc： 查看日志的界面
 */

public class LogActivity extends AppActivity {

    private ListView mLv;
    private LinkedList<LogInfo> mLogCache;
    private MyAdapterList mMyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();
    }

    private void initView() {
        mLv = (ListView)findViewById(R.id.lv);
        mLogCache = LogCache.getInstance().getLogCache();
        mMyAdapter = new MyAdapterList(this,mLogCache);
        mLv.setAdapter(mMyAdapter);
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent,View view,int position,long id
            )
            {
                Bundle bundle = new Bundle();
                bundle.putString("html",mLogCache.get(position).log);
                openActivity(HtmlTextActivity.class,bundle);
            }
        });
        Button button = new Button(this);
        button.setText("清除日志");
        SuspendView suspendView = new SuspendView(this,button,600,800);
        suspendView.setOnClickListener(new OnSuspendClickListener() {
            @Override
            public void onClick(SuspendView view) {
                LogUtils.clearCache();
                LogUtils.flush();
                mMyAdapter.removeAll();
            }
        });
        suspendView.show();
    }

    public static class ViewHolder extends ListViewHolder {

        public View rootView;
        public TextView mTvTime;
        public TextView mTvTitle;
        public TextView mMoreLogView;

        public ViewHolder(Context context,int layoutID) {
            super(context,layoutID);
            this.rootView = rootView;
            this.mTvTime = (TextView)findViewById(R.id.tv_time);
            this.mTvTitle = (TextView)findViewById(R.id.tv_title);
            this.mMoreLogView = (TextView)findViewById(R.id.tv_content);
        }
    }

    private class MyAdapterList extends ListBaseAdapter<LogInfo,ViewHolder> {

        public MyAdapterList(Context context,List<LogInfo> data) {
            super(context,data);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int position) {
            return new ViewHolder(viewGroup.getContext(),R.layout.item_log);
        }

        @Override
        public void onBindData(ViewHolder holder,int position) {
            LogInfo info = getItem(position);
            int color;
            switch (info.type) {
                default:
                case LogUtils.VERBOSE:
                    color = LogUtils.VERBOSE_COLOR;
                    break;
                case LogUtils.DEBUG:
                    color = LogUtils.DEBUG_COLOR;
                    break;
                case LogUtils.INFO:
                    color = LogUtils.INFO_COLOR;
                    break;
                case LogUtils.WARN:
                    color = LogUtils.WARN_COLOR;
                    break;
                case LogUtils.ERROR:
                    color = LogUtils.ERROR_COLOR;
                    break;
            }
            holder.mTvTime.setTextColor(color);
            holder.mTvTime.setText(info.time);
            holder.mTvTitle.setTextColor(color);
            holder.mTvTitle.setText(info.getType() + " : " + info.TAG);
            holder.mMoreLogView.setText(info.log);
            holder.mMoreLogView.setTextColor(color);
        }
    }
}
