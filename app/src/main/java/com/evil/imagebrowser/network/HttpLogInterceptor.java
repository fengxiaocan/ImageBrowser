/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evil.imagebrowser.network;

import com.app.baselib.log.LogUtils;
import com.app.baselib.util.StringUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * {@linkplain OkHttpClient#interceptors() application interceptor} or as a {@linkplain
 * OkHttpClient#networkInterceptors() network interceptor}. <p> The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
public final class HttpLogInterceptor implements Interceptor {
	private static final Charset UTF8 = Charset.forName("UTF-8");
	private static final int LOG_MAX_SIZE = 1024 * 20;//大于20k的文件不打印
	private static final String TAG = "OkHttp";
	private volatile Level level;
	
	public HttpLogInterceptor(Level level) {
		this.level = level;
	}
	
	/**
	 * Returns true if the body in question probably contains human readable text. Uses a small sample
	 * of code points to detect unicode control characters commonly used in binary file signatures.
	 */
	static boolean isPlaintext(Buffer buffer) {
		try {
			Buffer prefix = new Buffer();
			long byteCount = buffer.size() < 64 ? buffer.size() : 64;
			buffer.copyTo(prefix,0,byteCount);
			for (int i = 0;i < 16;i++) {
				if (prefix.exhausted()) {
					break;
				}
				int codePoint = prefix.readUtf8CodePoint();
				if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
					return false;
				}
			}
			return true;
		} catch (EOFException e) {
			return false; // Truncated UTF-8 sequence.
		}
	}
	
	/**
	 * Returns true if the response must have a (possibly 0-length) body. See RFC 7231.
	 */
	public static boolean hasBody(Response response) {
		// HEAD requests never yield a body regardless of the response headers.
		if (response.request().method().equals("HEAD")) {
			return false;
		}
		
		int responseCode = response.code();
		if ((responseCode < 100 || responseCode >= 200)
		    && responseCode != HTTP_NO_CONTENT
		    && responseCode != HTTP_NOT_MODIFIED)
		{
			return true;
		}
		
		// If the Content-Length or Transfer-Encoding headers disagree with the response code, the
		// response is malformed. For best compatibility, we honor the headers.
		if (contentLength(response) != -1 || "chunked"
				.equalsIgnoreCase(response.header("Transfer-Encoding")))
		{
			return true;
		}
		
		return false;
	}
	
	private static long contentLength(Response response) {
		return contentLength(response.headers());
	}
	
	private static long contentLength(Headers headers) {
		return stringToLong(headers.get("Content-Length"));
	}
	
	private static long stringToLong(String s) {
		if (s == null) { return -1; }
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	static void v(String message) {
		LogUtils.v(TAG,message);
	}
	
	static void i(String message) {
		LogUtils.i(TAG,message);
	}
	
	static void d(String message) {
		LogUtils.d(TAG,message);
	}
	
	static void w(String message) {
		LogUtils.w(TAG,message);
	}
	
	static void e(String message) {
		LogUtils.e(TAG,message);
	}
	
	static String join(String... msg) {
		return StringUtils.join(msg);
	}
	
	static String join(Object... msg) {
		return StringUtils.join(msg);
	}
	
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Change the level at which this interceptor logs.
	 */
	public HttpLogInterceptor setLevel(Level level) {
		if (level == null) {
			this.level = Level.NONE;
		}
		else { this.level = level; }
		return this;
	}
	
	@Override
	public Response intercept(Chain chain) throws IOException {
		Level level = this.level;
		
		Request request = chain.request();
		if (level == Level.NONE) {
			return chain.proceed(request);
		}
		v("↘↘↘↘↘↘↘↘↘=========↓↓↓↓↓↓↓↓↓=========↙↙↙↙↙↙↙↙↙");
		boolean logBody = level == Level.BODY;
		boolean logHeaders = logBody || level == Level.HEADERS;
		
		RequestBody requestBody = request.body();
		boolean hasRequestBody = requestBody != null;
		
		Connection connection = chain.connection();
		Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
		String requestStartMessage = join("--> ",request.method()," ",request.url().toString()," ",
		                                  protocol.toString());
		if (!logHeaders && hasRequestBody) {
			requestStartMessage = join(" (",requestBody.contentLength(),"-byte body)");
		}
		i(requestStartMessage);
		
		if (logHeaders) {
			if (hasRequestBody) {
				// Request body headers are only present when installed as a network interceptor. Force
				// them to be included (when available) so there values are known.
				if (requestBody.contentType() != null) {
					d("Content-Type: " + requestBody.contentType());
				}
				if (requestBody.contentLength() != -1) {
					d("Content-Length: " + requestBody.contentLength());
				}
			}
			
			Headers headers = request.headers();
			for (int i = 0, count = headers.size();i < count;i++) {
				String name = headers.name(i);
				// Skip headers from the request body as they are explicitly logged above.
				if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length"
						.equalsIgnoreCase(name))
				{
					d(name + ": " + headers.value(i));
				}
			}
			
			if (!logBody || !hasRequestBody) {
				i("--> END " + request.method());
			}
			else if (bodyEncoded(request.headers())) {
				i("--> END " + request.method() + " (encoded body omitted)");
			}
			else {
				
				Buffer buffer = new Buffer();
				requestBody.writeTo(buffer);
				
				Charset charset = UTF8;
				MediaType contentType = requestBody.contentType();
				if (contentType != null) {
					charset = contentType.charset(UTF8);
				}
				
				i("");
				if (isPlaintext(buffer)) {
					if (requestBody.contentLength() <= LOG_MAX_SIZE) {
						w(buffer.readString(charset));
					}
					i(join("--> END ",request.method()," (",requestBody.contentLength(),
					       "-byte body)"));
				}
				else {
					i(join("--> END ",request.method()," (binary ",requestBody.contentLength(),
					       "-byte body omitted)"));
				}
				
			}
		}
		
		long startNs = System.nanoTime();
		Response response;
		try {
			response = chain.proceed(request);
		} catch (Exception e) {
			i("<-- HTTP FAILED: " + e);
			throw e;
		}
		long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
		
		ResponseBody responseBody = response.body();
		long contentLength = responseBody.contentLength();
		String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
		i(join("<-- ",response.code(),' ',response.message(),' ',response.request().url()," (",
		       tookMs,"ms",(!logHeaders ? ", " + bodySize + " body" : ""),')'));
		
		if (logHeaders) {
			Headers headers = response.headers();
			for (int i = 0, count = headers.size();i < count;i++) {
				d(headers.name(i) + ": " + headers.value(i));
			}
			
			if (!logBody || !hasBody(response)) {
				i("<-- END HTTP");
			}
			else if (bodyEncoded(response.headers())) {
				i("<-- END HTTP (encoded body omitted)");
			}
			else {
				BufferedSource source = responseBody.source();
				source.request(Long.MAX_VALUE); // Buffer the entire body.
				Buffer buffer = source.buffer();
				
				Charset charset = UTF8;
				MediaType contentType = responseBody.contentType();
				if (contentType != null) {
					try {
						charset = contentType.charset(UTF8);
					} catch (UnsupportedCharsetException e) {
						i("");
						v("Couldn't decode the response body; charset is likely malformed.");
						v("<-- END HTTP");
						return response;
					}
				}
				
				if (!isPlaintext(buffer)) {
					i("");
					i("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
					return response;
				}
				
				if (contentLength != 0) {
					i("");
					if (!contentType.toString().contains("image")) {
						e(buffer.clone().readString(charset));
					}
				}
				
				i("<-- END HTTP (" + buffer.size() + "-byte body)");
			}
		}
		v("↗↗↗↗↗↗↗↗↗=========↑↑↑↑↑↑↑↑↑=========↖↖↖↖↖↖↖↖↖");
		return response;
	}
	
	private boolean bodyEncoded(Headers headers) {
		String contentEncoding = headers.get("Content-Encoding");
		return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
	}
	
	public enum Level {
		/**
		 * No logs.
		 */
		NONE,
		/**
		 * Logs request and response lines.
		 * <p/>
		 * <p>Example:
		 * <pre>{@code
		 * --> POST /greeting http/1.1 (3-byte body)
		 *
		 * <-- 200 OK (22ms, 6-byte body)
		 * }</pre>
		 */
		BASIC,
		/**
		 * Logs request and response lines and their respective headers.
		 * <p/>
		 * <p>Example:
		 * <pre>{@code
		 * --> POST /greeting http/1.1
		 * Host: example.com
		 * Content-Type: plain/text
		 * Content-Length: 3
		 * --> END POST
		 *
		 * <-- 200 OK (22ms)
		 * Content-Type: plain/text
		 * Content-Length: 6
		 * <-- END HTTP
		 * }</pre>
		 */
		HEADERS,
		/**
		 * Logs request and response lines and their respective headers and bodies (if present).
		 * <p/>
		 * <p>Example:
		 * <pre>{@code
		 * --> POST /greeting http/1.1
		 * Host: example.com
		 * Content-Type: plain/text
		 * Content-Length: 3
		 *
		 * Hi?
		 * --> END POST
		 *
		 * <-- 200 OK (22ms)
		 * Content-Type: plain/text
		 * Content-Length: 6
		 *
		 * Hello!
		 * <-- END HTTP
		 * }</pre>
		 */
		BODY,
		
	}
}
