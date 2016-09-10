package com.pucho.domain;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pucho.PuchoMainService;
import com.pucho.core.instrumentation.QueueNames;
import com.pucho.helper.ELasticIndexUnit;
import com.pucho.service.MQService;
import com.pucho.service.TranslateService;
import com.pucho.service.impl.ElasticSearchServiceImpl;
import com.pucho.utils.JsonUtil;

import ru.vyarus.dropwizard.guice.injector.lookup.InjectorLookup;

public class AnswerEventListener {

    private MQService mqService;
    private TranslateService translateService;

    public AnswerEventListener() {
        this.mqService = InjectorLookup.getInjector(PuchoMainService.getPuchoMainService()).get().getBinding(MQService.class).getProvider()
                .get();
        this.translateService = InjectorLookup.getInjector(PuchoMainService.getPuchoMainService()).get().getBinding(TranslateService.class).getProvider()
                .get();

    }

    @PostPersist
    protected void postPersist(Answer answer) {
        //System.out.println("answerPostPersist");

        try {
            this.mqService.sendMessage(QueueNames.ANSWERUPDATEQUEUE, JsonUtil.serializeJson(answer));
        } catch (JsonProcessingException e) {
            System.out.println("Failed to Send message to RabbitMq for answer id: " + answer.getId());
            e.printStackTrace();
        }


        if(answer.getLanguageContents() == null || answer.getLanguageContents().size() ==0) {
            String hindiContent = translateService.translateString(answer.getContent(), "HINDI");
            if(hindiContent != null && !hindiContent.isEmpty()) {
                AnswerLanguageContent languageContent = new AnswerLanguageContent();
                languageContent.setLanguage("HINDI");
                languageContent.setContent(hindiContent);
                answer.addLanguageContents(languageContent);
                answer.persist();
            }
        }

    }

    @PostUpdate
    protected void postUpdate(Answer answer) {
        //System.out.println("answerPostUpdate");
        try {
            this.mqService.sendMessage(QueueNames.ANSWERUPDATEQUEUE, JsonUtil.serializeJson(answer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
