package com.example.crudsqlite1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudsqlite1.Adapter.Adapter;
import com.example.crudsqlite1.Helper.Helper;
import com.example.crudsqlite1.Model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView listView;
    AlertDialog.Builder dialog;
    List<Model> listData = new ArrayList<>();
    Adapter adapter;
    Helper db;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Helper(this);
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
        });
        listView = findViewById(R.id.lv_data);
        adapter = new Adapter(listData);
        adapter.setListener((v, i) -> {
            final String id = listData.get(i).getId();
            final String nama = listData.get(i).getNama();
            final String warna = listData.get(i).getWarna();
            final byte[] image = listData.get(i).getImage();
            final CharSequence[] dialogItems = {"Lihat", "Edit", "Hapus"};

            dialog = new AlertDialog.Builder(this);
            dialog.setItems(dialogItems, (dialogInterface, i1) -> {
                switch (i1) {
                    case 0:
                        Intent intentDetails = new Intent(this, DetailActivity.class);
                        intentDetails.putExtra("id", id);
                        intentDetails.putExtra("nama", nama);
                        intentDetails.putExtra("warna", warna);
                        intentDetails.putExtra("image", image);
                        startActivity(intentDetails);
                        break;
                    case 1:
                        Intent intent = new Intent(this, EditorActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("nama", nama);
                        intent.putExtra("warna", warna);
                        intent.putExtra("image", image);
                        startActivity(intent);
                        break;
                    case 2:
                        db.Delete(Integer.parseInt(id));
                        listData.clear();
                        getData();
                        break;
                }
            }).show();
        });
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData() {
        listData.clear();
        listData = db.getAll();
        adapter.updateData(listData);
    }

    @Override
    protected void onResume() {
        getData();
        Toast.makeText(this, "onResume Main "+listData.size(), Toast.LENGTH_SHORT).show();
        super.onResume();
    }
}