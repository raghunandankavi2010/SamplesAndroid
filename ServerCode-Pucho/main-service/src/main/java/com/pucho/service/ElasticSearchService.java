package com.pucho.service;

import java.util.List;

import com.pucho.helper.ELasticIndexUnit;
import com.pucho.helper.ElasticSearchResultHelper;

public interface ElasticSearchService {

    public void push(ELasticIndexUnit indexUnit);

    public void pushBulk(List<ELasticIndexUnit> indexUnitList);

    public ElasticSearchResultHelper searchQuestion(String queryString, List<String> tags, Integer size, Integer from);

    public ElasticSearchResultHelper searchAnswer(String queryString, Integer size, Integer from);
    
    public void syncAll();
}
