package dev.hackaton.problemresolverapp.views.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.CreateNewProblemFragment;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class WebViewActivity extends AppCompatActivity {
    public static final int REQUEST_ANSWER = 71;
    private static final int SELECT_PICTURE = 1;

    public static final String ANSWER_URL = "url_answer";

    private ValueCallback<Uri> mCallback;
    private ValueCallback<Uri[]> mArrayCallback;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.setWebChromeClient(mWebChromeClient);

        String url = getIntent().getStringExtra(CreateNewProblemFragment.URI_LINK);
        mWebView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ANSWER_URL, mWebView.getUrl());
        setResult(RESULT_OK, intent);
        finish();
    }

    private class MyWebViewClient extends WebViewClient{
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().getPath());
            return true;
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String pixPath = getPath(selectedImageUri);
                if (mCallback != null) {
                    mCallback.onReceiveValue(Uri.parse("file://" + pixPath));
                } else if (mArrayCallback != null) {
                    mArrayCallback.onReceiveValue(new Uri[]{Uri.parse("file://" + pixPath)});
                }
            }
        }
    }


    public String getPath(Uri uri) {
        if (uri == null) {
            return "";
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return uri.getPath();
    }

    private final WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> callback, WebChromeClient.FileChooserParams fileChooserParams) {
            runFileChooser(null, callback);
            return true;
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> callback, String accept, String capture) {
            this.openFileChooser(callback);
        }

        @SuppressWarnings("unused")
        public void openFileChooser(ValueCallback<Uri> callback, String accept) {
            this.openFileChooser(callback);
        }

        public void openFileChooser(ValueCallback<Uri> callback) {
            runFileChooser(callback, null);
        }

        public void runFileChooser(ValueCallback<Uri> callback, ValueCallback<Uri[]> arrayCallback) {
            mCallback = callback;
            mArrayCallback = arrayCallback;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
        }

    };

}
