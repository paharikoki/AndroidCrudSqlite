package com.example.crudsqlite1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class DetailActivity extends AppCompatActivity {
    private TextView tvid,tvnama,tvwarna;
    private ImageView tvImage;
    private String id,nama,warna;
    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvid = findViewById(R.id.tv_id);
        tvnama = findViewById(R.id.tv_nama);
        tvwarna= findViewById(R.id.tv_warna);
        tvImage = findViewById(R.id.dl_image);

        id = getIntent().getStringExtra("id");
        warna = getIntent().getStringExtra("warna");
        nama = getIntent().getStringExtra("nama");
        image = getIntent().getByteArrayExtra("image");

        tvid.setText("Id Mobil: " +id);
        tvnama.setText("Nama Mobil: " +nama);
        tvwarna.setText("Warna Mobil: " +warna);
        ByteArrayInputStream stream = new ByteArrayInputStream(image);
        Bitmap bm = BitmapFactory.decodeStream(stream);
        tvImage.setImageBitmap(bm);
    }
}