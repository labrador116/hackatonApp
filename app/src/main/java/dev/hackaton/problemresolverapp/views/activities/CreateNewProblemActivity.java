package dev.hackaton.problemresolverapp.views.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemActivityPresenter;

public class CreateNewProblemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_problem_activity);
        CreateNewProblemActivityPresenter.createNewFragment(this);
    }
}
