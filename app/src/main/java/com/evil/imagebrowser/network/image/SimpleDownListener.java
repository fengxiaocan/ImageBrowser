package com.evil.imagebrowser.network.image;

import com.app.baselib.util.ToastUtils;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public class SimpleDownListener implements DownListener{
    @Override
    public void start() {
    
    }
    
    @Override
    public void progress(long total,long progress) {
    
    }
    
    @Override
    public void complete(String path) {
    
    }
    
    @Override
    public void error() {
        ToastUtils.showShort("下载失败");
    }
}
