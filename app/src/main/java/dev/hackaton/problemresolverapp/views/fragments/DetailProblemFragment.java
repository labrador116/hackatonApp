package dev.hackaton.problemresolverapp.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by sbt-markin-aa on 16.05.17.
 */

public class DetailProblemFragment extends Fragment {

    public static DetailProblemFragment newInstance(){
        Bundle bundle = new Bundle();
        DetailProblemFragment fragment = new DetailProblemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
