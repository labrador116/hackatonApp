package dev.hackaton.problemresolverapp.views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.Result;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemFragmentPresenter;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragment extends Fragment {
    public static final int REQUEST_PHOTO = 1;
    public static final String URI_STATE="uri_state";

    private Button mCreateProblemButton;
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

    private void scanCode(){
        mScannerView = new ZXingScannerView(getContext());
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                String res = result.getText();
                mScannerView.stopCamera();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(res));
                startActivity(browserIntent);
            }
        });

        getActivity().setContentView(mScannerView);
        mScannerView.startCamera();
    }
}
