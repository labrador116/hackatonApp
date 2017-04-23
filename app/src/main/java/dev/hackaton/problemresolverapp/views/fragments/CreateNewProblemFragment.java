package dev.hackaton.problemresolverapp.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemFragmentPresenter;
import dev.hackaton.problemresolverapp.views.activities.ShowMyProblemsActivity;
import dev.hackaton.problemresolverapp.views.activities.WebViewActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.R.attr.description;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragment extends Fragment {
    public static final int REQUEST_PHOTO = 1;
    public static final int REQUEST_BROWSER= 11;
    public static final int REQUEST_START_ACTIVITY_WEB_VIEW=5;
    public static final String URI_STATE="uri_state";
    public static final String URI_LINK = "link";

    private Button mCreateProblemButton;
    private  Button mShowMyProblemButton;
    private CreateNewProblemFragmentPresenter mPresenter;
    private Uri mUri;
    private ZXingScannerView mScannerView;

    public CreateNewProblemFragment(){
        mPresenter = new CreateNewProblemFragmentPresenter();
    }

    public static CreateNewProblemFragment newInstance(){
        Bundle args = new Bundle();
        CreateNewProblemFragment problemFragment = new CreateNewProblemFragment();
        problemFragment.setArguments(args);
        return problemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState!=null){
            mUri = Uri.parse(savedInstanceState.getString(URI_STATE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_new_problem_fragment,container,false);

        mCreateProblemButton = (Button) view.findViewById(R.id.create_problem_button);
        mCreateProblemButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                mUri = Uri.fromFile(mPresenter.getPhotoFile(getContext()));
                Intent captureImage = mPresenter.createNewProblemButtonHandler(mUri);
                startActivityForResult(captureImage,REQUEST_PHOTO);
            }
        });

        mShowMyProblemButton = (Button) view.findViewById(R.id.show_problem_buttpn);
        mShowMyProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ShowMyProblemsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mUri!=null){
            mPresenter.saveOnGalerySendBroadcast(getContext(),mUri);
            scanCode();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView!=null){
            mScannerView.stopCamera();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mUri!=null){
            outState.putString(URI_STATE,mUri.getPath());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CreateNewProblemFragment.REQUEST_START_ACTIVITY_WEB_VIEW){
            if(data!=null) {
                String answerUrl = data.getStringExtra(WebViewActivity.ANSWER_URL);

                String problemId =Uri.parse(answerUrl).getLastPathSegment();

                GetData getData = new GetData();
                getData.execute("http://62.109.16.244:8080/api/problem/?id="+problemId);
            }
        }
    }

    private void scanCode(){
        mScannerView = new ZXingScannerView(getContext());
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                String res = result.getText();
                mScannerView.stopCamera();
                startWebView(res);
            }
        });

        getActivity().setContentView(mScannerView);
        mScannerView.startCamera();
    }

    private void startWebView(String link)  {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra(URI_LINK,link);
        startActivityForResult(intent,REQUEST_START_ACTIVITY_WEB_VIEW);
    }

    private class GetData extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();

            try {
                URL url = new URL(args[0]);
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
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                int requestId = jsonObject.getInt("requestId");
                int areaId = jsonObject.getInt("areaId");
                String description = jsonObject.getString("description");
                mPresenter.sendProblem(getContext(), requestId, areaId, description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
