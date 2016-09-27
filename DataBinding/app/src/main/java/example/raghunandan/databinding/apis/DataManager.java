package example.raghunandan.databinding.apis;

import example.raghunandan.databinding.models.FeedResponse;
import io.reactivex.Observable;


/**
 * Created by Raghunandan on 25-09-2016.
 */
public class DataManager {

    private FeedApi feedApi;
    private Observable<FeedResponse> feedResponseObservable;


    public DataManager()
    {

            feedApi = new FeedApi();
    }

    public Observable<FeedResponse> fetchFeed() {

        feedResponseObservable = feedApi.fetchFeed(1);
        return feedResponseObservable;


    }
}
