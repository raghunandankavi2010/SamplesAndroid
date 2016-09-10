package com.pucho.helper;

import com.pucho.domain.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by dinesh.rathore on 27/09/15.
 */
@Getter
@Setter
public class MultiLanResponse {
    List<Question> data;

    public MultiLanResponse(List<Question> data) {
        this.data = data;
    }
}
