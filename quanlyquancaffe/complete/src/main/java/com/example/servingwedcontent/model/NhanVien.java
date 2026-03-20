package com.example.servingwebcontent.model;

import jakarta.persistence.*;

@Entity
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ten;
    private String chucVu;
    private double luong;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }

    public double getLuong() { return luong; }
    public void setLuong(double luong) {
        if (luong < 0) {
            throw new IllegalArgumentException("Lương không được âm");
        }
        this.luong = luong;
    }
}