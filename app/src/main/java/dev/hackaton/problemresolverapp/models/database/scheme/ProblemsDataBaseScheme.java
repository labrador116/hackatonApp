package dev.hackaton.problemresolverapp.models.database.scheme;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemsDataBaseScheme {
    public static final class MyProblemsTable{
        public static final String NAME = "my_problems";

        public static final class Columns {
            public static final String PROBLEM_ID = "problem_id";
            public static final String ZONE_ID = "zone_id";
            public static final String NAME_OF_PROBLEM = "problem_name";
        }
    }
}
