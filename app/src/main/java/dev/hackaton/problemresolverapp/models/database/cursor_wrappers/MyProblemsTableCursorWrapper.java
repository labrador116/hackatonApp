package dev.hackaton.problemresolverapp.models.database.cursor_wrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme;
import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme.MyProblemsTable;
import dev.hackaton.problemresolverapp.models.instances.Problem;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class MyProblemsTableCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MyProblemsTableCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Problem getProblem(){
        int problemId = getInt(getColumnIndex(MyProblemsTable.Columns.PROBLEM_ID));
        int zoneId = getInt(getColumnIndex(MyProblemsTable.Columns.ZONE_ID));
        String problemName = getString(getColumnIndex(MyProblemsTable.Columns.NAME_OF_PROBLEM));

        Problem problem = new Problem();
        problem.setProblemId(problemId);
        problem.setZoneId(zoneId);
        problem.setProblemName(problemName);

        return problem;
    }




}
