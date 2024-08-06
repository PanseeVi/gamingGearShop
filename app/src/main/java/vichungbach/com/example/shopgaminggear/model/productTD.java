package vichungbach.com.example.shopgaminggear.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class productTD implements Serializable {
   int  id;
   String tenPD;

   String giaPD;
   String hinhPD;
   String motaPD;
   String loaiPD;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPD() {
        return tenPD;
    }

    public void setTenPD(String tenPD) {
        this.tenPD = tenPD;
    }

    public String getGiaPD() {
        return giaPD;
    }

    public void setGiaPD(String giaPD) {
        this.giaPD = giaPD;
    }

    public String getHinhPD() {
        return hinhPD;
    }

    public void setHinhPD(String hinhPD) {
        this.hinhPD = hinhPD;
    }

    public String getMotaPD() {
        return motaPD;
    }

    public void setMotaPD(String motaPD) {
        this.motaPD = motaPD;
    }

    public String getLoaiPD() {
        return loaiPD;
    }

    public void setLoaiPD(String loaiPD) {
        this.loaiPD = loaiPD;
    }
}
