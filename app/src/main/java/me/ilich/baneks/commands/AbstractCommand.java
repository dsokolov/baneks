package me.ilich.baneks.commands;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractCommand<T> {

    private AsyncTask<Void, Void, Response<T>> asyncTask = null;
    private final SoftReference<Callback<T>> callbackRef;

    protected AbstractCommand(Callback<T> callback) {
        this.callbackRef = new SoftReference<>(callback);
    }

    public void execute() {
        asyncTask = new AsyncTask<Void, Void, Response<T>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Callback<T> callback = callbackRef.get();
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
                    int code = httpURLConnection.getResponseCode();
                    if (code == 200) {
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
                    Callback<T> callback = callbackRef.get();
                    if (callback != null) {
                        callback.onFail(response.errorResponse);
                    }
                }
                if (response.successResponse != null) {
                    Callback<T> callback = callbackRef.get();
                    if (callback != null) {
                        callback.onSuccess(response.successResponse);
                    }
                }
                Callback<T> callback = callbackRef.get();
                if (callback != null) {
                    callback.onFinish();
                }
            }

        };
        asyncTask.execute();
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
