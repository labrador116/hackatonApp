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
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.models.databinding.ProblemsDataBinding;
import dev.hackaton.problemresolverapp.models.instances.GetAnswerAboutProblemArea;
import dev.hackaton.problemresolverapp.models.instances.ProblemInstance;
import dev.hackaton.problemresolverapp.models.instances.ProblemPhoto;
import dev.hackaton.problemresolverapp.models.loaders.GetRequestLoader;
import dev.hackaton.problemresolverapp.views.activities.DetailProblemActivity;
import dev.hackaton.problemresolverapp.views.fragments.CreateNewProblemFragment;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by sbt-markin-aa on 22.04.17.
 */

public class CreateNewProblemFragmentPresenter{
    private ScanCallback mCallback;
    private errMessageCallback mErrCallback;

    public CreateNewProblemFragmentPresenter(Fragment fragment){
        mCallback = (ScanCallback) fragment;
        mErrCallback = (errMessageCallback) fragment;
    }

    public interface ScanCallback {
        void scanCallbackValue(String url);
    }

    public interface errMessageCallback{
        void sendErrMessage(String errMessage);
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



    public GetAnswerAboutProblemArea GetRequestJsoneParse(String data){
        GetAnswerAboutProblemArea problemArea = new GetAnswerAboutProblemArea();
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject jsonStatus = jsonObject.getJSONObject("Status");
            String status = jsonStatus.getString("status");

            if (validateStatus(status)==false){
                String errMessage = jsonStatus.getString("errors");
                mErrCallback.sendErrMessage(errMessage);
                return null;
            }

            JSONObject jb = jsonObject.getJSONObject("AreaProblemList");
            int areaId = jb.getInt("areaId");
            problemArea.setZoneId(areaId);
            JSONArray jsonArray = jb.getJSONArray("problemList");

            List<ProblemInstance> problems = new ArrayList<>();
            for (int i=0; i< jsonArray.length(); i++){
                JSONObject problemInstanceJson = jsonArray.getJSONObject(i);
                ProblemInstance problemInstance = new ProblemInstance();
                problemInstance.setProblemId(problemInstanceJson.getInt("problemId"));
                problemInstance.setProblemName(problemInstanceJson.getString("problemName"));
                problems.add(problemInstance);
            }
            problemArea.setListOfProblems(problems);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return problemArea;
    }

    public void createDetailActivity(Context context, GetAnswerAboutProblemArea answer){
        Intent intent = new Intent(context, DetailProblemActivity.class);
        intent.putExtra(DetailProblemActivity.ANSWER_ABOUT_PROBLEM_AREA, answer);
        context.startActivity(intent);
    }

    private boolean validateStatus(String numStatus){
        if (numStatus.equals("OK")==false){
            return false;
        }
        return true;
    }

}
