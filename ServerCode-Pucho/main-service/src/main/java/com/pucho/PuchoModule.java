package com.pucho;

import com.google.inject.AbstractModule;
import com.pucho.annotations.PushNotification;
import com.pucho.annotations.SMSNotification;
import com.pucho.service.*;
import com.pucho.repository.FileRepository;
import com.pucho.repository.FileRepositoryImpl;
import com.pucho.service.impl.*;

public class PuchoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LoginService.class).to(LoginServiceImpl.class);

        bind(NotificationService.class).annotatedWith(PushNotification.class).to(PushNotificationService.class);
        bind(NotificationService.class).annotatedWith(SMSNotification.class).to(SMSNotificationService.class);
        bind(TranslateService.class).to(BingTranslateService.class);
//        bind(TranslateService.class).to(GoogleTranslateService.class);
        bind(FileService.class).to(FileServiceImpl.class);
        bind(FileRepository.class).to(FileRepositoryImpl.class);
        bind(SMSService.class).to(SMSServiceImpl.class);
        bind(NotificationRegisterService.class).to(NotificationRegisterServiceImpl.class);
        bind(MQService.class).to(RabbitMQServiceImpl.class);
        bind(ElasticSearchService.class).to(ElasticSearchServiceImpl.class);
        bind(MLService.class).to(MLServiceImpl.class);
        bind(SMSService.class).to(SMSServiceImpl.class);
        bind(SearchService.class).to(SearchServiceImpl.class);
        bind(QAService.class).to(QAServiceImpl.class);
    }
    // @Provides
    // public TranslateService provideSMSService(PuchoConfiguration
    // puchoConfiguration){
    // return new
    // BingTranslateService(puchoConfiguration.getTranslatorConfiguration());
    // }
}
