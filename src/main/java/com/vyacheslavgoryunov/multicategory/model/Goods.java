package com.vyacheslavgoryunov.multicategory.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.vyacheslavgoryunov.multicategory.common.DetailNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    @Length(min = 1, max = 250)
    private String title;

    @Column(name = "description")
    @Length(max = 10000)
    private String description;

    @Column(name = "price", scale = 2)
    private Double price;

    @OneToOne(optional = true)
    private Category category;

    @OneToOne
    private User user;

    @Column(name = "details")
    private String detailsRaw;

    @Column(name = "created_at")
    private Date createdAt;

    @Transient
    private List<DetailNode>  details;

    public List<DetailNode> getDetails() {
        if (details == null && detailsRaw != null && !detailsRaw.isEmpty()) {
            try {
                details = new Gson().fromJson(detailsRaw, List.class);
            } catch (JsonSyntaxException e) {
            }
        }

        if (details == null) {
            details = new ArrayList<>();
        }

        return details;
    }

    public void setDetails(List<DetailNode> details) {
        this.details = details;
        detailsRaw = new Gson().toJson(details);
    }
}
