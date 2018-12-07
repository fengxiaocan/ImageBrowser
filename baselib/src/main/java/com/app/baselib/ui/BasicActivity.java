package com.app.baselib.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.app.baselib.AdapterUtils;
import com.app.baselib.intface.IHandler;
import com.app.baselib.intface.IRequestPermission;
import com.app.baselib.manager.FontRouter;
import com.app.baselib.pmsion.PermissionsUtils;
import com.app.baselib.util.HandlerUtils;
import com.app.baselib.util.ToastUtils;

import static com.app.baselib.pmsion.PermissionsUtils.checkAndRequestPermissions;

/**
 * The type Basic activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 11 :48
 * @描述： 基类activity
 */
public class BasicActivity extends AppCompatActivity implements View.OnClickListener, IRequestPermission {

	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		AdapterUtils.adapterDefault(this);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(newBase);
		//		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
		FontRouter.attachBaseActivity(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	/**
	 * 获取bundle
	 *
	 * @return bundle bundle
	 */
	public Bundle getBundle() {
		Intent intent = getIntent();
		return intent.getBundleExtra("Bundle");
	}
	
	/**
	 * 获取上下文
	 *
	 * @return context context
	 */
	public Context getContext() {
		return this;
	}
	
	/**
	 * 获取当前activity的快捷方式
	 *
	 * @return activity activity
	 */
	public Activity getActivity() {
		return this;
	}
	
	/**
	 * 获取根节点View
	 *
	 * @return view view
	 */
	public View getRootView() {
		return ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
	}
	
	/**
	 * 获取根节点Group
	 *
	 * @return view view
	 */
	public View getRootViewGroup() {
		return getWindow().getDecorView().findViewById(android.R.id.content);
	}
	
	/**
	 * 打开一个界面
	 *
	 * @param clazz the clazz
	 * @param isFinish the is finish
	 */
	public void openActivity(Class<? extends Activity> clazz,boolean isFinish) {
		openActivity(clazz,null,isFinish);
	}
	
	/**
	 * 打开一个界面
	 *
	 * @param clazz the clazz
	 * @param bundle the bundle
	 */
	public void openActivity(Class<? extends Activity> clazz,Bundle bundle) {
		openActivity(clazz,bundle,false);
	}
	
	/**
	 * 打开一个界面
	 *
	 * @param clazz the clazz
	 */
	public void openActivity(Class<? extends Activity> clazz) {
		openActivity(clazz,null,false);
	}
	
	/**
	 * 打开一个界面
	 *
	 * @param clazz the clazz
	 * @param bundle the bundle
	 * @param isFinish the is finish
	 */
	public void openActivity(
			Class<? extends Activity> clazz,Bundle bundle,boolean isFinish)
	{
		Intent intent = new Intent(this,clazz);
		if (bundle != null) {
			intent.putExtra("Bundle",bundle);
		}
		startActivity(intent);
		if (isFinish) {
			finish();
		}
	}
	
	/**
	 * 打开一个等待返回结果的界面
	 *
	 * @param clazz the clazz
	 * @param bundle the bundle
	 * @param requestCode the request code
	 */
	public void openActivityForResult(
			Class<? extends Activity> clazz,Bundle bundle,int requestCode)
	{
		Intent intent = new Intent(this,clazz);
		if (bundle != null) {
			intent.putExtra("Bundle",bundle);
		}
		startActivityForResult(intent,requestCode);
	}
	
	/**
	 * 打开一个等待返回结果的界面
	 *
	 * @param clazz the clazz
	 * @param requestCode the request code
	 */
	public void openActivityForResult(
			Class<? extends Activity> clazz,int requestCode)
	{
		openActivityForResult(clazz,null,requestCode);
	}
	
	/**
	 * 新建一个bundle
	 *
	 * @return bundle bundle
	 */
	public Bundle newBundle() {
		return new Bundle();
	}
	
	/**
	 * 设置点击事件
	 *
	 * @param view the view
	 */
	public void setOnClick(View view) {
		if (view != null) {
			view.setOnClickListener(this);
		}
	}
	
	/**
	 * Set on click.
	 *
	 * @param views the views
	 */
	public void setOnClick(View... views) {
		for (View view : views) {
			if (view != null) {
				view.setOnClickListener(this);
			}
		}
	}
	
	/**
	 * 申请权限
	 *
	 * @param requestCode 申请码
	 * @param permissions 权限组
	 */
	public void requestPermissions(
			int requestCode,@Nullable String[] permissions)
	{
		checkAndRequestPermissions(this,requestCode,permissions,this);
	}
	
	@Override
	public void requestPermissions(int requestCode,@Nullable String permissions) {
		checkAndRequestPermissions(this,requestCode,permissions,this);
	}
	
	/**
	 * 申请权限
	 *
	 * @param requestCode 申请码
	 * @param permissions 权限组
	 * @param requestPermissionCause 申请权限的原因
	 */
	public void requestPermissions(
			int requestCode,@Nullable String permissions,String requestPermissionCause)
	{
		checkAndRequestPermissions(this,requestCode,permissions,this,requestPermissionCause);
	}
	
	@Override
	public void onRequestPermissionsResult(
			int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode,permissions,grantResults);
		PermissionsUtils.checkPermissions(this,requestCode,permissions,this);
	}
	
	/**
	 * 申请权限成功
	 *
	 * @param requestCode 申请码
	 */
	@Override
	public void requestPremissionsSuccess(int requestCode) {
	
	}
	
	/**
	 * 申请权限失败
	 *
	 * @param requestCode 申请码
	 * @param permissions 权限组
	 */
	@Override
	public void requestPremissionsDefeat(
			int requestCode,@Nullable String[] permissions)
	{
	
	}
	
	/**
	 * 有权限
	 *
	 * @param requestCode 申请码
	 */
	@Override
	public void hasPremissions(int requestCode) {
		requestPremissionsSuccess(requestCode);
	}
	
	/**
	 * 没有权限
	 *
	 * @param requestCode 申请码
	 * @param permissions 权限组
	 */
	@Override
	public void nonePremissions(int requestCode,@Nullable String[] permissions) {
		requestPremissionsDefeat(requestCode,permissions);
	}
	
	@Override
	public void onClick(View v) {
	
	}
	
	/**
	 * 弹个吐司
	 *
	 * @param msg the msg
	 */
	public void toast(String msg) {
		ToastUtils.showShort(msg);
	}
	
	/**
	 * 弹个吐司
	 *
	 * @param msg the msg
	 */
	public void toast(@StringRes int msg) {
		ToastUtils.showShort(getString(msg));
	}
	
	/**
	 * 注册handler
	 *
	 * @param handler the handler
	 */
	public void registerHandler(IHandler handler) {
		HandlerUtils.registerHandler(handler);
	}
	
	/**
	 * 取消注册handler
	 *
	 * @param handler the handler
	 */
	public void unregisterHandler(IHandler handler) {
		HandlerUtils.unregisterHandler(handler);
	}
	
	/**
	 * Set full screen.
	 * 设置是否全屏
	 *
	 * @param isFullScreen the is full screen
	 */
	public void setFullScreen(boolean isFullScreen) {
		if (!isFullScreen) {//设置为非全屏
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(lp);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
		else {//设置为全屏
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			getWindow().setAttributes(lp);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
		
	}
	
	public ProgressDialog getProgressDialog() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
		}
		return mProgressDialog;
	}
	
	public void dismissProgress() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}
}
