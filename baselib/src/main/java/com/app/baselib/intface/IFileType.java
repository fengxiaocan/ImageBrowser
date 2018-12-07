package com.app.baselib.intface;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.app.baselib.intface.IFileType.ALARMS;
import static com.app.baselib.intface.IFileType.DCIM;
import static com.app.baselib.intface.IFileType.DOWNLOADS;
import static com.app.baselib.intface.IFileType.MOVIES;
import static com.app.baselib.intface.IFileType.MUSIC;
import static com.app.baselib.intface.IFileType.NOTIFICATIONS;
import static com.app.baselib.intface.IFileType.PICTURES;
import static com.app.baselib.intface.IFileType.PODCASTS;
import static com.app.baselib.intface.IFileType.RINGTONES;

/**
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.intface
 * @创建者: Noah.冯
 * @时间: 15:31
 * @描述： TODO
 */
@StringDef({IFileType.ALARMS, IFileType.DCIM, IFileType.DOWNLOADS, IFileType.MOVIES, IFileType.MUSIC, IFileType.NOTIFICATIONS, IFileType.PICTURES, IFileType.PODCASTS, IFileType.RINGTONES})
@Retention(RetentionPolicy.SOURCE)
public @interface IFileType{

    //警报的铃声
    String ALARMS = "Alarms";
    //相机拍摄的图片和视频保存的位置
    String DCIM = "DCIM";
    //下载文件保存的位置
    String DOWNLOADS = "Download";
    //电影保存的位置， 比如 通过google play下载的电影
    String MOVIES = "Movies";
    //音乐保存的位置
    String MUSIC = "Music";
    //通知音保存的位置
    String NOTIFICATIONS = "Notifications";
    //下载的图片保存的位置
    String PICTURES = "Pictures";
    //用于保存podcast(博客)的音频文件
    String PODCASTS = "Podcasts";
    //保存铃声的位置
    String RINGTONES = "Ringtones";
}
