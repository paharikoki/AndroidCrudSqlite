package com.example.crudsqlite1.Model;

public class Model {
    private String id,nama,warna;
    private byte[] image;

    public Model(){

    }
    public Model (String id,String nama,String warna,byte[] image){
        this.id = id;
        this.nama = nama;
        this.warna = warna;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
