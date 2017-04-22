package dev.hackaton.problemresolverapp.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemActivityPresenter;

public class CreateNewProblemActivity extends AppCompatActivity {
    private CreateNewProblemActivityPresenter mPresenter;
    private CreateNewProblemFragment mFragment;

    public CreateNewProblemActivity(){
        mPresenter = new CreateNewProblemActivityPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_problem_activity);

        mPresenter.createNewFragment(this, mFragment);
    }
}
