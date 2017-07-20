package dev.hackaton.problemresolverapp.models.databinding;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import dev.hackaton.problemresolverapp.models.database.cursor_wrappers.MyProblemsTableCursorWrapper;
import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme.MyProblemsTable;
import dev.hackaton.problemresolverapp.models.instances.ProblemInstance;

/**
 * Created by sbt-markin-aa on 23.04.17.
 */

public class ProblemsDataBinding {
    public static void sendProblem(Context context, int problemId, int zoneId, String description){
        ContentValues values = new ContentValues();
        values.put(MyProblemsTable.Columns.PROBLEM_ID, problemId);
        values.put(MyProblemsTable.Columns.ZONE_ID,zoneId);
        values.put(MyProblemsTable.Columns.NAME_OF_PROBLEM, description);
        context.getContentResolver().insert(Uri.parse("content://dev.hackaton.problemresolverapp.models.database.providers.ProblemsDataBaseContentProvider"),
                values
                );
    }

    public static List<ProblemInstance> getProblems(Context context){
        Cursor cursor = context.getContentResolver().query(Uri.parse("content://dev.hackaton.problemresolverapp.models.database.providers.ProblemsDataBaseContentProvider"),
                null,
                null,
                null,
                null);

        cursor.moveToFirst();
        List<ProblemInstance> problems = new ArrayList<>();

        try{
            while (!cursor.isAfterLast()){
               problems.add(new MyProblemsTableCursorWrapper(cursor).getProblem());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return problems;
    }
}
