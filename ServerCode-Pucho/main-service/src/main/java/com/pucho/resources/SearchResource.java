package com.pucho.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.helper.ErrorResponse;
import com.pucho.helper.GenericResponse;
import com.pucho.helper.SearchResultHelper;
import com.pucho.helper.SuccessResponse;
import com.pucho.service.ElasticSearchService;
import com.pucho.service.SearchService;
import com.pucho.utils.JsonUtil;

import io.dropwizard.jackson.JsonSnakeCase;

import java.util.List;

/**
 * Created by dinesh.rathore on 27/09/15.
 */

@Path("/search")
@JsonSnakeCase
@Produces({ "application/json" })
@Singleton
public class SearchResource {
	
	private final ElasticSearchService elasticSearchService;
	private final SearchService searchService;
	
	@Inject
	public SearchResource(ElasticSearchService elasticSearchService, SearchService searchService) {
		this.elasticSearchService = elasticSearchService;
		this.searchService = searchService;
	}

	@GET
	@Path("sync")
	public String syncToES() {
		this.elasticSearchService.syncAll();
		GenericResponse response = new GenericResponse();
		response.setSuccess(true);
		SuccessResponse successResponse = new SuccessResponse(true);
		response.setSuccessResponse(successResponse);
		String responseStr = null;
		try {
			responseStr = JsonUtil.serializeJson(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseStr;
	}

	@POST
	public String search(@DefaultValue("question")@QueryParam("type") String type,
                         @QueryParam("query") String query,
                         @DefaultValue("5")@QueryParam("size") Integer size, @DefaultValue("0")@QueryParam("from")Integer from) {
		GenericResponse response = new GenericResponse();

		if (type.equals("question")) {
			SearchResultHelper searchResponse = this.searchService.searchQuestions(query, size, from);
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setSuccess(searchResponse);
			response.setSuccess(true);
			response.setSuccessResponse(successResponse);
		}
		else if (type.equals("answer")) {
			SearchResultHelper searchResponse = this.searchService.searchAnswer(query, size, from);
			SuccessResponse successResponse = new SuccessResponse();
			successResponse.setSuccess(searchResponse);
			response.setSuccess(true);
			response.setSuccessResponse(successResponse);
		}
		else {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setReason(ErrorResponse.Reason.INVALID_SEARCH_TYPE);
			response.setFailure(errorResponse);
		}
		try {
			String responseStr = JsonUtil.serializeJson(response);
			if (response.isSuccess()) {
				return responseStr;
			}
			Response res = Response.status(Response.Status.BAD_REQUEST)
					.type(MediaType.APPLICATION_JSON)
					.entity(response)
					.build();
			throw new WebApplicationException(res);
		}
		catch (JsonProcessingException e) {
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
