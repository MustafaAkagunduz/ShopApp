package com.noisy.rrssProject.model.entity;

import com.noisy.rrssProject.model.base.EntityBase;
import jakarta.persistence.*;

@Entity
public class Category implements EntityBase {

    public Category() {}

    public Category(Long id, String name, Category parentCategory) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category parentCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }


}
