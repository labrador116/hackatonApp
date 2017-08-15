package dev.hackaton.problemresolverapp.presenters;

import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.CreateNewProblemFragment;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemActivityPresenter {

    public static void createNewFragment(AppCompatActivity activity) {
        CreateNewProblemFragment fragment = CreateNewProblemFragment.newInstance();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
