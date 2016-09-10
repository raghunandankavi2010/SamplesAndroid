package com.pucho.helper;

import com.pucho.domain.Answer;
import com.pucho.domain.Question;
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
public class SearchResultHelper {
    private Long total;
    private List<Question> questionsData;
    private List<Answer> answersData;
}
