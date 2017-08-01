package dev.hackaton.problemresolverapp.models.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.AsyncTaskLoader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by sbt-markin-aa on 08.05.17.
 */

public class PostRequestLoader extends AsyncTaskLoader<String> {
    public static final String URL_FOR_POST_REQUEST = "http://62.109.16.244:8080/api/mobile/register";
    private String mStringUrl;
    private Bitmap mPhotoProblemBitmap;
    private int mAreaId;
    private int mProblemTypeId;

    public PostRequestLoader(Context context, String stringUrl, Bitmap photoProblemBitmap, int areaId, int problemTypeId) {
        super(context);
        mStringUrl = stringUrl;
        mPhotoProblemBitmap = photoProblemBitmap;
        mAreaId = areaId;
        mProblemTypeId = problemTypeId;
    }

    @Override
    public String loadInBackground() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mPhotoProblemBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_FOR_POST_REQUEST)
                .build();

        RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), byteArray);
        IApiSendPostData sendPostData = retrofit.create(IApiSendPostData.class);
        Call<Response> call = sendPostData.sendData(imageBody, String.valueOf(mAreaId), String.valueOf(mProblemTypeId));
        try {
            retrofit2.Response<Response> response = call.execute();
            return response.toString();
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

    public interface IApiSendPostData {
        @Multipart
        @POST
        Call<Response> sendData(@Part("image") RequestBody image, @Part("areaId") String areaId, @Part("problemId") String problemId);
    }
}
