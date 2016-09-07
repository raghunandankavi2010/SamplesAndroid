package com.india.innovates.pucho.events;

import com.india.innovates.pucho.models.Answers;

import java.util.List;

/**
 * Created by Raghunandan on 01-02-2016.
 */
public class AnswersEvent {

    private List<Answers> answers;

    public AnswersEvent(List<Answers> answers)
    {
        this.answers = answers;
    }

    public List<Answers> getAnswers() {
        return answers;
    }
}
