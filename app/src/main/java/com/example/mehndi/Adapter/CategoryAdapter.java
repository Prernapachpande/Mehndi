package com.example.mehndi.Adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehndi.Category;
import com.example.mehndi.model.Datum;
import com.example.prernapachpande.Mehndi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CustomViewHolder> {
    private List<Datum> d;
    private FragmentActivity context;
    public CategoryAdapter(Category context, List<Datum> d) {
        this.context = (FragmentActivity) context.getActivity();
        this.d = d;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mehndi_category, parent, false);

        return new CustomViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        final Datum cat = d.get(position);
        holder.name.setText(cat.getCatName());

        final String imgURL = cat.getCatImage();
        Picasso.with(context).load(imgURL).into(holder.images);

    }

    public int getItemCount() {
        return d.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView name;

        public CustomViewHolder(View view) {
            super(view);
            images = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);

        }
    }
}
