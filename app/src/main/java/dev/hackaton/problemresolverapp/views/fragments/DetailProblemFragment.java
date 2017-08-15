package dev.hackaton.problemresolverapp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.models.loaders.PostRequestLoader;
import dev.hackaton.problemresolverapp.presenters.DetailProblemActivityPresenter;
import dev.hackaton.problemresolverapp.presenters.DetailProblemFragmentPresenter;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, PostRequestLoader.PostRequestLoaderCancelCallback {

    private static final int DIALOG_REQUEST_CODE = 2;

    private static final String ANSWER_FROM_DIALOG = "mAnswerFromDialog";
    private static final String DESCRIPTION_EDIT_TEXT = "mDescriptionEditText";

    private DetailProblemFragmentPresenter mPresenter;
    private GetAnswerAboutProblemArea mGetAnswerAboutProblemArea;
    private Button mChoiceProblemButton;
    private TextView mAnswerFromDialog;
    private EditText mDescriptionEditText;
    private LinearLayout mProblemDescriptionLinearLayout;
    private Button mStartPostRequestButton;
    private ProgressBar mSendPostRequestProgressBar;
    private Uri mPhotoProblemUri;
    private android.support.v4.content.Loader<String> mLoader;
    //ToDo можно использовать для хранения в списке действующих заявок
    private String mSelectedProblem;
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
        mGetAnswerAboutProblemArea = (GetAnswerAboutProblemArea) bundle.getSerializable(DetailProblemActivityPresenter.ANSWER_ABOUT_PROBLEM_AREA_BUNDLE);
        mPhotoProblemUri = bundle.getParcelable(DetailProblemActivityPresenter.PHOTO_PROBLEM_URI);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_problem_fragment, null, false);

        mAnswerFromDialog = (TextView) view.findViewById(R.id.selected_problem_from_dialog);
        mDescriptionEditText = (EditText) view.findViewById(R.id.problem_description_edit_text);
        mProblemDescriptionLinearLayout = (LinearLayout) view.findViewById(R.id.problem_description_linear_layout);
        mSendPostRequestProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mChoiceProblemButton = (Button) view.findViewById(R.id.choice_problem_button);
        mChoiceProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceProblemDialogFragment dialogFragment = new ChoiceProblemDialogFragment();
                Bundle bundle = mPresenter.setAnswerToBundle(mGetAnswerAboutProblemArea);
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(getActivity().getSupportFragmentManager().
                        findFragmentById(R.id.fragment_container), DIALOG_REQUEST_CODE);
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

        if (savedInstanceState != null) {
            if (savedInstanceState.getString(ANSWER_FROM_DIALOG) != null) {
                mAnswerFromDialog.setVisibility(View.VISIBLE);
                mAnswerFromDialog.setText(savedInstanceState.getString(ANSWER_FROM_DIALOG));
            }
            if (savedInstanceState.getString(DESCRIPTION_EDIT_TEXT) != null) {
                mDescriptionEditText.setVisibility(View.VISIBLE);
                mDescriptionEditText.setText(savedInstanceState.getString(DESCRIPTION_EDIT_TEXT));
            }
        }

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
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(ANSWER_FROM_DIALOG, mAnswerFromDialog
                .getText()
                .toString());
        outState.putString(DESCRIPTION_EDIT_TEXT, mDescriptionEditText
                .getText()
                .toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        File photoFile = new File(mPhotoProblemUri.getPath());
        if (!mDescriptionEditText.getText().toString().isEmpty()) {
            mLoader = new PostRequestLoader(
                    getContext(),
                    photoFile,
                    mGetAnswerAboutProblemArea.getZoneId(),
                    mSelectedIdProblem,
                    mDescriptionEditText.getText().toString(),
                    this);
        } else {
            mLoader = new PostRequestLoader(
                    getContext(),
                    photoFile,
                    mGetAnswerAboutProblemArea.getZoneId(),
                    mSelectedIdProblem,
                    this);
        }
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        mSendPostRequestProgressBar.setVisibility(View.GONE);
        String jsyon = data; //ToDO Создать список проблем по данным json
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().
                        add(R.id.fragment_container, SuccesPostRequestFragment.newInstance())
                        .commit();
            }
        });
    }


    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

    @Override
    public void cancelCallback() {
    }

    private void showAlerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Ошибка!")
                .setMessage("Произошла ошибка при отправке данных на сервер!");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void startPostRequest() {
        mSendPostRequestProgressBar.setVisibility(View.VISIBLE);
        mPresenter.createLoaderForPostRequest(getActivity(), this);
    }
}
