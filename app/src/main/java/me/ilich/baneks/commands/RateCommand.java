package me.ilich.baneks.commands;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public class RateCommand extends AbstractCommand<String> {

    private final String id;

    public RateCommand(Callback<String> callback, String id) {
        super(callback);
        this.id = id;
    }

    @Override
    protected String getUrl() {
        return "http://baneks.ru/rating.php";
    }

    @Override
    protected String getRequestMethod() {
        return "POST";
    }

    @Override
    protected void onBeforeRequest(HttpURLConnection httpURLConnection) {
        String urlParameters = String.format("id=%s", id);
        httpURLConnection.setDoOutput(true);
        DataOutputStream wr = null;
        try {
            wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected String parseSuccessResponse(String s) {
        return s;
    }

}
