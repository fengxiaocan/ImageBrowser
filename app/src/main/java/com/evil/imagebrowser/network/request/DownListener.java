package com.evil.imagebrowser.network.request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import static com.dgtle.network.request.HttpHelper.BYTE_SIZE;
import static com.dgtle.network.request.HttpHelper.close;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
class DownListener implements HttpParseListener {
    DownFileCallback mFileCallback;
    private long mTotalLength;

    public DownListener(DownFileCallback fileCallback) {
        mFileCallback = fileCallback;
    }

    @Override
    public void onStart(long startTime) {
        if (mFileCallback != null) {
            mFileCallback.onStart(startTime);
        }
    }

    @Override
    public boolean disposeResponse(int responseCode) {
        return responseCode >= 200 && responseCode < 400;
    }

    @Override
    public void onResponse(
            HttpURLConnection connection,HttpParams params
    ) throws IOException
    {
        int contentLength = connection.getContentLength();
        mTotalLength = contentLength;
        if (mFileCallback != null) {
            mFileCallback.onResponseLength(contentLength);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                mTotalLength = connection.getContentLengthLong();
                mFileCallback.onResponseLength(mTotalLength);
            }
            mFileCallback.onResponseMessage(connection.getResponseMessage());
        }
    }

    @Override
    public boolean readPriority() {
        return true;
    }

    @Override
    public void onResponse(HttpParams params,InputStream respone) throws Exception {
        byte[] buf = new byte[BYTE_SIZE];
        String downFile = params.getDownFilePath();
        FileOutputStream outputStream = new FileOutputStream(new File(downFile),false);
        int lenght;
        long startTime = System.currentTimeMillis();
        long downLenght = 0;
        while ((lenght = respone.read(buf)) > 0) {
            downLenght += lenght;
            outputStream.write(buf,0,lenght);

            if (mFileCallback != null) {
                if (System.currentTimeMillis()-startTime >= 1000){
                    startTime = System.currentTimeMillis();
                    mFileCallback.onProgress(mTotalLength,downLenght);
                }
            }

            outputStream.flush();
        }
        //关流
        close(outputStream);
        close(respone);
    }

    @Override
    public void replyResponse(HttpParams params,OutputStream outputStream) throws Exception {
    }

    @Override
    public void onErrorResponse(HttpParams params,InputStream error) throws Exception {
        String parse = HttpHelper.parse(error,params.getCharsetName());
        if (mFileCallback != null) {
            mFileCallback.onErrorResponse(parse);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if (mFileCallback != null) {
            mFileCallback.onFailure(e);
        }
    }

    @Override
    public void onFinish(long elapsedTime) {
        if (mFileCallback != null) {
            mFileCallback.onFinish(elapsedTime);
        }
    }
}
