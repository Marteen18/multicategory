package com.vyacheslavgoryunov.multicategory.common;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class DetailNode {
    @JsonAlias("title")
    private String title;

    @JsonAlias("value")
    private String value;

    public DetailNode() {
    }

    public DetailNode(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
