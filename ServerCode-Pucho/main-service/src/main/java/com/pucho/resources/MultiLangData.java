package com.pucho.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.domain.Question;
import com.pucho.helper.MultiLanResponse;
import com.pucho.service.TranslateService;
import com.pucho.utils.JsonUtil;
import io.dropwizard.jackson.JsonSnakeCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
@Path("/multiLanQuestion")
@JsonSnakeCase
@Produces({"application/json"})
@Singleton
public class MultiLangData {

    private TranslateService translateService;

    @Inject
    public MultiLangData(TranslateService translateService) {
        this.translateService = translateService;
    }

    private static final Logger logger = LoggerFactory.getLogger(MultiLangData.class);

    @GET
    @Timed
    @Path("/{language}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public String getQuestions(@PathParam("language") String language) {
        List<Question> questionList = Question.all();
        try {
            for(Question question:questionList){
                String otherLanguage = translateService.translateString(question.getContent(), language);
                System.out.println(otherLanguage);
                question.setContent(otherLanguage);
            }
            MultiLanResponse response = new MultiLanResponse(questionList);

            return JsonUtil.serializeJson(response);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e,
                    javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error while parsing the response ", e);
            throw new WebApplicationException(e,
                    javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
