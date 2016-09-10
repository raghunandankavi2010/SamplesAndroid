package com.pucho.helper;

import com.pucho.data.AnswerESData;
import com.pucho.data.QuestionESData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by harshmathur on 28/02/16.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticSearchResultHelper {
    private Long total;
    private List<QuestionESData> questionEsData;
    private List<AnswerESData> answerESData;
}
