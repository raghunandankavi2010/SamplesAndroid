package example.raghunandan.databinding.util;

import java.util.ArrayList;
import java.util.List;

import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.models.QuestionAskedBy;
import example.raghunandan.databinding.viewmodel.FeedViewModel;

/**
 * Created by Raghunandan on 28-09-2016.
 */

public class MockFeedList {


    public static List<FeedModel> feeds() {

        List<FeedModel> feedModelList = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            FeedModel feedModel = new FeedModel();
            QuestionAskedBy questionAskedBy = new QuestionAskedBy();
            questionAskedBy.setFullName("Raghunandan Kavi");
            feedModel.setUser(questionAskedBy);
            feedModelList.add(feedModel);
        }

        return feedModelList;
    }
}
