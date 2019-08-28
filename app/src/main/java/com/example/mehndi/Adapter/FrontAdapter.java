package com.example.mehndi.Adapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mehndi.HandFront;
import com.example.mehndi.ImageFragment;
import com.example.mehndi.model.mehndiimg;
import com.example.prernapachpande.Mehndi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FrontAdapter extends RecyclerView.Adapter<FrontAdapter.CustomViewHolder>  {
  private ArrayList<mehndiimg> m;
  private FragmentActivity context;

  public FrontAdapter(HandFront context, ArrayList<mehndiimg> m) {
    this.context = context.getActivity();
    this.m = m;
  }

  @Override
  public FrontAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.mehndi_images, parent, false);

    return new FrontAdapter.CustomViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(FrontAdapter.CustomViewHolder holder, final int position) {
    final mehndiimg img = m.get(position);
    String imgURL = img.getImages();
    Picasso.with(context).load(imgURL).into(holder.imageview);
    holder.imageview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("mehul", "onclick working");
        Bundle bundle = new Bundle();
        bundle.putSerializable("images",m);
        bundle.putInt("position",position);

        ImageFragment fragment = new ImageFragment();
        FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.relative,fragment);
        fragmentTransaction.commit();
        fragment.setArguments(bundle);
      }
    });
  }


  @Override
  public int getItemCount()
  {
    return m.size();
  }

  public class CustomViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageview;

    public CustomViewHolder(View view) {
      super(view);
      imageview = (ImageView) view.findViewById(R.id.mehndiimage);


    }
  }
}
