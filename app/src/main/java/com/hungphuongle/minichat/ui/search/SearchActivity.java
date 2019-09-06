package com.hungphuongle.minichat.ui.search;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.hungphuongle.minichat.R;
import com.hungphuongle.minichat.model.request.MessageChatResponse;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back;
    private EditText edtSearch;
    private ProgressBar prLoading;
    private List<MessageChatResponse> listFriend;
    private String TAG = "SearchActivity";
    private TextView tvNotFound;
    private RecyclerView rcListKeySearch;
    private ListView rcListSearchResult;
    private boolean isShowListSearch = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

    }

    private void initView(){
        back=findViewById(R.id.btnBack);
        edtSearch=findViewById(R.id.edtSearch);
        prLoading=findViewById(R.id.prLoading);
        tvNotFound=findViewById(R.id.txtNotFound);
        rcListKeySearch = findViewById(R.id.rcListKeySearch);
        rcListSearchResult = findViewById(R.id.rcListSearchResult);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
//                onBackPressed();
                if (isShowListSearch)
                    onBackPressed();
                else {
                    handleShowList();
                }
                break;
            case R.id.btnSearchToolBar:
//                if (!edtSearch.getText().toString().equalsIgnoreCase("")) {
//                    handleSearchRingtone(edtSearch.getText().toString());
//                }
                break;
        }
    }

//    private void handleSearchRingtone(String keySearch) {
//        prLoading.setVisibility(View.VISIBLE);
//        listFriend.clear();
//        RetrofitUtil.getApi().getRingtoneByKey(keySearch).enqueue(new Callback<ResponseDTO<ArrayList<RingTone>>>() {
//            @Override
//            public void onResponse(Call<ResponseDTO<ArrayList<RingTone>>> call, Response<ResponseDTO<ArrayList<RingTone>>> response) {
//                prLoading.setVisibility(View.GONE);
//                if (response.code() == 200) {
//                    Log.i(TAG, "onResponse: link " + call.request().toString());
//                    Log.i(TAG, "onResponse: " + response.body());
//                    ArrayList<RingTone> lstData = response.body().getData();
//                    if(CommonUntil.isNullorEmty(lstData))
//                    {
//                        tvNotFound.setVisibility(View.VISIBLE);
//                    }else {
//                        tvNotFound.setVisibility(View.GONE);
//                        listFriend.addAll(lstData);
//
//                    }
//                    adapterLstRingtone.notifyDataSetChanged();
//                    //show list ringtone result
//                    showListResult();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDTO<ArrayList<RingTone>>> call, Throwable t) {
//                prLoading.setVisibility(View.GONE);
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//    }

    private void showListResult() {
        rcListKeySearch.setVisibility(View.GONE);
        rcListSearchResult.setVisibility(View.VISIBLE);
        isShowListSearch = false;
    }
    private void handleShowList() {
        if (isShowListSearch) {
            rcListKeySearch.setVisibility(View.GONE);
            rcListSearchResult.setVisibility(View.VISIBLE);
            isShowListSearch = false;
        } else {
            rcListKeySearch.setVisibility(View.VISIBLE);
            rcListSearchResult.setVisibility(View.GONE);
            tvNotFound.setVisibility(View.GONE);
            isShowListSearch = true;
        }
    }
}
