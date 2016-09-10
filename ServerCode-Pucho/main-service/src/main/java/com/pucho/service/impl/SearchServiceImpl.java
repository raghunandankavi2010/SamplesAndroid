package com.pucho.service.impl;

import com.google.inject.Inject;
import com.pucho.data.AnswerESData;
import com.pucho.data.QuestionESData;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.domain.User;
import com.pucho.helper.ElasticSearchResultHelper;
import com.pucho.helper.SearchResultHelper;
import com.pucho.resources.SMSResource;
import com.pucho.service.ElasticSearchService;
import com.pucho.service.MLService;
import com.pucho.service.SearchService;
import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dinesh.rathore on 27/09/15.
 */
public class SearchServiceImpl implements SearchService {
    private final MLService mlService;
    private final ElasticSearchService elasticSearchService;
    private static final Logger logger = LoggerFactory.getLogger(SMSResource.class);

    @Inject
    public SearchServiceImpl(MLService mlService, ElasticSearchService elasticSearchService) {
        this.mlService = mlService;
        this.elasticSearchService = elasticSearchService;
    }


    @Override
    public SearchResultHelper searchQuestions(String queryWords, Integer size, Integer from) {
        List<String> tags = this.mlService.getTags(queryWords);
        ElasticSearchResultHelper resultFromElasticSearch = this.elasticSearchService.searchQuestion(queryWords, tags, size, from);
        List<QuestionESData> questionESDataList = resultFromElasticSearch.getQuestionEsData();
        List<Question> questionList = new ArrayList<>();
        for (QuestionESData questionESData: questionESDataList) {
            Question ques = questionESData.getQuestion();
            Filter filter = new Filter();
            filter.addCondition("id", Condition.Operator.eq, ques.getUserId());
            List<User> userList = User.where(filter);
            if (userList != null && userList.size() != 0) {
                ques.setUser(userList.get(0));
            }
            else {
                logger.error("No User exits for userId: "+ques.getUserId()+"  with questionId: "+ques.getId());
            }
            questionList.add(ques);
        }
        SearchResultHelper result = new SearchResultHelper();
        result.setTotal(resultFromElasticSearch.getTotal());
        result.setQuestionsData(questionList);
        return result;
    }

    @Override
    public SearchResultHelper searchAnswer(String queryWords, Integer size, Integer from) {
        ElasticSearchResultHelper resultFromElasticSearch = this.elasticSearchService.searchAnswer(queryWords, size, from);
        List<AnswerESData> answerESDataList = resultFromElasticSearch.getAnswerESData();
        List<Answer> answerList = new ArrayList<>();
        for (AnswerESData answerESData: answerESDataList) {
            Answer answer = answerESData.getAnswer();
            Filter filter = new Filter();
            filter.addCondition("id", Condition.Operator.eq, answer.getUserId());
            List<User> userList = User.where(filter);
            if (userList != null && userList.size() != 0) {
                answer.setUser(userList.get(0));
            }
            else {
                logger.error("No User exits for userId: "+answer.getUserId()+"  with answerId: "+answer.getId());
            }
            answerList.add(answer);
        }
        SearchResultHelper result = new SearchResultHelper();
        result.setTotal(resultFromElasticSearch.getTotal());
        result.setAnswersData(answerList);
        return result;
    }
}
