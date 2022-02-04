package com.example.crudsqlite1.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.crudsqlite1.Model.Model;
import com.example.crudsqlite1.R;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Model> listData;

    public Adapter(Activity activity,List<Model> listData){
        this.activity = activity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null &&  inflater != null){
            view= inflater.inflate(R.layout.card_item,null);
        }
        if (view != null){
            TextView nama = view.findViewById(R.id.tv_nama);
            TextView warna = view.findViewById(R.id.tv_warna);
            ImageView image = view.findViewById(R.id.iv_image);
            Model data =listData.get(i);
            nama.setText(data.getNama());
            warna.setText(data.getWarna());
            byte[] bytes = Base64.decode(data.getImage(),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            image.setImageBitmap(bitmap);

        }
        return view;
    }
}
