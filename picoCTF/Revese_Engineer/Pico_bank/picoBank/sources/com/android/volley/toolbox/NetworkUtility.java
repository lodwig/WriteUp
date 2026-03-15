package com.android.volley.toolbox;

import android.os.SystemClock;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.ClientError;
import com.android.volley.Header;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.List;

/* loaded from: classes.dex */
final class NetworkUtility {
    private static final int SLOW_REQUEST_THRESHOLD_MS = 3000;

    private NetworkUtility() {
    }

    static void logSlowRequests(long requestLifetime, Request<?> request, byte[] responseContents, int statusCode) {
        if (VolleyLog.DEBUG || requestLifetime > 3000) {
            VolleyLog.d("HTTP response for request=<%s> [lifetime=%d], [size=%s], [rc=%d], [retryCount=%s]", request, Long.valueOf(requestLifetime), responseContents != null ? Integer.valueOf(responseContents.length) : "null", Integer.valueOf(statusCode), Integer.valueOf(request.getRetryPolicy().getCurrentRetryCount()));
        }
    }

    static NetworkResponse getNotModifiedNetworkResponse(Request<?> request, long requestDuration, List<Header> responseHeaders) {
        Cache.Entry entry = request.getCacheEntry();
        if (entry == null) {
            return new NetworkResponse(304, (byte[]) null, true, requestDuration, responseHeaders);
        }
        List<Header> combinedHeaders = HttpHeaderParser.combineHeaders(responseHeaders, entry);
        return new NetworkResponse(304, entry.data, true, requestDuration, combinedHeaders);
    }

    static byte[] inputStreamToBytes(InputStream in, int contentLength, ByteArrayPool pool) throws IOException {
        PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(pool, contentLength);
        byte[] buffer = null;
        try {
            buffer = pool.getBuf(1024);
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                bytes.write(buffer, 0, count);
            }
            byte[] byteArray = bytes.toByteArray();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            pool.returnBuf(buffer);
            bytes.close();
            return byteArray;
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {
                    VolleyLog.v("Error occurred when closing InputStream", new Object[0]);
                }
            }
            pool.returnBuf(buffer);
            bytes.close();
            throw th;
        }
    }

    static void attemptRetryOnException(Request<?> request, RetryInfo retryInfo) throws VolleyError {
        RetryPolicy retryPolicy = request.getRetryPolicy();
        int oldTimeout = request.getTimeoutMs();
        try {
            retryPolicy.retry(retryInfo.errorToRetry);
            request.addMarker(String.format("%s-retry [timeout=%s]", retryInfo.logPrefix, Integer.valueOf(oldTimeout)));
        } catch (VolleyError e) {
            request.addMarker(String.format("%s-timeout-giveup [timeout=%s]", retryInfo.logPrefix, Integer.valueOf(oldTimeout)));
            throw e;
        }
    }

    static class RetryInfo {
        private final VolleyError errorToRetry;
        private final String logPrefix;

        private RetryInfo(String logPrefix, VolleyError errorToRetry) {
            this.logPrefix = logPrefix;
            this.errorToRetry = errorToRetry;
        }
    }

    static RetryInfo shouldRetryException(Request<?> request, IOException exception, long requestStartMs, HttpResponse httpResponse, byte[] responseContents) throws VolleyError {
        if (exception instanceof SocketTimeoutException) {
            return new RetryInfo("socket", new TimeoutError());
        }
        if (exception instanceof MalformedURLException) {
            throw new RuntimeException("Bad URL " + request.getUrl(), exception);
        }
        if (httpResponse != null) {
            int statusCode = httpResponse.getStatusCode();
            VolleyLog.e("Unexpected response code %d for %s", Integer.valueOf(statusCode), request.getUrl());
            if (responseContents != null) {
                List<Header> responseHeaders = httpResponse.getHeaders();
                NetworkResponse networkResponse = new NetworkResponse(statusCode, responseContents, false, SystemClock.elapsedRealtime() - requestStartMs, responseHeaders);
                if (statusCode == 401 || statusCode == 403) {
                    return new RetryInfo("auth", new AuthFailureError(networkResponse));
                }
                if (statusCode >= 400 && statusCode <= 499) {
                    throw new ClientError(networkResponse);
                }
                if (statusCode >= 500 && statusCode <= 599 && request.shouldRetryServerErrors()) {
                    return new RetryInfo("server", new ServerError(networkResponse));
                }
                throw new ServerError(networkResponse);
            }
            return new RetryInfo("network", new NetworkError());
        }
        if (request.shouldRetryConnectionErrors()) {
            return new RetryInfo("connection", new NoConnectionError());
        }
        throw new NoConnectionError(exception);
    }
}
