package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {}