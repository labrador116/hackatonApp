package dev.hackaton.problemresolverapp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.models.loaders.PostRequestLoader;
import dev.hackaton.problemresolverapp.presenters.DetailProblemFragmentPresenter;
import dev.hackaton.problemresolverapp.views.activities.DetailProblemActivity;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    private static final int DIALOG_REQUEST_CODE = 2;

    private DetailProblemFragmentPresenter mPresenter;
    private GetAnswerAboutProblemArea mGetAnswerAboutProblemArea;
    private Button mChoiceProblemButton;
    private TextView mAnswerFromDialog;
    private EditText mDescriptionEditText;
    private LinearLayout mProblemDescriptionLinearLayout;
    private Button mStartPostRequestButton;
    private Uri mPhotoProblemUri;
    private android.support.v4.content.Loader<String> mLoader;
    private String mUrlPost = "http://62.109.16.244:8080/api/mobile/register";
    private String mSelectedProblem;
    private String mDescription;
    private int mSelectedIdProblem;

    public static DetailProblemFragment newInstance(Bundle bundle) {
        DetailProblemFragment fragment = new DetailProblemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new DetailProblemFragmentPresenter();
        Bundle bundle = getArguments();
        mGetAnswerAboutProblemArea = (GetAnswerAboutProblemArea) bundle.getSerializable(DetailProblemActivity.ANSWER_ABOUT_PROBLEM_AREA_BUNDLE);
        mPhotoProblemUri = bundle.getParcelable(DetailProblemActivity.PHOTO_PROBLEM_URI);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_problem_fragment, null, false);

        mAnswerFromDialog = (TextView) view.findViewById(R.id.selected_problem_from_dialog);
        mDescriptionEditText = (EditText) view.findViewById(R.id.problem_description_edit_text);
        mProblemDescriptionLinearLayout = (LinearLayout) view.findViewById(R.id.problem_description_linear_layout);
        mChoiceProblemButton = (Button) view.findViewById(R.id.choice_problem_button);
        mChoiceProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceProblemDialogFragment dialogFragment = new ChoiceProblemDialogFragment();
                Bundle bundle = mPresenter.setAnswerToBundle(mGetAnswerAboutProblemArea);
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(getActivity().getSupportFragmentManager().findFragmentById(R.id.detail_problem_activity_container), DIALOG_REQUEST_CODE);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "choice problem");
            }
        });

        mStartPostRequestButton = (Button) view.findViewById(R.id.start_post_request_button);
        mStartPostRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostRequest();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAnswerFromDialog.getText().length() > 0) {
            mSelectedProblem = mAnswerFromDialog.getText().toString();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case DIALOG_REQUEST_CODE:
                    String selectedItem = data.getStringExtra(ChoiceProblemDialogFragment.DIALOG_FRAGMENT_RESULT);
                    int selectedItemId = data.getIntExtra(ChoiceProblemDialogFragment.DEALOG_FRAGMENT_RESULT_ID, 0);
                    mAnswerFromDialog.setText(selectedItem);
                    mAnswerFromDialog.setVisibility(View.VISIBLE);
                    mProblemDescriptionLinearLayout.setVisibility(View.VISIBLE);
                    mStartPostRequestButton.setVisibility(View.VISIBLE);
                    mSelectedProblem = selectedItem;
                    mSelectedIdProblem = selectedItemId;
            }
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        File photoFile = new File(mPhotoProblemUri.getPath());
        Bitmap bitmapPhotoFile = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        if (!mDescriptionEditText.getText().toString().isEmpty()) {
            mLoader = new PostRequestLoader(
                    getContext(),
                    mUrlPost,
                    bitmapPhotoFile,
                    mGetAnswerAboutProblemArea.getZoneId(),
                    mSelectedIdProblem,
                    mDescriptionEditText.getText().toString());
        } else {
            mLoader = new PostRequestLoader(
                    getContext(),
                    mUrlPost,
                    bitmapPhotoFile,
                    mGetAnswerAboutProblemArea.getZoneId(),
                    mSelectedIdProblem);
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        String jsyon = data;
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    private void startPostRequest() {
        mPresenter.createLoaderForPostRequest(getActivity(), this);
    }
}
