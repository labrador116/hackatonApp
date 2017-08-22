package dev.hackaton.problemresolverapp.models.loaders;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.AsyncTaskLoader;

import java.io.File;
import java.io.IOException;

import dev.hackaton.problemresolverapp.R;
import dev.hackaton.problemresolverapp.views.fragments.DetailProblemFragment;
import dev.hackaton.problemresolverapp.views.fragments.SuccesPostRequestFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by sbt-markin-aa on 08.05.17.
 */

public class PostRequestLoader extends AsyncTaskLoader<String> {
    private static final String URL_FOR_POST_REQUEST = "http://31.148.99.128:8080/ipr/api/mobile/register/";
    private String mStringUrl;
    private File mPhotoProblemBitmap;
    private int mAreaId;
    private int mProblemTypeId;
    private String mDescription;
    private Context mContext;
    private PostRequestLoaderCancelCallback mCancelCallback;

    public PostRequestLoader(Context context, File photoProblemBitmap, int areaId, int problemTypeId, DetailProblemFragment fragment) {
        super(context);
        mPhotoProblemBitmap = photoProblemBitmap;
        mAreaId = areaId;
        mProblemTypeId = problemTypeId;
        mContext=context;
        mCancelCallback = fragment;
    }

    public PostRequestLoader(Context context, File photoProblemBitmap, int areaId, int problemTypeId, String description, DetailProblemFragment fragment) {
        super(context);
        mPhotoProblemBitmap = photoProblemBitmap;
        mAreaId = areaId;
        mProblemTypeId = problemTypeId;
        mDescription = description;
        mContext=context;
        mCancelCallback = fragment;
    }

    @Override
    public String loadInBackground() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URL_FOR_POST_REQUEST)
                .build();

        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), mPhotoProblemBitmap);
        MultipartBody ww = new MultipartBody.Builder().addFormDataPart("image", "problem_image.jpg", imageBody).build();
        RequestBody areaId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mAreaId));
        RequestBody problemTypeId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mProblemTypeId));
        IApiSendPostData sendPostData = retrofit.create(IApiSendPostData.class);
        Call<ResponseBody> call = sendPostData.sendData(ww.part(0), areaId, problemTypeId);
        try {
            retrofit2.Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }

    @Override
    public void onCanceled(String data) {
        mCancelCallback.cancelCallback();
        super.onCanceled(data);
    }

    interface IApiSendPostData {
        @Multipart
        @POST(".")
        Call<ResponseBody> sendData(@Part MultipartBody.Part image, @Part("areaId") RequestBody areaId, @Part("problemId") RequestBody problemId);
    }

    public interface PostRequestLoaderCancelCallback{
        void cancelCallback();
    }
}
