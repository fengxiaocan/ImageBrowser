package com.evil.imagebrowser.network.request;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import static com.dgtle.network.request.HttpConfig.ContentType;

/**
 * @项目名： SuperHttp
 * @包名： com.evil.http
 * @创建者: Noah.冯
 * @时间: 19:39
 * @描述： TODO
 */

class HttpHelper {

    public static final int BYTE_SIZE = 8 * 1024;

    /**
     * 失败回调
     *
     * @param callback
     * @param error
     */
    static void Failure(HttpParseListener callback,Throwable error) {
        if (callback != null) {
            callback.onFailure(error);
        } else {
            LOGE(error.getMessage());
        }
    }

    /**
     * 失败回调
     *
     * @param callback
     * @param startTime
     */
    static void Finish(HttpURLConnection conn,HttpParseListener callback,long startTime) {
        if (conn != null) {
            conn.disconnect();
        }
        long entTime = System.currentTimeMillis();
        if (callback != null) {
            long elapsedTime = entTime - startTime;
            callback.onFinish(elapsedTime);
        }
    }

//    //解析http请求响应
//    static void response(HttpURLConnection conn,String charsetName,HttpRequestCallback callback)
//            throws Exception
//    {
//        int responseCode = conn.getResponseCode();
//        //读取URLConnection的响应
//        if (callback != null) {
//            if (callback.disposeResponse(responseCode)) {
//                InputStream inputStream = conn.getInputStream();
//                String string = HttpHelper.parse(inputStream,charsetName);
//                callback.onResponse(responseCode,string);
//            } else {
//                InputStream inputStream = conn.getErrorStream();
//                String string = HttpHelper.parse(inputStream,charsetName);
//                callback.onErrorResponse(string);
//            }
//        }
//    }

    //解析http请求响应
    static void response(HttpURLConnection conn,HttpParams params,HttpParseListener callback)
            throws Exception
    {
        int responseCode = conn.getResponseCode();
        //读取URLConnection的响应
        if (callback != null) {
            if (callback.disposeResponse(responseCode)) {

                callback.onResponse(conn,params);

                if (callback.readPriority()) {
                    InputStream inputStream = conn.getInputStream();
                    callback.onResponse(params,inputStream);
                    close(inputStream);

                    OutputStream stream = conn.getOutputStream();
                    callback.replyResponse(params,stream);
                    close(stream);
                } else {
                    OutputStream stream = conn.getOutputStream();
                    callback.replyResponse(params,stream);
                    close(stream);

                    InputStream inputStream = conn.getInputStream();
                    callback.onResponse(params,inputStream);
                    close(inputStream);
                }
            } else {
                InputStream inputStream = conn.getErrorStream();
                callback.onErrorResponse(params,inputStream);
                close(inputStream);
            }
        }
    }

    //解析http请求响应的字符串
    static String parse(InputStream inputStream,String charsetName) throws Exception {
        byte[] buf = new byte[BYTE_SIZE];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int lenght;
        while ((lenght = inputStream.read(buf)) > 0) {
            outputStream.write(buf,0,lenght);
            outputStream.flush();
        }
        //获取服务器返回的字符串
        String string = outputStream.toString(charsetName);
        //关流
        close(outputStream);
        close(inputStream);

        return string;
    }

    static void updateFile(HttpURLConnection conn,LinkedHashMap<String,String> files)
            throws IOException
    {
        OutputStream out = conn.getOutputStream();
        if (files != null) {
            conn.setRequestProperty(
                    ContentType,
                    "multipart/form-data; boundary=" + HttpConfig.getConfig().getBoundary()
            );
            boolean isHasFilePost = false;
            for (String key : files.keySet()) {
                //读取文件上传到服务器
                File file = new File(files.get(key));
                if (file != null && file.exists()) {
                    String filename = file.getName();
                    String contentType = guessMimeType(file.getAbsolutePath());
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n");
                    strBuf.append("--");
                    strBuf.append(HttpConfig.getConfig().getBoundary());
                    strBuf.append("--");
                    strBuf.append("\r\n");
                    strBuf.append(key);
                    strBuf.append("Content-Disposition: form-data; name=\"");
                    strBuf.append(key);
                    strBuf.append("\"; filename=\"");
                    strBuf.append(filename);
                    strBuf.append("\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut,0,bytes);
                        out.flush();
                    }
                    in.close();
                    isHasFilePost = true;
                }
            }
            if (isHasFilePost) {
                StringBuffer strBuf = new StringBuffer();
                strBuf.append("\r\n");
                strBuf.append("--");
                strBuf.append(HttpConfig.getConfig().getBoundary());
                strBuf.append("--");
                strBuf.append("\r\n");
                byte[] endData = (strBuf.toString().getBytes());
                out.write(endData);
            }
        }
        out.flush();
        out.close();
    }

    static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOGE(e.getMessage());
            }
        }
    }

    static void LOGE(Object msg) {
        if (openLog()) {
            Log.e(getLogTag(),getLogMessage(msg));
        }
    }

    static void LOGD(Object msg) {
        if (openLog()) {
            Log.d(getLogTag(),getLogMessage(msg));
        }
    }

    static String getLogMessage(Object msg) {
        if (msg != null) {
            return String.valueOf(msg);
        }
        return "http message is null!";
    }

    static boolean openLog() {
        return HttpConfig.getConfig().isOpenLog();
    }

    static String getLogTag() {
        return HttpConfig.getConfig().getLogTag();
    }

    static synchronized void checkHashMap(LinkedHashMap params) {
        if (params == null) {
            params = new LinkedHashMap<>();
        }
    }

    public static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
