package dev.hackaton.problemresolverapp.models.database.scheme;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemsDataBaseScheme {
    public static final class MyProblemsTable{
        public static final String NAME = "my_problems";

        public static final class Columns {
            public static final String REQUEST_PROBLEM_ID = "request_problem_id";
            public static final String PROBLEM_NAME = "problem_name";
            public static final String PROBLEM_STATUS = "problem_status";
        }
    }
}
