package dev.hackaton.problemresolverapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.ShowMyProblemActivityPresenter;
import dev.hackaton.problemresolverapp.views.fragments.ShowMyProblemFragment;

/**
 * Created by sbt-markin-aa on 23.04.17.
 */

public class ShowMyProblemsActivity extends BaseApplicationActivity {
    private ShowMyProblemFragment mFragment;
    private ShowMyProblemActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new ShowMyProblemActivityPresenter();
        mPresenter.createFragment(mFragment, this);
    }
}
