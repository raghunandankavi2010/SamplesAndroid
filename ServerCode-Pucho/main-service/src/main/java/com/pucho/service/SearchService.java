package com.pucho.service;

import com.pucho.data.AnswerESData;
import com.pucho.data.QuestionESData;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.helper.SearchResultHelper;

import java.util.List;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public interface SearchService {
    public SearchResultHelper searchQuestions(String queryWords, Integer size, Integer from);
    public SearchResultHelper searchAnswer(String queryWords, Integer size, Integer from);
}
