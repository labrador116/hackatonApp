package dev.hackaton.problemresolverapp.presenters;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;

/**
 * @author Markin Andrey on 03.08.2017.
 */
public class DetailProblemActivityPresenter {
    public static final String ANSWER_ABOUT_PROBLEM_AREA="answer_about_problem_area";
    public static final String ANSWER_ABOUT_PROBLEM_AREA_BUNDLE="answer_about_problem_area_bundle";
    public static final String PHOTO_PROBLEM_URI = "photoProblemUri";

    public static void startDetailProblemFragment(AppCompatActivity activity){
        GetAnswerAboutProblemArea answer = (GetAnswerAboutProblemArea) activity.getIntent().getSerializableExtra(ANSWER_ABOUT_PROBLEM_AREA);
        Uri photoProblemUri = (Uri) activity.getIntent().getParcelableExtra(PHOTO_PROBLEM_URI);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANSWER_ABOUT_PROBLEM_AREA_BUNDLE, answer);
        bundle.putParcelable(PHOTO_PROBLEM_URI, photoProblemUri);
        Fragment fragment = DetailProblemFragment.newInstance(bundle);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.detail_problem_activity_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
