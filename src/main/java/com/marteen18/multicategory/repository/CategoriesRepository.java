package com.marteen18.multicategory.repository;

import com.marteen18.multicategory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoriesRepository")
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsPublic(boolean isPublic);

    @Query("SELECT id FROM Category WHERE parentId = :id")
    List<Long> selectIdByParentId(@Param("id") long id);
}
