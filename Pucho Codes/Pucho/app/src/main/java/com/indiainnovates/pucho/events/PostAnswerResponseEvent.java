package com.indiainnovates.pucho.events;

import com.indiainnovates.pucho.models.PostAnswerResponse;

import java.util.List;

/**
 * Created by Raghunandan on 29-01-2016.
 */
public class PostAnswerResponseEvent {

    private List<PostAnswerResponse> postAnswerResponseList;

    public PostAnswerResponseEvent(List<PostAnswerResponse> postAnswerResponseList)
    {
        this.postAnswerResponseList = postAnswerResponseList;
    }

    public List<PostAnswerResponse> getPostAnswerResponseList() {
        return postAnswerResponseList;
    }
}
