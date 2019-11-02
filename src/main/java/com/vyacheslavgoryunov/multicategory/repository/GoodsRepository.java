package com.vyacheslavgoryunov.multicategory.repository;

import com.vyacheslavgoryunov.multicategory.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository("goodsRepository")
public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findAllByCategory_IsPublic(boolean isPublic);

    List<Goods> findAllByCategory_IsPublicOrCategoryIsNull(boolean isPublic);

    List<Goods> findAllByCategory_Id(long categoryId);

    List<Goods> findAllByCategory_IdAndCategory_IsPublic(long categoryId, boolean isPublic);

    List<Goods> findAllByCategory_IdIn(Collection<Long> ids);

    List<Goods> findAllByCategory_IdInAndCategory_IsPublic(Collection<Long> ids, boolean isPublic);
}
