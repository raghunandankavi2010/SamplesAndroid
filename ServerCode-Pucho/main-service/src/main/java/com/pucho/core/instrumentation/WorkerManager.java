package com.pucho.core.instrumentation;

import com.google.inject.Inject;

import io.dropwizard.lifecycle.Managed;


public class WorkerManager implements Managed {
    
    private final SMSWorker smsWorker;
    private final QuestionUpdateHandler questionWorker;
    private final AnswerUpdateHandler answerWorker;
    private final ElasticSearchSyncWorker elasticWorker;

    @Inject
    public WorkerManager(SMSWorker smsWorker, QuestionUpdateHandler questionWorker, AnswerUpdateHandler answerWorker, ElasticSearchSyncWorker elasticWorker) {
        this.smsWorker = smsWorker;
        this.questionWorker = questionWorker;
        this.answerWorker = answerWorker;
        this.elasticWorker = elasticWorker;
    }

    @Override
    public void start() throws Exception {
        (new Thread(smsWorker)).start();
        (new Thread(questionWorker)).start();
        (new Thread(answerWorker)).start();
        (new Thread(elasticWorker)).start();
    }

    @Override
    public void stop() throws Exception {
    }

}
