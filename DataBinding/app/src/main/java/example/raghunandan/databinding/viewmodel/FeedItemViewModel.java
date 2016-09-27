package example.raghunandan.databinding.viewmodel;

import android.content.Context;

import example.raghunandan.databinding.models.FeedModel;

/**
 * Created by Raghunandan on 27-09-2016.
 */

public class FeedItemViewModel {

    private Context context;
    private FeedModel feedModel;
    private String dummy="hello";
    public FeedItemViewModel( FeedModel feedModel)
    {
        this.feedModel = feedModel;
    }



    public FeedModel getFeedModel() {
        return feedModel;
    }

    public void setFeedModel(FeedModel feedModel) {
        this.feedModel = feedModel;
    }
}
