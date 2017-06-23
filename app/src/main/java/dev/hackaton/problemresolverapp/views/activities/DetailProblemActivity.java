package dev.hackaton.problemresolverapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemActivity extends AppCompatActivity {
    public static final String ANSWER_ABOUT_PROBLEM_AREA="answer_about_problem_area";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_problem_activity);

        Fragment fragment = DetailProblemFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.detail_problem_activity_container, fragment).commit();
    }
}
