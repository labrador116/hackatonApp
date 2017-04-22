package dev.hackaton.problemresolverapp.models.instances;

import java.util.Date;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class ProblemPhoto {

    public String getProblemPhotoName(){
        return "IMG_"+ String.valueOf(new Date().getTime())+".jpg";
    }
}
