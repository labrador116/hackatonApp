package dev.hackaton.problemresolverapp.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.presenters.CreateNewProblemActivityPresenter;

public class CreateNewProblemActivity extends BaseApplicationActivity {

    public static void startActivity(Context context){
        Intent intent = new Intent(context, CreateNewProblemActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateNewProblemActivityPresenter.createNewFragment(this);
    }
}
