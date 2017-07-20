package dev.hackaton.problemresolverapp.views.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.presenters.DetailProblemFragmentPresenter;
import dev.hackaton.problemresolverapp.views.activities.DetailProblemActivity;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemFragment extends Fragment {

    private static final int DIALOG_REQUEST_CODE = 2;

    private DetailProblemFragmentPresenter mPresenter;
    private GetAnswerAboutProblemArea mGetAnswerAboutProblemArea;
    private Button mChoiceProblemButton;
    private TextView mAnswerFromDialog;
    private String mSelectedProblem;

    public static DetailProblemFragment newInstance(Bundle bundle){
        DetailProblemFragment fragment = new DetailProblemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mPresenter = new DetailProblemFragmentPresenter();
        Bundle bundle=getArguments();
        mGetAnswerAboutProblemArea = (GetAnswerAboutProblemArea) bundle.getSerializable(DetailProblemActivity.ANSWER_ABOUT_PROBLEM_AREA_BUNDLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_problem_fragment,null,false);

        mAnswerFromDialog = (TextView) view.findViewById(R.id.selected_problem_from_dialog);

        mChoiceProblemButton = (Button) view.findViewById(R.id.choice_problem_button);
        mChoiceProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceProblemDialogFragment dialogFragment = new ChoiceProblemDialogFragment();
                Bundle bundle = mPresenter.setAnswerToBundle(mGetAnswerAboutProblemArea);
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(getActivity().getSupportFragmentManager().findFragmentById(R.id.detail_problem_activity_container), DIALOG_REQUEST_CODE);
                dialogFragment.show(getActivity().getSupportFragmentManager(),"choice problem");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAnswerFromDialog.getText().length()>0){
            mSelectedProblem = mAnswerFromDialog.getText().toString();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case DIALOG_REQUEST_CODE:
                    String selectedItem = data.getStringExtra(ChoiceProblemDialogFragment.DIALOG_FRAGMENT_RESULT);
                    mAnswerFromDialog.setText(selectedItem);
                    mAnswerFromDialog.setVisibility(View.VISIBLE);
            }
        }
    }
}
