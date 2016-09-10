package com.pucho.helper;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticSearchBulkResponseListener implements ActionListener<BulkResponse> {

	@Override
	public void onResponse(BulkResponse response) {
		//do nothing
		
	}

	@Override
	public void onFailure(Throwable e) {
		Logger logger = LoggerFactory.getLogger(ElasticSearchResponseListener.class);
	    logger.info(e.getMessage());
	}

}
