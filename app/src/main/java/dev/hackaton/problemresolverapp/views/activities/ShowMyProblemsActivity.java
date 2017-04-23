package dev.hackaton.problemresolverapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.ShowMyProblemFragment;

/**
 * Created by sbt-markin-aa on 23.04.17.
 */

public class ShowMyProblemsActivity extends AppCompatActivity {
    private ShowMyProblemFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_my_problem_activity);

        if(mFragment==null){
            mFragment=new ShowMyProblemFragment();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.show_my_problem_fragment_container,mFragment).commit();
    }
}
