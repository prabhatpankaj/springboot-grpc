package com.techbellys.serverservice.repository;

import com.techbellys.serverservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}