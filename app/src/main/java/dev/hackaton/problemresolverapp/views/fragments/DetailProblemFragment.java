package dev.hackaton.problemresolverapp.views.fragments;

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
        View view = inflater.inflate(R.layout.detail_problem_fragment,null,false);

        mAnswerFromDialog = (TextView) view.findViewById(R.id.selected_problem_from_dialog);

        mChoiceProblemButton = (Button) view.findViewById(R.id.choice_problem_button);
        mChoiceProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChoiceProblemDialogFragment dialogFragment = new ChoiceProblemDialogFragment();
                Bundle bundle = mPresenter.setAnswerToBundle(mGetAnswerAboutProblemArea);
                dialogFragment.setArguments(bundle);
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
}
