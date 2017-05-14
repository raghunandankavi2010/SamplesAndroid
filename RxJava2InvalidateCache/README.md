# RxJava2 Invalidate Cached Observable


During rotation change you want to continue with network operation wothout having to launch another operation.

So we use a singleton and use a .cache() operator. This prevents launching another network operation.

But there is one problem you may need to invalidate the cache.

The solution was posted by Dave Moten. More info at http://stackoverflow.com/questions/31733455/rxjava-observable-cache-invalidate

This is just a implementation in RxJava2 using the same solution from the stackoverflow link.

![ScreenShot](https://github.com/raghunandankavi2010/SamplesAndroid/blob/master/RxJava2InvalidateCache/device-2017-05-14-230724.png)

