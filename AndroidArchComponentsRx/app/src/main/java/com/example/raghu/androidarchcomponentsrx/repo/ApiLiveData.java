package com.example.raghu.androidarchcomponentsrx.repo;

import android.arch.lifecycle.MediatorLiveData;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.vo.Resource;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by raghu on 26/9/17.
 */

public class ApiLiveData<T> extends MediatorLiveData<Resource<T>> {

    Disposable disposable;



    public void getData(Single<Response<T>> apicall){


        disposable = apicall
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<Response<T>, Throwable>() {
                    @Override
                    public void accept(Response<T> exampleResponse, Throwable throwable) throws Exception {

                        //Resource<T> success = (Resource<T>) Resource.success(exampleResponse.body());
                        if(exampleResponse.isSuccessful()){

                            setValue(Resource.success(exampleResponse.body()));
                            //Resource<T> successr = Resource.success(exampleResponse.body());
                            //Resource<T> success = convertInstanceOfObject(Resource.success(exampleResponse.body()), Resource.class);

                            //setValue(success);
                        }else {
                            if(throwable!=null){
                                throwable.printStackTrace();
                            }
                            Resource<T> errorr = Resource.error(exampleResponse.message(),null);
                            //Resource<T> error = convertInstanceOfObject(Resource.error(exampleResponse.message(),null), Resource.class);
                            setValue(errorr);
                        }
                        //throwable.printStackTrace();
                    }
                });


    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if(disposable!=null)
            disposable.dispose();
    }


    /*public static <T> Resource<T> convertInstanceOfObject(Object o, Class<Resource> clazz) {
            return clazz.cast(o);

    }*/


}