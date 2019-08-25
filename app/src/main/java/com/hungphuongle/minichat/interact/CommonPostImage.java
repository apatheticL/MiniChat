package com.hungphuongle.minichat.interact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hungphuongle.minichat.model.request.BaseResponse;
import com.hungphuongle.minichat.model.request.StatusResponse;
import com.hungphuongle.minichat.ui.home.HomeActivity;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonPostImage {
    public static void postImage(String path, final StatusResponse statusResponse, final Activity activity, final ImageView imageView) {
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );
        final MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        Common.getUserService().upload(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                statusResponse.setAttachments( response.body());
                Glide.with(activity)
                        .load(Common.getLinkImage(statusResponse.getAttachments()))
                        .into(imageView);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public static String getPath(Context context, Intent intent){
        String path;
        Cursor c = context.getContentResolver().query(intent.getData(), new String[]{"_data"}, null, null, null);
        if ( c == null ){
            return "";
        }
        c.moveToFirst();
        path = c.getString(c.getColumnIndex("_data"));
        return path;
    }
}
