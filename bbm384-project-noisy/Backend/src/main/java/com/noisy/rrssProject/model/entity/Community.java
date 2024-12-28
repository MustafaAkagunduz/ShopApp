package com.noisy.rrssProject.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.noisy.rrssProject.model.base.EntityBase;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Community implements EntityBase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "communities")
    private List<Moderator> moderators = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Moderator> getModerators() {
        return moderators;
    }

    public void setModerators(List<Moderator> moderators) {
        this.moderators = moderators;
    }
}
