package com.marteen18.multicategory.common;

import com.marteen18.multicategory.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryContainer {
    private Category category;
    private List<CategoryContainer> children;
    private int depth;

    public static List<CategoryContainer> create(List<Category> categories) {
        return scan(null, categories, new ArrayList<>(), 0);
    }

    private static List<CategoryContainer> scan(Category parent, List<Category> categories, List<Long> handled, int depth) {
        List<CategoryContainer> result = new ArrayList<>();

        for (Category category : categories) {
            if (handled.contains(category.getId()))
                continue;

            if (parent != null && (category.getParentId() == null || category.getParentId() != parent.getId()))
                continue;

            handled.add(category.getId());
            result.add(new CategoryContainer(category, scan(category, categories, handled, depth + 1), depth));
        }

        return result;
    }
}
