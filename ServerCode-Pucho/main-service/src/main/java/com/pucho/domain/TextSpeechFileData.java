package com.pucho.domain;

import org.minnal.instrument.entity.AggregateRoot;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dinesh.rathore on 13/12/15.
 */
@Entity
@Table(name="textSpeechFiles")
@AggregateRoot
public class TextSpeechFileData extends BaseDomain{
    private Long id;
    private Long textId;
    private String filename;

    @Override
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
