package com.example.crudsqlite1.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudsqlite1.Model.Model;
import com.example.crudsqlite1.R;

import java.io.ByteArrayInputStream;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private List<Model> listData;
    private OnAdapterClickListener listener;

    public Adapter(List<Model> listData) {
        this.listData = listData;
    }

    public void updateData(List<Model> modelList) {
        listData.clear();
        listData = modelList;
        notifyDataSetChanged();
    }

    public void setListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vh = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new AdapterViewHolder(vh);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        Model item = listData.get(position);
        holder.nama.setText(item.getNama());
        holder.warna.setText(item.getWarna());
        byte[] img = item.getImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(img);
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
        holder.image.setImageBitmap(bitmap);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public interface OnAdapterClickListener {
        void onClick(View v, int position);
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView nama, warna;
        ImageView image;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_nama);
            warna = itemView.findViewById(R.id.tv_warna);
            image = itemView.findViewById(R.id.tv_image);
            itemView.setOnClickListener(v -> listener.onClick(v, getBindingAdapterPosition()));
        }
    }
}
