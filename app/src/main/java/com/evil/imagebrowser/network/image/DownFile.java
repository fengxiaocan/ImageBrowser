package com.evil.imagebrowser.network.image;

import com.app.base.set.CacheConfig;
import com.app.baselib.key.Md5Utils;
import com.app.baselib.util.Utils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public class DownFile {
	private String url;
	private String path;
	private DownListener mDownProgress;
	private boolean isDown;
	private FileDownloadSampleListener mSampleListener = new FileDownloadSampleListener() {
		@Override
		protected void progress(
				BaseDownloadTask task,int soFarBytes,int totalBytes)
		{
			if (mDownProgress != null) {
				mDownProgress.progress(totalBytes,soFarBytes);
			}
		}
		
		@Override
		protected void completed(BaseDownloadTask task) {
			if (mDownProgress != null) { mDownProgress.complete(path); }
			isDown = true;
		}
		
		@Override
		protected void error(BaseDownloadTask task,Throwable e) {
			if (mDownProgress != null) { mDownProgress.error(); }
		}
	};
	
	private DownFile(Builder builder) {
		url = builder.url;
		mDownProgress = builder.downProgress;
		String codeMd5 = Md5Utils.codeMd5(url);
		
		File imageFile = CacheConfig.INSTANCE.createImageFile(codeMd5);
		path = imageFile.getAbsolutePath();
		if (imageFile.exists() && imageFile.length() > 0) {
			isDown = true;
		}
		start();
	}
	
	public static Builder builder(String url) {
		return new Builder(url);
	}
	
	public DownFile start() {
		if (isDown) {
			mDownProgress.complete(path);
			return this;
		}
		mDownProgress.start();
		FileDownloader.setup(Utils.getContext());
		FileDownloader.getImpl().create(url).setPath(path).setListener(mSampleListener).start();
		return this;
	}
	
	public String getPath() {
		return path;
	}
	
	public static final class Builder {
		private String url;
		private DownListener downProgress;
		
		private Builder(String val) {
			url = val;
		}
		
		public Builder listener(DownListener val) {
			downProgress = val;
			return this;
		}
		
		public DownFile start() {
			return new DownFile(this);
		}
	}
}
