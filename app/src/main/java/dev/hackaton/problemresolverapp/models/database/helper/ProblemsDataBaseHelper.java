package dev.hackaton.problemresolverapp.models.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme;

import static dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme.*;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemsDataBaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "problems.db";

    public ProblemsDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MyProblemsTable.NAME+
                " ( "+
                "_id integer primary key autoincrement, "+
                MyProblemsTable.Columns.PROBLEM_ID + ", "+
                MyProblemsTable.Columns.ZONE_ID + ", " +
                MyProblemsTable.Columns.NAME_OF_PROBLEM +
                " ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
