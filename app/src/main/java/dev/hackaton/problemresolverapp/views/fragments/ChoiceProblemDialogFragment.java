package dev.hackaton.problemresolverapp.views.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Выберите проишествие")
                .setItems((CharSequence[]) mGetAnswerAboutProblemArea.getListOfProblems().toArray(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedStringProblem = mGetAnswerAboutProblemArea.getListOfProblems().get(which);
                        TextView seletedProblem = (TextView) getActivity().findViewById(R.id.selected_problem_from_dialog);
                        seletedProblem.setText(selectedStringProblem); //ToDo костыль
                    }
                });

        return builder.create();
    }
}
