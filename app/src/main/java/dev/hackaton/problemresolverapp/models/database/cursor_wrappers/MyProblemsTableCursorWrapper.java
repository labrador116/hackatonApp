package dev.hackaton.problemresolverapp.models.database.cursor_wrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import dev.hackaton.problemresolverapp.models.database.scheme.ProblemsDataBaseScheme.MyProblemsTable;
import dev.hackaton.problemresolverapp.models.instances.ProblemInstance;

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

    public ProblemInstance getProblem(){
        int problemId = getInt(getColumnIndex(MyProblemsTable.Columns.REQUEST_PROBLEM_ID));
        String problemName = getString(getColumnIndex(MyProblemsTable.Columns.PROBLEM_NAME));
        String problemStatus = getString(getColumnIndex(MyProblemsTable.Columns.PROBLEM_STATUS));

        ProblemInstance problem = new ProblemInstance();
        problem.setProblemId(problemId);
        problem.setProblemName(problemName);
        problem.setProblemStatus(problemStatus);
        return problem;
    }




}
