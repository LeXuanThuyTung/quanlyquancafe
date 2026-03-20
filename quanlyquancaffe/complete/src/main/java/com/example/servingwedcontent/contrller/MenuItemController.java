package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.MenuItem;
import com.example.servingwebcontent.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuItemController {

    @Autowired
    private MenuItemRepository repo;

    // Luôn trả dữ liệu mới, không cache ở browser
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAll() {
        List<MenuItem> items = repo.findAll();
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .body(items);
    }

    @PostMapping
    public MenuItem create(@RequestBody MenuItem item) {
        return repo.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> update(@PathVariable Long id, @RequestBody MenuItem updated) {
        return repo.findById(id).map(item -> {
            item.setName(updated.getName());
            item.setPrice(updated.getPrice());
            item.setStock(updated.getStock());
            return ResponseEntity.ok(repo.save(item));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            repo.flush(); 
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}