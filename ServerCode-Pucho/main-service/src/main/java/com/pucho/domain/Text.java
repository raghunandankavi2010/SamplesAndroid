package com.pucho.domain;

import org.minnal.instrument.entity.AggregateRoot;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dinesh.rathore on 13/12/15.
 */
@Entity
@Table(name="texts")
@AggregateRoot
public class Text extends BaseDomain{
    private Long id;
    private String language;
    private String sentence;

    @Override
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
