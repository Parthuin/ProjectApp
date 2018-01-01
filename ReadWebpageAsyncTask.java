package com.example.blalonde9489.projectapp;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Brandon Lalonde on 1/1/2018.
 */

public class ReadWebpageAsyncTask extends Activity {
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().toString();
            }

            return "Download failed";
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
    }
}

    // Triggered via a button in your layout
    public void onClick(View view) {
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute(new String[] { "https://www.pokemon.com/us/pokedex/" });

    }
}
