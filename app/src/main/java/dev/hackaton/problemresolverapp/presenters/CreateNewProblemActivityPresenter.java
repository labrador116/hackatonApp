package dev.hackaton.problemresolverapp.presenters;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.CreateNewProblemActivity;
import dev.hackaton.problemresolverapp.views.CreateNewProblemFragment;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemActivityPresenter {

    public void createNewFragment(AppCompatActivity activity, Fragment fragment){
        if(fragment==null){
            fragment = CreateNewProblemFragment.newInstance();
        }
        activity.getSupportFragmentManager().beginTransaction().add(R.id.new_problem_fragment_container, fragment).commit();
    }
}
