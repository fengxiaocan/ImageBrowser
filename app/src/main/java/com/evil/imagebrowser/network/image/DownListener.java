package com.evil.imagebrowser.network.image;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public interface DownListener {
    void  start();

    void progress(long total,long progress);

    void complete(String path);

    void error();
}
