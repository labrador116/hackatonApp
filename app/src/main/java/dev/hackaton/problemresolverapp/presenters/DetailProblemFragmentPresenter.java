package dev.hackaton.problemresolverapp.presenters;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.models.loaders.GetRequestLoader;
import dev.hackaton.problemresolverapp.models.loaders.PostRequestLoader;
import dev.hackaton.problemresolverapp.views.fragments.CreateNewProblemFragment;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;

/**
 * Created by sbt-markin-aa on 23.06.17.
 */

public class DetailProblemFragmentPresenter {
    public static final String ANSWER_TO_DIALOG = "answer_to_dialog";

    public Bundle setAnswerToBundle(GetAnswerAboutProblemArea answer) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ANSWER_TO_DIALOG, answer);
        return bundle;
    }

    public void createLoaderForPostRequest(FragmentActivity activity, DetailProblemFragment fragment) {
        activity.getSupportLoaderManager().initLoader(2, new Bundle(), fragment);
    }

}
