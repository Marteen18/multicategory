package com.marteen18.multicategory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "title")
    @Length(min = 1)
    private String title;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "depth")
    private int depth;
}
