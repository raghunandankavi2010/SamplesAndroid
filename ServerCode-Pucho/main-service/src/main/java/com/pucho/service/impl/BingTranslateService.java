package com.pucho.service.impl;

import com.memetix.mst.detect.Detect;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.pucho.configuration.TranslatorConfiguration;
import com.pucho.service.TranslateService;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
public class BingTranslateService implements TranslateService {

    private TranslatorConfiguration translatorConfiguration;

//    public BingTranslateService(TranslatorConfiguration translatorConfiguration) {
//        this.translatorConfiguration = translatorConfiguration;
////        Translate.setClientId(translatorConfiguration.getClientId());
////        Translate.setClientSecret(translatorConfiguration.getClientSecret());
//
//        Translate.setClientId("575a9c74-579a-4713-ba3c-713bbad84810");
//        Translate.setClientSecret("SAhYpIE7qgzeBNFWS6mJ4FthZipXgoy4BchUEepiPkc");
//
//    }

    public BingTranslateService() {
        Translate.setClientId("575a9c74-579a-4713-ba3c-713bbad84810");
        Translate.setClientSecret("SAhYpIE7qgzeBNFWS6mJ4FthZipXgoy4BchUEepiPkc");
    }


    @Override
    public String translateString(String input, String destLan) {
        String translatedText = null;
        try{
            translatedText = Translate.execute(input, Language.valueOf(destLan));
        }catch (Exception e){
            e.printStackTrace();
        }
        return translatedText;

    }

    public String[] translateStrings(String[] input, String destLan) {
        String[] translatedText = null;
        try{
            translatedText= Translate.execute(input, Language.valueOf(destLan));
        }catch (Exception e){
            e.printStackTrace();
        }
        return translatedText;

    }

//    public static void main(String[] args) {
//        try {
//            BingTranslateService bingTranslateService = new BingTranslateService();
//            System.out.println(bingTranslateService.translateString("Hello Vikram", "HINDI"));
//            Language detectedLanguage = Detect.execute("नमस्ते विक्रम");
//
//            System.out.println(detectedLanguage.toString());
//
//            String[] sourceTexts =
//                    {
//                            "Hello Vikram"
//                    };
//            String[] result = bingTranslateService.translateStrings(sourceTexts, "HINDI");
//            for(String r : result) {
//                System.out.println(r);
//            }
//
//
//        }catch (Exception e ){
//
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
