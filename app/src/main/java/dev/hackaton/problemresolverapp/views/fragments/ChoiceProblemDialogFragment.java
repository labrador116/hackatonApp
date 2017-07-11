package dev.hackaton.problemresolverapp.views.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.presenters.DetailProblemFragmentPresenter;

/**
 * Created by sbt-markin-aa on 02.07.17.
 */

public class ChoiceProblemDialogFragment extends DialogFragment {
    public static final String DIALOG_FRAGMENT_RESULT = "dialog_fragment_result";
    private GetAnswerAboutProblemArea mGetAnswerAboutProblemArea;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGetAnswerAboutProblemArea = (GetAnswerAboutProblemArea)
                getArguments().getSerializable(DetailProblemFragmentPresenter.ANSWER_TO_DIALOG);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        CharSequence [] problemListCharSequence = new CharSequence[mGetAnswerAboutProblemArea.getListOfProblems().size()];

        for (int i = 0; i < mGetAnswerAboutProblemArea.getListOfProblems().size(); i++) {
            problemListCharSequence[i]=mGetAnswerAboutProblemArea.getListOfProblems().get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите проишествие")
                .setItems(problemListCharSequence, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedStringProblem = mGetAnswerAboutProblemArea.getListOfProblems().get(which);
                        Intent intent = new Intent();
                        intent.putExtra(DIALOG_FRAGMENT_RESULT,selectedStringProblem);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    }
                });

        return builder.create();
    }
}
