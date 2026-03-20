package com.example.servingwebcontent.database;

import com.example.servingwebcontent.model.Giaodich;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaodichAiven {

    private final String jdbcUrl = "jdbc:mysql://mysql-2954f5bb-opp-data.j.aivencloud.com:14833/defaultdb?sslMode=REQUIRED";
    private final String jdbcUsername = "avnadmin";
    private final String jdbcPassword = "AVNS_fIeg8rQ_jgkVDcDFWyn";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
    }

    // Lấy tất cả giao dịch
    public List<Giaodich> getAllGiaodich() throws SQLException {
        List<Giaodich> list = new ArrayList<>();
        String sql = "SELECT * FROM giaodich";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String magd = rs.getString("MaGiaoDich");
                String makh = rs.getString("MaKh");
                String ngay = rs.getString("NgayThangNamGiaoDich");
                String nv = rs.getString("NhanVienGiaoDich");
                double tongTien = rs.getDouble("TongTien");
                int tongSoSp = rs.getInt("TongSoSp");

                Giaodich gd = new Giaodich(magd,makh, ngay, nv, tongTien, tongSoSp);
                list.add(gd);
            }
        }

        return list;
    }

    // Lấy giao dịch theo mã
    public Giaodich getGiaodichById(String maGiaoDich) throws SQLException {
        String sql = "SELECT * FROM giaodich WHERE MaGiaoDich = ?";
        Giaodich gd = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maGiaoDich);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                gd = new Giaodich(
                        rs.getString("MaGiaoDich"),
                        rs.getString("MaKh"),
                        rs.getString("NgayThangNamGiaoDich"),
                        rs.getString("NhanVienGiaoDich"),
                        rs.getDouble("TongTien"),
                        rs.getInt("TongSoSp")
                );
            }

            rs.close();
        }

        return gd;
    }

    // Tạo giao dịch
    public boolean createGiaodich(Giaodich gd) throws SQLException {
        String sql = "INSERT INTO giaodich (MaGd,MaKh,ThoigianGd, NhanvienGd, TongTien, TongSoSp) VALUES (?,?,?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gd.getMgd());
            pstmt.setString(2, gd.getNgd());
            pstmt.setString(3, gd.getNvgd());
            pstmt.setDouble(4, gd.getTt());
            pstmt.setInt(5, gd.getTsp());

            return pstmt.executeUpdate() > 0;
        }
    }

    // Cập nhật giao dịch
    public boolean updateGiaodich(String maGiaoDich, Giaodich gd) throws SQLException {
        String sql = "UPDATE giaodich SET MaKh = ?, ThoigianGd = ?, NhanvienGd = ?, TongTien = ?, TongSoSp = ? WHERE MaGd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gd.getNgd());
            pstmt.setString(2,gd.getMkh());
            pstmt.setString(2, gd.getNvgd());
            pstmt.setDouble(3, gd.getTt());
            pstmt.setInt(4, gd.getTsp());
            pstmt.setString(5, maGiaoDich);

            return pstmt.executeUpdate() > 0;
        }
    }

    // Xóa giao dịch
    public boolean deleteGiaodich(String maGiaoDich) throws SQLException {
        String sql = "DELETE FROM giaodich WHERE MaGiaoDich = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maGiaoDich);
            return pstmt.executeUpdate() > 0;
        }
    }
}
