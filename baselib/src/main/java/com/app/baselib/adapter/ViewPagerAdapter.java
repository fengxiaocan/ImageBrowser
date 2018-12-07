package com.app.baselib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewPagerAdapter<T,V extends ViewPagerAdapter.ViewPagerViewHolder>
        extends PagerAdapter
{
    private List<T> mDatas;

    public ViewPagerAdapter() {
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(T... datas) {
        if (datas != null) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            mDatas.clear();
            for (T data : datas) {
                mDatas.add(data);
            }
        }
    }

    public void setDatas(List<T> data) {
        mDatas = data;
    }

    public void addDatas(T... datas) {
        if (datas != null) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            for (T data : datas) {
                mDatas.add(data);
            }
        }
    }

    public void setDataAndNotify(List<T> data) {
        setDatas(data);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.addAll(data);
    }

    public void addDatasAndNotify(List<T> data) {
        addDatas(data);
        notifyDataSetChanged();
    }

    public void addData(T t) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(t);
    }

    public void addDataAndNotify(T t) {
        addData(t);
        notifyDataSetChanged();
    }

    public void insertData(T t,int position) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
            mDatas.add(t);
        } else {
            if (position < 0) {
                mDatas.add(0,t);
            } else if (mDatas.size() > position) {
                mDatas.add(t);
            } else {
                mDatas.add(position,t);
            }
        }
    }

    public void insertDataAndNotify(T t,int position) {
        insertData(t,position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public T getItemData(int position) {
        return mDatas.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container,int position) {
        V holder = createViewHolder(container,position);
        holder.findViewById(holder.mRootView);
        holder.initData(getItemData(position),position);
        container.addView(holder.getRootView());
        return holder;
    }

    @Override
    public void destroyItem(
            @NonNull ViewGroup container,int position,@NonNull Object object
    )
    {
        if (object instanceof ViewPagerAdapter.ViewPagerViewHolder) {
            ((ViewPagerViewHolder)object).destroyItem(container,position);
        }
        //        super.destroyItem(container,position,object);
    }

    @Override
    public boolean isViewFromObject(
            @NonNull View view,@NonNull Object object
    )
    {
        if (object instanceof ViewPagerAdapter.ViewPagerViewHolder) {
            return view == ((ViewPagerViewHolder)object).getRootView();
        }
        return false;
    }

    public abstract V createViewHolder(ViewGroup container,int position);

    public static abstract class ViewPagerViewHolder<T> {
        public View mRootView;

        public ViewPagerViewHolder(View rootView) {
            mRootView = rootView;
        }

        public ViewPagerViewHolder(ViewGroup container,int layoutRes,int position) {
            mRootView = LayoutInflater.from(container.getContext()).inflate( layoutRes, container,false);
        }

        public View getRootView() {
            return mRootView;
        }

        public void destroyItem(ViewGroup container,int position) {
            container.removeView(mRootView);
        }

        public abstract void initData(T t,int position);
        
        public abstract void findViewById(View rootView);

        public Context getContext() {
            return mRootView.getContext();
        }
    }
}
