package edu.poly.asm.repository;

import edu.poly.asm.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContaining(String name);
    Page<Category> findByNameContaining(String name, Pageable pageable);
}
