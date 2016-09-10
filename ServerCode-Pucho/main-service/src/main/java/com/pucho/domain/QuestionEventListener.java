package com.pucho.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Optional;
import com.google.inject.Injector;
import com.pucho.PuchoMainService;
import com.pucho.core.instrumentation.QueueNames;
import com.pucho.service.MQService;
import com.pucho.service.TranslateService;
import com.pucho.utils.JsonUtil;

import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup;

public class QuestionEventListener {

    private MQService mqService;
    private TranslateService translateService;

    public QuestionEventListener() {
        this.mqService = InjectorLookup.getInjector(PuchoMainService.getPuchoMainService()).get().getBinding(MQService.class).getProvider()
                .get();
        this.translateService = InjectorLookup.getInjector(PuchoMainService.getPuchoMainService()).get().getBinding(TranslateService.class).getProvider()
                .get();

    }

    @PostPersist
    protected void postPersist(Question question) {
        System.out.println("questionPostPersist");
        try {
            this.mqService.sendMessage(QueueNames.QUESTIONUPDATEQUEUE, JsonUtil.serializeJson(question));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(question.getLanguageContents() == null || question.getLanguageContents().size() ==0) {
            String hindiContent = translateService.translateString(question.getTitle(), "HINDI");
            if(hindiContent != null && !hindiContent.isEmpty()) {
                QuestionLanguageContent languageContent = new QuestionLanguageContent();
                languageContent.setLanguage("HINDI");
                languageContent.setContent(hindiContent);
                question.addLanguageContents(languageContent);
                question.persist();
            }
        }

    }

    @PostUpdate
    protected void postUpdate(Question question) {
        System.out.println("questionPostUpdate");
        try {
            this.mqService.sendMessage(QueueNames.QUESTIONUPDATEQUEUE, JsonUtil.serializeJson(question));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
