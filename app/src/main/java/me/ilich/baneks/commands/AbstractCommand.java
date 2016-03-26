package me.ilich.baneks.commands;

import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommand<T> {

    private static final String COOKIES_HEADER = "Set-Cookie";
    private static CookieManager cookieManager = new CookieManager();

    private AsyncTask<Void, Void, Response<T>> asyncTask = null;
    //private final SoftReference<Callback<T>> callbackRef;
    private final Callback<T> callback;

    protected AbstractCommand(Callback<T> callback) {
        //this.callbackRef = new SoftReference<>(callback);
        this.callback = callback;
    }

    protected String getRequestMethod() {
        return "GET";
    }

    public void execute() {
        asyncTask = new AsyncTask<Void, Void, Response<T>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Callback<T> callback = callbackRef.get();
                if (callback != null) {
                    callback.onStart();
                }
            }

            @Override
            protected Response<T> doInBackground(Void... params) {
                Response<T> response = new Response<>();
                InputStream inputStream = null;
                try {
                    URL url = new URL(getUrl());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod(getRequestMethod());
                    if (cookieManager.getCookieStore().getCookies().size() > 0) {
                        httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";", cookieManager.getCookieStore().getCookies()));
                    }
                    onBeforeRequest(httpURLConnection);
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
                        saveCookies(httpURLConnection);
                        inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        String line = bufferedReader.readLine();
                        while (line != null) {
                            sb.append(line);
                            sb.append('\n');
                            line = bufferedReader.readLine();
                        }
                        String full = sb.toString();
                        response.successResponse = parseSuccessResponse(full);
                    } else {
                        response.errorResponse = new Non200ErrorResponse();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    response.errorResponse = new ExceptionErrorResponse(e);
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return response;
            }

            @Override
            protected void onPostExecute(Response<T> response) {
                super.onPostExecute(response);
                if (response.errorResponse != null) {
                    //Callback<T> callback = callbackRef.get();
                    if (callback != null) {
                        callback.onFail(response.errorResponse);
                    }
                }
                if (response.successResponse != null) {
                    //Callback<T> callback = callbackRef.get();
                    if (callback != null) {
                        callback.onSuccess(response.successResponse);
                    }
                }
                //Callback<T> callback = callbackRef.get();
                if (callback != null) {
                    callback.onFinish();
                }
            }

        };
        asyncTask.execute();
    }

    private void saveCookies(HttpURLConnection httpURLConnection) {
        Map<String, List<String>> headerFields = httpURLConnection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    protected void onBeforeRequest(HttpURLConnection httpURLConnection) {

    }

    protected abstract String getUrl();

    protected abstract T parseSuccessResponse(String s);

    public void cancel() {
        asyncTask.cancel(true);
    }

    private static class Response<T> {

        private T successResponse;
        private ErrorResponse errorResponse;

    }

    public interface Callback<T> {

        void onStart();

        void onSuccess(T successResponse);

        void onFail(ErrorResponse errorResponse);

        void onFinish();

    }

}
