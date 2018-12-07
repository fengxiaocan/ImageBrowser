package com.evil.imagebrowser.network.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.app.base.set.CacheConfig;
import com.app.base.utils.GlideUtils;
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
public class DownImage {
	private String url;
	private ImageView mImageView;
	private Context mContext;
	private @DrawableRes
	int errorImage;
	private @DrawableRes
	int loadImage;
	private String path;
	private DownProgress mDownProgress;
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
			if (mDownProgress != null)
			mDownProgress.complete();
			isDown = true;
			GlideUtils.INSTANCE.load(mContext,path,errorImage,mImageView);
		}
		
		@Override
		protected void error(BaseDownloadTask task,Throwable e) {
			if (mDownProgress != null)
			mDownProgress.error();
			GlideUtils.INSTANCE.loadDrawable(mContext,errorImage,mImageView);
		}
	};
	
	private DownImage(Builder builder) {
		url = builder.url;
		mImageView = builder.imageView;
		mContext = builder.context;
		errorImage = builder.errorImage;
		loadImage = builder.loadImage;
		mDownProgress = builder.downProgress;
		String codeMd5 = Md5Utils.codeMd5(url);
		
		File imageFile = CacheConfig.INSTANCE.createImageFile(codeMd5);
		path = imageFile.getAbsolutePath();
		if (imageFile.exists() && imageFile.length() > 0) {
			isDown = true;
		}
		start();
	}
	
	public static Builder with(Context context) {
		return new Builder(context);
	}
	
	public DownImage start() {
		if (isDown ) {
			GlideUtils.INSTANCE.load(mContext,path,errorImage,mImageView);
			mDownProgress.complete();
			return this;
		}
		GlideUtils.INSTANCE.loadDrawable(mContext,loadImage,mImageView);
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
		private ImageView imageView;
		private Context context;
		private int errorImage;
		private int loadImage;
		private DownProgress downProgress;
		
		private Builder(Context context) {
			this.context = context;
		}
		
		public Builder url(String val) {
			url = val;
			return this;
		}
		
		public Builder into(ImageView val) {
			imageView = val;
			return this;
		}
		
		public Builder listener(DownProgress val) {
			downProgress = val;
			return this;
		}
		
		public Builder errorImage(@DrawableRes int val) {
			errorImage = val;
			return this;
		}
		
		public Builder loadImage(@DrawableRes int val) {
			loadImage = val;
			return this;
		}
		
		public DownImage start() {
			return new DownImage(this);
		}
	}
}
