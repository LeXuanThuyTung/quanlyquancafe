package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.NhanVien;
import com.example.servingwebcontent.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienRepository repo;

    @GetMapping
    public ResponseEntity<List<NhanVien>> getAll() {
        List<NhanVien> list = repo.findAll();
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(list);
    }

    @PostMapping
    public NhanVien create(@RequestBody NhanVien nv) {
        return repo.save(nv);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NhanVien> update(@PathVariable Long id, @RequestBody NhanVien updated) {
        return repo.findById(id).map(nv -> {
            nv.setTen(updated.getTen());
            nv.setChucVu(updated.getChucVu());
            nv.setLuong(updated.getLuong());
            return ResponseEntity.ok(repo.save(nv));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            repo.flush();
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}