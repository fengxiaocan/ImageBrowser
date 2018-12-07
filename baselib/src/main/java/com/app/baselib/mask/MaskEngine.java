package com.app.baselib.mask;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.app.baselib.AdapterUtils;

class MaskEngine {
    private static MaskEngine ourInstance;
    private BitmapLruCache mLruCache;

    private MaskEngine() {
        //        int totalMemory = (int)Runtime.getRuntime().totalMemory();
        //        int freeMemory = (int)Runtime.getRuntime().freeMemory();
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        //        LogUtils.e("noah","totalMemory="+Formatter.formatFileSize(Utils.getContext(),totalMemory));
        //        LogUtils.e("noah","freeMemory="+Formatter.formatFileSize(Utils.getContext(),freeMemory));
        //        LogUtils.e("noah","maxMemory="+Formatter.formatFileSize(Utils.getContext(),maxMemory));
        mLruCache = new BitmapLruCache(maxMemory / 5);
    }

    public static MaskEngine getInstance() {
        synchronized (MaskEngine.class) {
            if (ourInstance == null) {
                ourInstance = new MaskEngine();
            }
        }
        return ourInstance;
    }

    public void recycle() {
        ourInstance = null;
        mLruCache = null;
    }

    public Bitmap getBitmap(
            Integer color
    )
    {
        return mLruCache.get(color);
    }

    static class BitmapLruCache extends LruCache<Integer,Bitmap> {

        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        /*当缓存大于我们设定的最大值时，会调用这个方法，我们可以用来做内存释放操作*/
        @Override
        protected void entryRemoved(boolean evicted,Integer key,Bitmap oldValue,Bitmap newValue) {
            super.entryRemoved(evicted,key,oldValue,newValue);
            if (evicted && oldValue != null) {
                oldValue.recycle();
            }
        }

        /*创建 bitmap*/
        @Override
        protected Bitmap create(Integer key) {
            Bitmap bitmap = Bitmap.createBitmap(AdapterUtils.getScreenWidth(),
                                                AdapterUtils.getScreenHeight(),
                                                Bitmap.Config.ARGB_8888
            );
            bitmap.eraseColor(key);
            return bitmap;
        }

        /*获取每个 value 的大小*/
        @Override
        protected int sizeOf(Integer key,Bitmap value) {
            //            LogUtils.e("noah","getByteCount=" + Formatter.formatFileSize(Utils.getContext(),value.getByteCount()));
            return value.getByteCount();
        }
    }
}
