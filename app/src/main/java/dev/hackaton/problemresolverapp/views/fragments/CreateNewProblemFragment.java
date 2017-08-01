package dev.hackaton.problemresolverapp.views.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.models.loaders.GetRequestLoader;
import dev.hackaton.problemresolverapp.models.loaders.PostRequestLoader;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemFragmentPresenter;
import dev.hackaton.problemresolverapp.views.activities.ShowMyProblemsActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, CreateNewProblemFragmentPresenter.ScanCallback,
        CreateNewProblemFragmentPresenter.errMessageCallback {
    public static final int REQUEST_PHOTO = 1;
    public static final String URI_STATE="uri_state";
    public static final String URI_LINK = "link";

    private Button mCreateProblemButton;
    private Button mShowMyProblemButton;
    private CreateNewProblemFragmentPresenter mPresenter;
    private Uri mUri;
    private ZXingScannerView mScannerView;
    private android.support.v4.content.Loader<String> mLoader;
    private String mUrlForGetRequestFromScanner;
    GetAnswerAboutProblemArea mGetAnswer;

    public CreateNewProblemFragment(){
        mPresenter = new CreateNewProblemFragmentPresenter(this);
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
            if(savedInstanceState.getString(URI_STATE)!=null) {
                mUri = Uri.parse(savedInstanceState.getString(URI_STATE));
            }
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
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.INTERNET},2);

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

        if(mScannerView!=null){
            mScannerView.stopCamera();
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
        if(mUri!=null) {
            mPresenter.scanCode(mScannerView,getContext(), (AppCompatActivity) getActivity());
            mPresenter.saveOnGalerySendBroadcast(getContext(),mUri);
        }
    }

    @Override
    public void scanCallbackValue(String url) {
        mUrlForGetRequestFromScanner=url;
        mPresenter.createLoaderForGetRequest(mLoader, (AppCompatActivity) getActivity(), mUrlForGetRequestFromScanner, this);
    }

    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
        switch (id){
            case 1:
                String urlGet =args.getString(GetRequestLoader.URL_FOR_GET_REQUEST);
                mLoader = new GetRequestLoader(getContext(), urlGet);
                break;
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {
        if (loader instanceof GetRequestLoader){
            mGetAnswer = mPresenter.GetRequestJsoneParse(data);

            if(mGetAnswer!=null) {
                mPresenter.createDetailActivity(getContext(), mGetAnswer, mUri);
            } else {
                this.onDestroy();
            }
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<String> loader) {

    }

    @Override
    public void sendErrMessage(String errMessage) {
        Toast.makeText(getContext(),errMessage,Toast.LENGTH_LONG).show();
    }
}
