package com.indiainnovates.pucho.events;

import com.indiainnovates.pucho.models.QuestionContentModel;

import java.util.List;

/**
 * Created by Raghunandan on 14-04-2016.
 */
public class EditQuestionEvent
{

        private String res;

        public EditQuestionEvent(String response)
        {
            this.res = response;
        }

        public String getResponse() {
            return res;
        }
}
