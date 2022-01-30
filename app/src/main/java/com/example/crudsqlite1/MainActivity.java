package com.example.crudsqlite1;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.crudsqlite1.Adapter.Adapter;
import com.example.crudsqlite1.Helper.Helper;
import com.example.crudsqlite1.Model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<Model> listData = new ArrayList<>();
    Adapter adapter;
    Helper db = new Helper(this);
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Helper(getApplicationContext());
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);
            }
        });
        listView= findViewById(R.id.lv_data);
        adapter = new Adapter(MainActivity.this,listData);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = listData.get(i).getId();
                final String nama = listData.get(i).getNama();
                final String warna = listData.get(i).getWarna();
                final CharSequence[] dialogItems = {"Lihat","Edit","Hapus"};

                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intentDetails = new Intent(MainActivity.this,DetailActivity.class);
                                intentDetails.putExtra("id",id);
                                intentDetails.putExtra("nama",nama);
                                intentDetails.putExtra("warna",warna);
                                startActivity(intentDetails);
                                break;
                            case 1:
                                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                                intent.putExtra("id",id);
                                intent.putExtra("nama",nama);
                                intent.putExtra("warna",warna);
                                startActivity(intent);
                                break;
                            case  2:
                                db.Delete(Integer.parseInt(id));
                                listData.clear();
                                getData();
                                break;
                        }
                    }
                }).show();
            }
        });
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                final String id = listData.get(i).getId();
//                final String nama = listData.get(i).getNama();
//                final String warna = listData.get(i).getWarna();
//                final CharSequence[] dialogItems = {"Lihat","Edit","Hapus"};
//
//                dialog = new AlertDialog.Builder(MainActivity.this);
//                dialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i){
//                            case 0:
//                                Intent intentDetails = new Intent(MainActivity.this,DetailActivity.class);
//                                intentDetails.putExtra("id",id);
//                                intentDetails.putExtra("nama",nama);
//                                intentDetails.putExtra("warna",warna);
//                                startActivity(intentDetails);
//                                break;
//                            case 1:
//                                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
//                                intent.putExtra("id",id);
//                                intent.putExtra("nama",nama);
//                                intent.putExtra("warna",warna);
//                                startActivity(intent);
//                                break;
//                            case  2:
//                                db.Delete(Integer.parseInt(id));
//                                listData.clear();
//                                getData();
//                                break;
//                        }
//                    }
//                }).show();
//
//
//                return false;
//            }
//        });
        getData();
    }
    private void getData(){
        ArrayList<HashMap<String,String>> rows = db.getAll();
        for (int i = 0;i < rows.size();i++){
            String id = rows.get(i).get("id");
            String nama = rows.get(i).get("nama");
            String warna = rows.get(i).get("warna");

            Model data = new Model();
            data.setId(id);
            data.setNama(nama);
            data.setWarna(warna);
            listData.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listData.clear();
        getData();
    }
}