package com.lyft.mordor;

import android.app.Application;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

/**
 * Created by zakharov on 3/23/14.
 */
public class App extends Application {
    private MortarScope rootScope;

    @Override public void onCreate() {
        super.onCreate();

        // So that exceptions thrown in RxJava onError methods don't have their stack traces swallowed.
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override public void handleError(Throwable e) {
                throw new RuntimeException(e);
            }
        });

        rootScope =
                Mortar.createRootScope(BuildConfig.DEBUG, ObjectGraph.create(new AppModule()));
    }

    public MortarScope getRootScope() {
        return rootScope;
    }
}
