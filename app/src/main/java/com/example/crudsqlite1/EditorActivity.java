package com.example.crudsqlite1;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.crudsqlite1.Helper.Helper;

public class EditorActivity extends AppCompatActivity {

    private EditText etnama,etwarna;
    private Button btnsave;
    private Helper db = new Helper(this);
    private String id,nama,warna;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etnama = findViewById(R.id.et_nama);
        etwarna= findViewById(R.id.et_warna);
        btnsave = findViewById(R.id.btn_save);

        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        warna = getIntent().getStringExtra("warna");

        if (id==null || id.equals("")){
            setTitle("Tambah Mobil Rental");
        }else {
            setTitle("Edit Mobil Rental");
            etnama.setText(nama);
            etwarna.setText(warna);
        }
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (id == null || id.equals("")){
                        Save();
                    }else {
                        Edit();
                    }
                }catch (Exception e){
                    Log.e("Saving",e.getMessage());
                }
            }
        });
    }
    private void Save(){
        if (String.valueOf(etnama.getText()).equals("") || String.valueOf(etwarna.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Harap Isi Semua Field!", Toast.LENGTH_SHORT).show();
        }else{
            db.Insert(etnama.getText().toString(),etwarna.getText().toString());
            finish();
        }
    }
    private void Edit(){
        if (String.valueOf(etnama.getText()).equals("") || String.valueOf(etwarna.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Harap Isi Semua Field!", Toast.LENGTH_SHORT).show();
        }else{
            db.Update(Integer.parseInt(id),etnama.getText().toString(),etwarna.getText().toString());
            finish();
        }
    }
}