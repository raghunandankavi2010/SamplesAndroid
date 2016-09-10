package com.pucho.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.api.client.json.Json;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.pucho.core.instrumentation.ElasticSearchConstants;
import com.pucho.data.AnswerESData;
import com.pucho.helper.*;
import org.apache.lucene.queryparser.xml.builders.FilteredQueryBuilder;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkAction;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexAction;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.pucho.configuration.ElasticSearchConfiguration;
import com.pucho.configuration.PuchoConfiguration;
import com.pucho.data.QuestionESData;
import com.pucho.domain.Answer;
import com.pucho.domain.Question;
import com.pucho.service.ElasticSearchService;
import com.pucho.service.MLService;
import com.pucho.utils.JsonUtil;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchServiceImpl implements ElasticSearchService {

	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);
	private final ElasticSearchConfiguration elasticSearchConfiguration;
	private final MLService mlService;

	private Client client = null;

	@Inject
	public ElasticSearchServiceImpl(PuchoConfiguration configuration, MLService mlService) {
		this.elasticSearchConfiguration = configuration.getElasticSearchConfiguration();
		this.mlService = mlService;
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", this.elasticSearchConfiguration.getClusterName()).build();
		try {
			this.client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(
							InetAddress.getByName(this.elasticSearchConfiguration.getAddress()),
							this.elasticSearchConfiguration.getPort()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void push(ELasticIndexUnit indexUnit) {
		IndexRequestBuilder builder = IndexAction.INSTANCE.newRequestBuilder(this.client);
		builder.setIndex(indexUnit.getIndex());
		builder.setType(indexUnit.getType());
		builder.setId(indexUnit.getId());
		builder.setSource(indexUnit.getBodyAsJson());
		if (indexUnit.getParent() != null) {
			builder.setParent(indexUnit.getParent());
		}
		try {
			System.out.println(JsonUtil.serializeJson(indexUnit));
			builder.execute(new ElasticSearchResponseListener());
		} catch (NoNodeAvailableException e) {
			// do nothing, ES config is wrong
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void pushBulk(List<ELasticIndexUnit> indexUnitList) {
		BulkRequestBuilder builder = BulkAction.INSTANCE.newRequestBuilder(this.client);
		for (ELasticIndexUnit indexUnit : indexUnitList) {
			IndexRequestBuilder indexbuilder = IndexAction.INSTANCE.newRequestBuilder(this.client);
			indexbuilder.setIndex(indexUnit.getIndex());
			indexbuilder.setType(indexUnit.getType());
			indexbuilder.setId(indexUnit.getId());
			indexbuilder.setSource(indexUnit.getBodyAsJson());
			if (indexUnit.getParent() != null) {
				indexbuilder.setParent(indexUnit.getParent());
			}
			builder.add(indexbuilder);
		}
		try {
			builder.execute(new ElasticSearchBulkResponseListener());
		} catch (NoNodeAvailableException e) {
			// do nothing, ES config is wrong
		}
	}

	@Override
	public ElasticSearchResultHelper searchQuestion(String queryString, List<String> tags, Integer size, Integer from) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.must(QueryBuilders.multiMatchQuery(queryString, "title", "content")
						.analyzer("str_index_analyzer").operator(MatchQueryBuilder.Operator.AND)
						.type(MultiMatchQueryBuilder.Type.MOST_FIELDS))
				.should(QueryBuilders.termsQuery("tags", tags));

		final SearchRequestBuilder request = this.client.prepareSearch(ElasticSearchConstants.index)
				.setTypes(ElasticSearchConstants.questionType)
				.setQuery(query)
				.setSize(size)
				.setFrom(from);
		ElasticSearchResultHelper result = new ElasticSearchResultHelper();
		try {
			SearchResponse searchResponse = request.execute().actionGet();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			List<QuestionESData> questionESDataList = new ArrayList<>();
			for (SearchHit searchHit: searchHits) {
				String questionESDataString = searchHit.getSourceAsString();
				try {
					questionESDataList.add(new Gson().fromJson(questionESDataString, QuestionESData.class));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result.setQuestionEsData(questionESDataList);
			result.setTotal(searchResponse.getHits().getTotalHits());
		}
		catch (NoNodeAvailableException e) {
			//do nothing, ES config is wrong
		}
		return result;

	}

	@Override
	public ElasticSearchResultHelper searchAnswer(String queryString, Integer size, Integer from) {
		QueryBuilder query = QueryBuilders.matchQuery("content", queryString)
				.operator(MatchQueryBuilder.Operator.AND)
				.analyzer("str_index_analyzer");

		final SearchRequestBuilder request = this.client.prepareSearch(ElasticSearchConstants.index)
				.setTypes(ElasticSearchConstants.answerType)
				.setQuery(query)
				.setSize(size)
				.setFrom(from);
		ElasticSearchResultHelper result = new ElasticSearchResultHelper();
		try {
			SearchResponse searchResponse = request.execute().actionGet();
			SearchHit[] searchHits = searchResponse.getHits().getHits();
			List<AnswerESData> answerESDataList = new ArrayList<>();
			for (SearchHit searchHit: searchHits) {
				String answerESDataString = searchHit.getSourceAsString();
				try {
					answerESDataList.add(new Gson().fromJson(answerESDataString, AnswerESData.class));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result.setAnswerESData(answerESDataList);
			result.setTotal(searchResponse.getHits().getTotalHits());
		}
		catch (NoNodeAvailableException e) {
			//do nothing, ES config is wrong
		}
		return result;
	}

	@Override
	public void syncAll() {
		List<Question> questionList = Question.all();
		List<ELasticIndexUnit> indexUnitList = new ArrayList<>();
		String index = ElasticSearchConstants.index;
		String questionType = ElasticSearchConstants.questionType;
		String answerType = ElasticSearchConstants.answerType;
		for (Question question: questionList) {
			List<String> tags = this.mlService.getTags(question.getTitle());
            QuestionESData quesEsData = question.toESData();
            quesEsData.setTags(tags);
            indexUnitList.add(new ELasticIndexUnit(index, questionType,
                            question.getId().toString(), quesEsData.toString(), null));
            Set<Answer> answerSet = question.getAnswers();
            for (Answer answer: answerSet) {
            	indexUnitList.add(new ELasticIndexUnit(index, answerType, answer.getId().toString(), answer.toESData().toString(),
            			question.getId().toString()));
            }
		}
		this.pushBulk(indexUnitList);
	}
}
