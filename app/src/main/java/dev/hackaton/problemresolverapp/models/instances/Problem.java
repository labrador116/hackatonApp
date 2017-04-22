package dev.hackaton.problemresolverapp.models.instances;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class Problem {
    private int mProblemId;
    private int mZoneId;
    private String mProblemName;

    public int getProblemId() {
        return mProblemId;
    }

    public void setProblemId(int problemId) {
        mProblemId = problemId;
    }

    public int getZoneId() {
        return mZoneId;
    }

    public void setZoneId(int zoneId) {
        mZoneId = zoneId;
    }

    public String getProblemName() {
        return mProblemName;
    }

    public void setProblemName(String problemName) {
        mProblemName = problemName;
    }
}
