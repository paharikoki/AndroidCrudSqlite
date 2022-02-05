package com.example.crudsqlite1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crudsqlite1.Helper.Helper;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
        image = getIntent().getByteArrayExtra("image");

        if (id==null || id.equals("")){
            setTitle("Tambah Mobil Rental");
        }else {
            setTitle("Edit Mobil Rental");
            etnama.setText(nama);
            etwarna.setText(warna);
            ByteArrayInputStream stream = new ByteArrayInputStream(image);
            Bitmap bm = BitmapFactory.decodeStream(stream);
            ivimage.setImageBitmap(bm);
        }
        btnsave.setOnClickListener(view -> {
            try {
                if (id == null || id.equals("")){
                    Save();
                }else {
                    Edit();
                }
            }catch (Exception e){
                Log.e("Saving",e.getMessage());
            }
        });
        ivimage.setOnClickListener(view -> pickImage());
        fabimage.setOnClickListener(view -> pickImage());
    }
    private void Save(){
        if (String.valueOf(etnama.getText()).equals("") || String.valueOf(etwarna.getText()).equals("")){
            Toast.makeText(getApplicationContext(), "Harap Isi Semua Field!", Toast.LENGTH_SHORT).show();
        }else{
            ByteArrayOutputStream outS = new ByteArrayOutputStream();
            Bitmap bitmap = ((BitmapDrawable)ivimage.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, outS);
            image = outS.toByteArray();
            db.Insert(etnama.getText().toString(),etwarna.getText().toString(),image);
            Toast.makeText(getApplicationContext(), "Berhasil Menambahkan Data!", Toast.LENGTH_LONG).show();
            finish();
        }
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
        assert data != null;
        imguri = data.getData();
        ivimage.setImageURI(imguri);

    }
}