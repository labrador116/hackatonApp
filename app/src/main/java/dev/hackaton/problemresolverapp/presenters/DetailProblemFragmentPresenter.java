package dev.hackaton.problemresolverapp.presenters;

import android.os.Bundle;

import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;

/**
 * Created by sbt-markin-aa on 23.06.17.
 */

public class DetailProblemFragmentPresenter {
    public static final String ANSWER_TO_DIALOG = "answer_to_dialog";

    public Bundle setAnswerToBundle (GetAnswerAboutProblemArea answer){
    Bundle bundle = new Bundle();
    bundle.putSerializable(ANSWER_TO_DIALOG, answer);
    return  bundle;
    }

}
