
package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.DonHang;
import com.example.servingwebcontent.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/search")
public class TimKiemController {

    @Autowired
    private DonHangRepository donHangRepo;

    // GET /api/search/orders/{id}
    @GetMapping("/orders/{id}")
    public ResponseEntity<?> timKiemDonHang(@PathVariable Long id) {
        Optional<DonHang> donHang = donHangRepo.findById(id);
        if (donHang.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(donHang.get());
    }
}
