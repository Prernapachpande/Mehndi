package com.example.mehndi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehndi.Adapter.CategoryAdapter;
import com.example.mehndi.model.Datum;
import com.example.mehndi.model.mehndi;
import com.example.prernapachpande.Mehndi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends Fragment {
    private List<Datum> CategoryList;
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private CategoryAdapter eAdapter;
    public Category() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Data.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        ApiInterface testapi  = RetroClient.getRetrofitClient().create(ApiInterface.class);
        CategoryList = new ArrayList<>();
        final Call<mehndi> call = testapi.getData();
        call.enqueue(new Callback<mehndi>() {
            @Override
            public void onResponse(Call<mehndi> call, Response<mehndi> response) {
                pDialog.dismiss();

                if (response.isSuccessful()) {
                    CategoryList = response.body().getData();


                    recyclerView = (RecyclerView)view.findViewById(R.id.recycler_category);
                    eAdapter = new CategoryAdapter(Category.this, CategoryList);

                    RecyclerView.LayoutManager eLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(eLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eAdapter);
                    recyclerView.addOnItemTouchListener( new Category.RecyclerTouchListener( getActivity(), recyclerView, new Category.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    } ) );
                }
            }

            @Override
            public void onFailure(Call<mehndi> call, Throwable t) {
                pDialog.dismiss();
            }
        });
        return view;
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Category.ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Category.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));

                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
                int position = recyclerView.getChildAdapterPosition(child);
                Fragment fragment = null;

                switch (position) {
                    case 0:
                        fragment = new HandFront();
                        break;
                    case 1:
                        fragment = new HandBack();
                        break;
                    case 2:
                        fragment = new Foot();
                        break;
                    case 3:
                        fragment = new Tattoo();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}
