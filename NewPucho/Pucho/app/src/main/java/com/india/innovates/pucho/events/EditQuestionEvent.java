package com.india.innovates.pucho.events;

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
