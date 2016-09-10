package com.pucho.domain;

import org.minnal.instrument.entity.AggregateRoot;

import javax.persistence.*;

/**
 * Created by dinesh.rathore on 30/04/15.
 */
@Entity
@Table(name="subjects")
@AggregateRoot
public class Subject extends BaseDomain {

    private Long id;
    private String name;


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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
}
