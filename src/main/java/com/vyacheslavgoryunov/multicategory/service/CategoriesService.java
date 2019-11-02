package com.vyacheslavgoryunov.multicategory.service;

import com.vyacheslavgoryunov.multicategory.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoriesService")
public class CategoriesService {
    private final CategoriesRepository repository;

    public CategoriesService(@Qualifier("categoriesRepository") CategoriesRepository repository) {
        this.repository = repository;
    }

    //should be cached
    public List<Long> getChildSequence(long id) {
        List<Long> result = new ArrayList<>();
        result.add(id);

        List<Long> childrenId = repository.selectIdByParentId(id);
        if (childrenId != null) {
            for (Long childId : childrenId) {
                result.addAll(getChildSequence(childId));
            }
        }

        return result;
    }
}
