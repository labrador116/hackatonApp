package dev.hackaton.problemresolverapp.models.database.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import dev.hackaton.problemresolverapp.models.database.helper.ProblemsDataBaseHelper;
import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme;

import static dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme.*;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemsDataBaseContentProvider extends ContentProvider {
    public static final UriMatcher sURI_MATCHER;
    public static final String AUTHORITY = "dev.hackaton.problemresolverapp.models.database.providers.ProblemsDataBaseContentProvider";

    private SQLiteDatabase mDatabase;

    static {
        sURI_MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
    }

    @Override
    public boolean onCreate() {
        mDatabase = new ProblemsDataBaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = mDatabase.query(MyProblemsTable.NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
                );

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        mDatabase.insert(MyProblemsTable.NAME,null,values);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
