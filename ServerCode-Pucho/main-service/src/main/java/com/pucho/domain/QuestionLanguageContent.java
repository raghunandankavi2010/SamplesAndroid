package com.pucho.domain;

import org.minnal.instrument.entity.Searchable;

import javax.persistence.*;

/**
 * Created by dinesh.rathore on 12/04/16.
 */
@Entity
@Table(name = "question_language_contents")
public class QuestionLanguageContent extends SoftDeletableModel {
    private Long id;
    private Long questionId;

    @Searchable
    private String language;
    private String content;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

