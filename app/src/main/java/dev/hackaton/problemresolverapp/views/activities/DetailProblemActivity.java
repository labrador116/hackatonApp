package dev.hackaton.problemresolverapp.views.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.presenters.DetailProblemActivityPresenter;
import dev.hackaton.problemresolverapp.views.fragments.ChoiceProblemDialogFragment;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemActivity extends AppCompatActivity  {
    public static final String ANSWER_ABOUT_PROBLEM_AREA="answer_about_problem_area";
    public static final String ANSWER_ABOUT_PROBLEM_AREA_BUNDLE="answer_about_problem_area_bundle";
    public static final String PHOTO_PROBLEM_URI = "photoProblemUri";
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_problem_activity);
        DetailProblemActivityPresenter.startDetailProblemFragment(this);
    }
}
