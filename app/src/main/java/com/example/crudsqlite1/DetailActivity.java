package com.example.crudsqlite1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView tvid,tvnama,tvwarna;
    private String id,nama,warna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvid = findViewById(R.id.tv_id);
        tvnama = findViewById(R.id.tv_nama);
        tvwarna= findViewById(R.id.tv_warna);

        id = getIntent().getStringExtra("id");
        warna = getIntent().getStringExtra("warna");
        nama = getIntent().getStringExtra("nama");

        tvid.setText("Id Mobil: " +id);
        tvnama.setText("Nama Mobil: " +nama);
        tvwarna.setText("Warna Mobil: " +warna);
    }
}