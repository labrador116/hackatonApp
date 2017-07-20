package dev.hackaton.problemresolverapp.models.instances;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sbt-markin-aa on 08.05.17.
 */

public class GetAnswerAboutProblemArea implements Serializable{
    private int mZoneId;
    private List<ProblemInstance> mListOfProblems;

    public int getZoneId() {
        return mZoneId;
    }

    public void setZoneId(int zoneId) {
        mZoneId = zoneId;
    }

    public List<ProblemInstance> getListOfProblems() {
        return mListOfProblems;
    }

    public void setListOfProblems(List<ProblemInstance> listOfProblems) {
        mListOfProblems = listOfProblems;
    }
}
