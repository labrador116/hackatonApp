package dev.hackaton.problemresolverapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.activities.CreateNewProblemActivity;

/**
 * @author Markin Andrey on 11.08.2017.
 */
public class SuccesPostRequestFragment extends Fragment {
    private Button mGetStartPageButton;

    public static SuccesPostRequestFragment newInstance() {
        Bundle args = new Bundle();
        SuccesPostRequestFragment fragment = new SuccesPostRequestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.success_post_request_fragment, container, false);
        mGetStartPageButton = (Button) view.findViewById(R.id.button);
        mGetStartPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewProblemActivity.startActivity(getContext());
            }
        });
        return view;
    }
}
