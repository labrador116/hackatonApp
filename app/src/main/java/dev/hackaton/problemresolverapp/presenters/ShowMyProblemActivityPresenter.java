package dev.hackaton.problemresolverapp.presenters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.ShowMyProblemFragment;

/**
 * Created by sbt-markin-aa on 03.05.17.
 */

public class ShowMyProblemActivityPresenter {

    public void createFragment(Fragment fragment, AppCompatActivity activity){
        if(fragment==null){
            fragment=new ShowMyProblemFragment();
        }
       // activity.getSupportFragmentManager().beginTransaction().add(R.id.show_my_problem_fragment_container,fragment).commit();
    }
}
