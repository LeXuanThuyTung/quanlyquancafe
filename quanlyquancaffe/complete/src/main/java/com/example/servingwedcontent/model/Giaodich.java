package com.example.servingwebcontent.model;
import java.util.ArrayList;
import java.util.Scanner;

public class Giaodich {

    public String MaKh;
    public String MaGiaoDich;
    public String NgayThangNamGiaoDich;
    public String NhanVienGiaoDich;
    public double TongTien;
    public int TongSoSp;
   

public Giaodich(String MaKh,String mgd,String ngd,String nvgd,double tt,int tsp){
    this.MaKh = MaKh;
    this.MaGiaoDich = mgd;
    this.NgayThangNamGiaoDich = ngd;
    this.NhanVienGiaoDich = nvgd;
    this.TongTien = tt;
    this.TongSoSp = tsp; 
}
public String getMkh(){
    return MaKh;
}
public String getMgd(){
    return MaGiaoDich;
    }
    public String getNgd(){
        return NgayThangNamGiaoDich;
    }
    public String getNvgd(){
        return NhanVienGiaoDich;
    }
    public double getTt(){
        return TongTien;
    }
    public int getTsp(){
        return TongSoSp;
    }
    public void setMkh(String MaKh){
        this.MaKh = MaKh;
    }
    public void setMgd(String mgd){
         this.MaGiaoDich = mgd;
    }
    public void setNgd(String ngd){
        this.NgayThangNamGiaoDich = ngd;
    }
    public void setNvgd(String nvgd){
        this.NhanVienGiaoDich = nvgd;
    }
    public void setTt(double tt){
        this.TongTien = tt;
    }
    public void setTsp(int tsp){
        this.TongSoSp = tsp;
    }
}