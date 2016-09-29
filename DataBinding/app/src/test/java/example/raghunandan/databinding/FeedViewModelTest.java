package example.raghunandan.databinding;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import example.raghunandan.databinding.apis.DataManager;
import example.raghunandan.databinding.models.FeedModel;
import example.raghunandan.databinding.models.FeedResponse;
import example.raghunandan.databinding.util.MockFeedList;
import example.raghunandan.databinding.viewmodel.FeedViewModel;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Raghunandan on 28-09-2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class FeedViewModelTest {

    @Mock
    Context mMockContext;

    FeedViewModel feedViewModel;
    @Mock
    FeedViewModel.DataListener dataListener;

    @Mock
    DataManager dataManager;

    @Mock
    Observable<FeedResponse> observable;


    @Before
    public void setUp() {

        feedViewModel = new FeedViewModel(mMockContext, dataListener);

    }

    @Test
    public void testShouldScheduleLoadFromAPIOnBackgroundThread() {


        List<FeedModel> mockFeedModel = MockFeedList.feeds();
        when(dataManager.fetchFeed()).thenReturn(observable);


        feedViewModel.fetchFeed();

        dataListener.onDataChanged(mockFeedModel);
        verify(dataListener).onDataChanged(mockFeedModel);

    }
}
