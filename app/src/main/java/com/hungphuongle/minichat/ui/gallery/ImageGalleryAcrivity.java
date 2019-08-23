//package com.hungphuongle.minichat.ui.gallery;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.MediaStore;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.hungphuongle.minichat.R;
//import com.hungphuongle.minichat.model.ImageData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ImageGalleryAcrivity extends AppCompatActivity implements GalleryAdapter.IGalleryAdapter {
//    private RecyclerView rcImages;
//    private List<ImageData> imageDatas;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_gallery);
//        rcImages = findViewById(R.id.rc_images);
//        rcImages.setLayoutManager(new GridLayoutManager(this, 3));
//        rcImages.setAdapter(new GalleryAdapter(this));
//        loadImageData();
//    }
//
//    private void loadImageData(){
//        new AsyncTask<Void, Void, List<ImageData>>(){
//            @Override
//            protected List<ImageData> doInBackground(Void... voids) {
//                Cursor c = getContentResolver().query(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        new String[]{MediaStore.Images.Media.DATA},
//                        null,
//                        null,
//                        MediaStore.Images.Media.DATE_ADDED + " DESC"
//                );
//                List<ImageData> imageDatas = new ArrayList<>();
//                if (c != null) {
//                    int indexData = c.getColumnIndex(MediaStore.Images.Media.DATA);
//                    c.moveToFirst();
//                    while (!c.isAfterLast()) {
//                        String path = c.getString(indexData);
//                        imageDatas.add(new ImageData(path));
//                        c.moveToNext();
//                    }
//                    c.close();
//                }
//                return imageDatas;
//            }
//
//            @Override
//            protected void onPostExecute(List<ImageData> imageData) {
//                imageDatas = imageData;
//                rcImages.getAdapter().notifyDataSetChanged();
//            }
//        }.execute();
//    }
//
//
//    @Override
//    public int getCount() {
//        if (imageDatas == null ){
//            return 0;
//        }
//        return imageDatas.size();
//    }
//
//    @Override
//    public ImageData getData(int pos) {
//        return imageDatas.get(pos);
//    }
//
//    @Override
//    public void onClick(int position) {
//        Intent intent = new Intent();
//        intent.putExtra("PATH", imageDatas.get(position).getPath());
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//}
