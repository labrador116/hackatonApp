package dev.hackaton.problemresolverapp.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.File;

import dev.hackaton.problemresolverapp.models.instances.ProblemPhoto;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragmentPresenter{

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public File getPhotoFile (Context context){
        File[] path = context.getExternalMediaDirs();
        ProblemPhoto photo = new ProblemPhoto();

        File externalFileDir = new File(path[0], photo.getProblemPhotoName());

        return externalFileDir;
    }

    public void saveOnGalerySendBroadcast(Context context, Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);
    }

    public Intent createNewProblemButtonHandler(Uri uri){
        final  Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return captureImage;
    }


}
