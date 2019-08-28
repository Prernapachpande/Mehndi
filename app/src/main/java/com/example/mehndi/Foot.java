package com.example.mehndi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehndi.Adapter.FootAdapter;
import com.example.mehndi.model.mehndiimg;
import com.example.prernapachpande.Mehndi.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Foot extends Fragment {
    private ArrayList<mehndiimg> imageList;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private FootAdapter eAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_foot,container,false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface testapi  = RetroClient.getRetrofitClient().create(ApiInterface.class);
        imageList = new ArrayList<>();
        final Call<ArrayList<mehndiimg>> call = testapi.design("4");
        call.enqueue(new Callback<ArrayList<mehndiimg>>() {
            @Override
            public void onResponse(Call<ArrayList<mehndiimg>> call, Response<ArrayList<mehndiimg>> response) {
                pDialog.dismiss();

                if (response.isSuccessful()) {

                    imageList =  response.body();

                    recyclerView = (RecyclerView)view.findViewById(R.id.recycler_foot);
                    eAdapter = new FootAdapter(Foot.this, imageList);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<mehndiimg>> call, Throwable t) {
                pDialog.dismiss();
            }
        });
        return view;
    }
}

