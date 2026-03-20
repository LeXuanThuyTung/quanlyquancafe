package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.database.GiaodichAiven;
import com.example.servingwebcontent.database.myDBConnection;
import com.example.servingwebcontent.model.Giaodich;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Connection;
import java.util.Map;
import java.util.UUID;
@Controller
public class GDController {
@Autowired myDBConnection dbConnection;
    private final GiaodichAiven giaoDichDB = new GiaodichAiven();
  
    // Hiển thị tất cả giao dịch
    @GetMapping("/giaodich")
    public String hienThiDanhSach(Model model) {
        try {
            List<Giaodich> ds = giaoDichDB.getAllGiaodich();
            model.addAttribute("giaodich", ds);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
        return "giaodich";
    }

    // Thêm giao dịch
    @PostMapping("/giaodich/add")
    public String themGiaodich(
            @RequestParam String maKh,
            @RequestParam String timeGd,
            @RequestParam String nvGd,
            @RequestParam double tongTien,
            @RequestParam int tongSp,
            Model model) {

        try {
            String newMaGd = taoMaGiaodich();
            Giaodich gd = new Giaodich(newMaGd,maKh, timeGd, nvGd, tongTien, tongSp);
            giaoDichDB.createGiaodich(gd);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi thêm giao dịch: " + e.getMessage());
        }

        return "redirect:/giaodich";
    }

    // Sửa giao dịch
    @PostMapping("/giaodich/edit")
    public String suaGiaodich(
            @RequestParam String maGd,
            @RequestParam String maKh,
            @RequestParam String timeGd,
            @RequestParam String nvGd,
            @RequestParam double tongTien,
            @RequestParam int tongSp,
            Model model) {

        try {
            Giaodich newGd = new Giaodich(maGd,maKh,timeGd, nvGd, tongTien, tongSp);
            giaoDichDB.updateGiaodich(maGd, newGd);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi sửa giao dịch: " + e.getMessage());
        }

        return "redirect:/giaodich";
    }

    // Xóa giao dịch
    @PostMapping("/giaodich/delete")
    public String xoaGiaodich(@RequestParam String maGd, Model model) {
        try {
            giaoDichDB.deleteGiaodich(maGd);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi xóa giao dịch: " + e.getMessage());
        }

        return "redirect:/giaodich";
    }

    // Hàm sinh mã giao dịch mới: GD001, GD002,...
    private String taoMaGiaodich() throws Exception {
        List<Giaodich> danhSach = giaoDichDB.getAllGiaodich();
        int max = danhSach.size() + 1;
        return String.format("GD%03d", max);
    }
    
 @PostMapping("/dathang")
@ResponseBody
public String datHang(@RequestParam String maKH, @RequestBody Map<String, Integer> gioHang) {
    Connection conn = null;
    PreparedStatement stmt = null;
    PreparedStatement stmtGia = null;
    ResultSet rs = null;

    try {
        conn = dbConnection.getConnection();
        conn.setAutoCommit(false);

        String maGD = "GD" + UUID.randomUUID().toString().substring(0, 6);

        String sqlGetGia = "SELECT price FROM menu_item WHERE id = ?";
        stmtGia = conn.prepareStatement(sqlGetGia);

        String sqlInsert = "INSERT INTO donhang(MaGd, MaKh, MaSp, TongSoSp, TongTien) VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sqlInsert);

        for (Map.Entry<String, Integer> entry : gioHang.entrySet()) {
            String maSP = entry.getKey();
            int soLuong = entry.getValue();

            // Nếu ID là số nguyên thì dùng parseInt
            stmtGia.setInt(1, Integer.parseInt(maSP));

            rs = stmtGia.executeQuery();

            if (rs.next()) {
                double gia = rs.getDouble("price");
                double tongTien = gia * soLuong;

                stmt.setString(1, maGD);
                stmt.setString(2, maKH);
                stmt.setInt(3, Integer.parseInt(maSP)); // MaSp lưu dạng số
                stmt.setInt(4, soLuong);
                stmt.setDouble(5, tongTien);
                stmt.addBatch();
            } else {
                return "Sản phẩm không tồn tại: " + maSP;
            }
        }

        stmt.executeBatch();
        conn.commit();

        return "Đặt hàng thành công. Mã giao dịch: " + maGD;

    } catch (Exception e) {
        try {
            if (conn != null) conn.rollback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        return "Lỗi đặt hàng: " + e.getMessage();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (stmtGia != null) stmtGia.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
@GetMapping("/dathang")
public String hienThiTrangDatHang() {
    return "dathang"; // tên file dathang.html trong /templates
}

}