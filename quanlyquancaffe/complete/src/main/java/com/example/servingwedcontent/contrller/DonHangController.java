package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.DonHang;
import com.example.servingwebcontent.model.MenuItem;
import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.DonHangRepository;
import com.example.servingwebcontent.repository.MenuItemRepository;
import com.example.servingwebcontent.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class DonHangController {

    @Autowired
    private DonHangRepository donHangRepo;

    @Autowired
    private MenuItemRepository menuRepo;

    @Autowired
    private NhanVienRepository nvRepo;

    // get all
    @GetMapping
    public ResponseEntity<List<DonHang>> getAll() {
        List<DonHang> list = donHangRepo.findAll();
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(list);
    }

    // create: expects JSON { "menuItemId":1, "nhanVienId":2, "soLuong": 3 }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> body) {
        try {
            Long menuItemId = body.get("menuItemId") == null ? null : Long.valueOf(String.valueOf(body.get("menuItemId")));
            Long nhanVienId = body.get("nhanVienId") == null ? null : Long.valueOf(String.valueOf(body.get("nhanVienId")));
            Integer soLuong = body.get("soLuong") == null ? null : Integer.valueOf(String.valueOf(body.get("soLuong")));

            if (menuItemId == null || nhanVienId == null || soLuong == null || soLuong <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Dữ liệu không hợp lệ"));
            }

            Optional<MenuItem> optItem = menuRepo.findById(menuItemId);
            Optional<NhanVien> optNV = nvRepo.findById(nhanVienId);
            if (optItem.isEmpty() || optNV.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Menu item hoặc nhân viên không tồn tại"));
            }

            MenuItem item = optItem.get();
            NhanVien nv = optNV.get();

            DonHang dh = new DonHang();
            dh.setMenuItem(item);
            dh.setNhanVien(nv);
            dh.setSoLuong(soLuong);
            dh.setOrderDate(LocalDateTime.now());
            double thanhTien = item.getPrice() * soLuong;
            dh.setThanhTien(thanhTien);

            DonHang saved = donHangRepo.save(dh);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + ex.getMessage()));
        }
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!donHangRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        donHangRepo.deleteById(id);
        donHangRepo.flush();
        return ResponseEntity.noContent().build();
    }
}