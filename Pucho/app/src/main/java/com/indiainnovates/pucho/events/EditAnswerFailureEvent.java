package com.indiainnovates.pucho.events;

/**
 * Created by Raghunandan on 18-04-2016.
 */
public class EditAnswerFailureEvent {


        private String failure;
        public EditAnswerFailureEvent(String failure)
        {
            this.failure =failure;
        }

        public String getFailure() {
            return failure;
        }


}
