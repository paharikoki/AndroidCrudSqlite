package com.example.crudsqlite1;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.crudsqlite1.Helper.Helper;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditorActivity extends AppCompatActivity {

    private EditText etnama,etwarna;
    private Button btnsave;
    private Helper db = new Helper(this);
    private String id,nama,warna;
    private FloatingActionButton fabimage;
    private ImageView ivimage;
    private byte[] image;
    private Uri imguri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etnama = findViewById(R.id.et_nama);
        etwarna= findViewById(R.id.et_warna);
        btnsave = findViewById(R.id.btn_save);
        ivimage= findViewById(R.id.iv_image);
        fabimage=findViewById(R.id.fab_image);

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
        ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        fabimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }
    private void Save(){
        if (String.valueOf(etnama.getText()).equals("") || String.valueOf(etwarna.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Harap Isi Semua Field!", Toast.LENGTH_SHORT).show();
        }else{
            try {
                InputStream inputStream = getContentResolver().openInputStream(imguri);
                image = getBytes(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.Insert(etnama.getText().toString(),etwarna.getText().toString(),image);
            finish();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();

    }

    private void Edit(){
        if (String.valueOf(etnama.getText()).equals("") || String.valueOf(etwarna.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Harap Isi Semua Field!", Toast.LENGTH_SHORT).show();
        }else{
            db.Update(Integer.parseInt(id),etnama.getText().toString(),etwarna.getText().toString(),image);
            finish();
        }
    }
    private void pickImage(){
        ImagePicker.Companion.with(EditorActivity.this)
                .crop()
                .start();
        //Toast.makeText(EditorActivity.this, "Harusnya ini Buka Kamera & Galery", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imguri = data.getData();
        ivimage.setImageURI(imguri);

    }
}