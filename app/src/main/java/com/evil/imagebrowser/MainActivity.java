package com.evil.imagebrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evil.imagebrowser.api.API;
import com.evil.imagebrowser.network.RetrofigUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
}
