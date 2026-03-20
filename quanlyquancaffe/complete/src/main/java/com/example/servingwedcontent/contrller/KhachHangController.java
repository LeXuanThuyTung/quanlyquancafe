package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.database.myDBConnection;
import com.example.servingwebcontent.model.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class KhachHangController {
  @Autowired myDBConnection dbConnection;
    // Lấy danh sách khách hàng
    @GetMapping
    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT code, name, phone, address FROM customers";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm khách hàng
    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer c) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "INSERT INTO customers (code, name, phone, address) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getCode());
            ps.setString(2, c.getName());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            ps.executeUpdate();
            return ResponseEntity.ok(Map.of("message", "Thêm khách hàng thành công"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    @PutMapping("/{code}")
public ResponseEntity<?> updateCustomer(@PathVariable String code, @RequestBody Customer c) {
    try (Connection conn = dbConnection.getConnection()) {
        String sql = "UPDATE customers SET code=?, name=?, phone=?, address=? WHERE code=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getCode());
        ps.setString(2, c.getName());
        ps.setString(3, c.getPhone());
        ps.setString(4, c.getAddress());
        ps.setString(5, code);
        int rows = ps.executeUpdate();
        if (rows > 0) {
            return ResponseEntity.ok(Map.of("message", "Cập nhật thành công"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Không tìm thấy khách hàng"));
        }
    } catch (Exception e) {
        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
    }
}
    // Xóa khách hàng
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String code) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "DELETE FROM customers WHERE code=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, code);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return ResponseEntity.ok(Map.of("message", "Xóa thành công"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Không tìm thấy khách hàng"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}