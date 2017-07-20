package dev.hackaton.problemresolverapp.models.instances;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemInstance {
    private int mProblemId;
    private String mProblemName;

    public int getProblemId() {
        return mProblemId;
    }

    public void setProblemId(int problemId) {
        mProblemId = problemId;
    }

    public String getProblemName() {
        return mProblemName;
    }

    public void setProblemName(String problemName) {
        mProblemName = problemName;
    }
}
