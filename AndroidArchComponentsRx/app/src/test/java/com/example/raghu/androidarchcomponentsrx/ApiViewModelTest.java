package com.example.raghu.androidarchcomponentsrx;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.repo.ApiRepository;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by raghu on 2/12/17.
 */
@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class ApiViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ApiRepository repository;
    private ApiViewModel apiViewModel;

    @Before
    public void setup() {
        repository = mock(ApiRepository.class);
        apiViewModel = new ApiViewModel(repository);
    }


   @Test
    public void testNull() {

       MutableLiveData<Resource<Example>> resourceMutableLiveData = new MutableLiveData<>();

       when(apiViewModel.getData()).thenReturn(resourceMutableLiveData);
       assertThat(apiViewModel.getData(),notNullValue() );
       verify(repository).getData();
    }


    @Test
    public void loadData() {

        apiViewModel.getData();
        when(repository.getData()).thenReturn(mock(LiveData.class));
        verify(repository).getData();


    }

    @Test
    public void sendResultToUI() {
        MutableLiveData<Resource<Example>> resourceMutableLiveData = new MutableLiveData<>();


        when(repository.getData()).thenReturn(resourceMutableLiveData);
        Observer<Resource<Example>> observer = mock(Observer.class);
        apiViewModel.getData().observeForever(observer);
        Example example = TestUtil.createSampleExample("Raghunandan", "30");
        Resource<Example> exampleResource = Resource.success(example);

        resourceMutableLiveData.setValue(exampleResource);
        verify(observer).onChanged(exampleResource);

        reset(observer);


        Example exampleExample = TestUtil.createSampleExample("Raghunandan Kavi", "30");
        Resource<Example> exampleResourceExample = Resource.success(exampleExample);

        resourceMutableLiveData.setValue(exampleResourceExample);

        verify(observer).onChanged(exampleResourceExample);


    }

}
