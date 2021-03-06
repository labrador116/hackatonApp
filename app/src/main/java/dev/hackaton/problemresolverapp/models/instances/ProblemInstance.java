package dev.hackaton.problemresolverapp.models.instances;

import java.io.Serializable;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemInstance implements Serializable {
    private int mProblemId;
    private String mProblemName;
    private String mProblemStatus;

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

    public String getProblemStatus() {
        return mProblemStatus;
    }

    public void setProblemStatus(String problemStatus) {
        mProblemStatus = problemStatus;
    }
}
