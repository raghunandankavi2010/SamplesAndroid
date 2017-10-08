package com.example.raghu.retrofitsample.test2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by raghu on 6/10/17.
 */

public class Example {

    @SerializedName("related_ids")
    @Expose
    private List<Integer> relatedIds = null;
    @SerializedName("upsells_ids")
    @Expose
    private List<Integer> upsellsIds = null;

    public List<Integer> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(List<Integer> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public List<Integer> getUpsellsIds() {
        return upsellsIds;
    }

    public void setUpsellsIds(List<Integer> upsellsIds) {
        this.upsellsIds = upsellsIds;
    }

}