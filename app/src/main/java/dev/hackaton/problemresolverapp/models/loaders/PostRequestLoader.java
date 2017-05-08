package dev.hackaton.problemresolverapp.models.loaders;

import android.app.LoaderManager;

import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sbt-markin-aa on 08.05.17.
 */

public class PostRequestLoader extends AsyncTaskLoader<String> {
    public static final String URL_FOR_POST_REQUEST = "post_url";
    HttpURLConnection urlConnection;
    String mStringUrl;

    public PostRequestLoader(Context context, String stringUrl) {
        super(context);
        mStringUrl = stringUrl;
    }

    @Override
    public String loadInBackground() {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(mStringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);

    }
}
