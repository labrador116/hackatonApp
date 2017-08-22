package dev.hackaton.problemresolverapp.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.databinding.ProblemsDataBinding;
import dev.hackaton.problemresolverapp.models.instances.ProblemInstance;

/**
 * Created by sbt-markin-aa on 23.04.17.
 */

public class ShowMyProblemFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyProblemAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_my_problem_fragment, container, false);

        mRecyclerView= (RecyclerView) view.findViewById(R.id.my_problems_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI(){
        List<ProblemInstance> problems = ProblemsDataBinding.getProblems(getContext());
        mAdapter = new MyProblemAdapter(problems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class MyProblemHolder extends RecyclerView.ViewHolder{
        private TextView mProblemNameTextView;
        private TextView mStatusProblemTextView;
        private ProblemInstance mProblem;


        public MyProblemHolder(View itemView) {
            super(itemView);
            mProblemNameTextView = (TextView) itemView.findViewById(R.id.problem_name_text_view);
            mStatusProblemTextView = (TextView) itemView.findViewById(R.id.problem_status_text_view);
        }

        public void bindProblem(ProblemInstance problem){
            mProblem=problem;
            mProblemNameTextView.setText(mProblem.getProblemName());
            mStatusProblemTextView.setText(mProblem.getProblemStatus());
        }
    }

    private class MyProblemAdapter extends RecyclerView.Adapter<MyProblemHolder>{
        private List<ProblemInstance> mProblems;

        public MyProblemAdapter(List<ProblemInstance> problems){
            mProblems=problems;
        }

        @Override
        public MyProblemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_problem,parent,false);
            return new MyProblemHolder(view);
        }

        @Override
        public void onBindViewHolder(MyProblemHolder holder, int position) {
            ProblemInstance problem = mProblems.get(position);
            holder.bindProblem(problem);
        }

        @Override
        public int getItemCount() {
            return mProblems.size();
        }
    }
}
