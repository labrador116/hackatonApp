package dev.hackaton.problemresolverapp.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dev.hackaton.problemresolverapp.R;

/**
 * @author Markin Andrey on 11.08.2017.
 */
public class BaseApplicationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout_activity);
    }
}
