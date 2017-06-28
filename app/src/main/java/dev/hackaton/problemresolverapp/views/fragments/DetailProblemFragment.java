package dev.hackaton.problemresolverapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.views.activities.DetailProblemActivity;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemFragment extends Fragment {

    private GetAnswerAboutProblemArea mGetAnswerAboutProblemArea;

    public static DetailProblemFragment newInstance(Bundle bundle){
        DetailProblemFragment fragment = new DetailProblemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        mGetAnswerAboutProblemArea = (GetAnswerAboutProblemArea) bundle.getSerializable(DetailProblemActivity.ANSWER_ABOUT_PROBLEM_AREA_BUNDLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
