package dev.hackaton.problemresolverapp.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import java.io.File;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.databinding.ProblemsDataBinding;
import dev.hackaton.problemresolverapp.models.instances.ProblemPhoto;
import dev.hackaton.problemresolverapp.models.loaders.GetRequestLoader;
import dev.hackaton.problemresolverapp.views.fragments.CreateNewProblemFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragmentPresenter{
    private ScanCallback mCallback;

    public CreateNewProblemFragmentPresenter(Fragment fragment){
        mCallback = (ScanCallback) fragment;
    }

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

    public void sendProblem(Context context, int problemId, int zoneId, String description){
        ProblemsDataBinding.sendProblem(context,problemId,zoneId,description);
    }

    public void scanCode(ZXingScannerView scanner, Context context, AppCompatActivity activity){
        scanner = new ZXingScannerView(context);
        scanner.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                mCallback.scanCallbackValue(result.getText());
            }
        });
        activity.setContentView(scanner);
        scanner.startCamera();
    }

    public void createLoaderForGetRequest(Loader<String> loader, AppCompatActivity activity, String urlForGet, CreateNewProblemFragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString(GetRequestLoader.URL_FOR_GET_REQUEST, urlForGet);
        activity.getSupportLoaderManager().initLoader(1,bundle, fragment);
    }

    public interface ScanCallback {
        void scanCallbackValue(String url);
    }
}
