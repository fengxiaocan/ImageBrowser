package com.evil.imagebrowser.network.image;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public interface DownProgress {
    void  start();

    void progress(long total,long progress);

    void complete();

    void error();
}
