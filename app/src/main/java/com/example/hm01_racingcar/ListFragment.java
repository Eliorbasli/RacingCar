package com.example.hm01_racingcar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;


public class ListFragment extends Fragment {


    private RecyclerView list_RV_top10;


    public MyDb myDB;
    private List_CallBack listCallBack;
    private ArrayList<Record> records;
    private AppCompatActivity activity;
    private RecyclerView list_RV_records;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list,container , false);
        initViews(view);

        MSP fromJSON = MSP.getInstance();

        if (fromJSON != null){
            String js = fromJSON.getString("MY_DB", "");
            MyDb newDb = new Gson().fromJson(js,MyDb.class);

            ArrayList<Record> records = myDB.getRecords();

            RecordAdapter recordAdapter = new RecordAdapter(this, myDB.getRecords());

            list_RV_records.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
            list_RV_records.setHasFixedSize(true);
            list_RV_records.setItemAnimator(new DefaultItemAnimator());
            list_RV_records.setAdapter(recordAdapter);

            recordAdapter.setRecordItemClickListener(new RecordAdapter.RecordItemClickListener() {
                @Override
                public void recordItemClick(Record record, int position) {
                    if(listCallBack != null){
                        double lat = record.getMyLocation().getLatitube();
                        double lon = record.getMyLocation().getLongitube();
                        listCallBack.RecordClicked(lat,lon);
                    }
                }
            });
        }
        else{

        }

        return view;
    }

    private void initViews(View view) {

        //list_RV_top10 = view.findViewById(R.id.list_RV_top10);
    }

    public void setListCallBack(List_CallBack listCallBack){
        this.listCallBack = listCallBack;
    }

    public void setActivity(AppCompatActivity activity){
        this.activity = activity;
    }



}