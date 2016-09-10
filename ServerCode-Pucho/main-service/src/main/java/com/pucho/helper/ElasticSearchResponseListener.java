package com.pucho.helper;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchResponseListener implements ActionListener<IndexResponse>{

	@Override
	public void onResponse(IndexResponse response) {
		//do nothing
	}

	@Override
	public void onFailure(Throwable e) {
		Logger logger = LoggerFactory.getLogger(ElasticSearchResponseListener.class);
	    logger.info(e.getMessage());
	}

}
