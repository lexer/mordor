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

    @Override
    public void onCreate() {
        super.onCreate();

        rootScope =
                Mortar.createRootScope(BuildConfig.DEBUG, ObjectGraph.create(new AppModule()));
    }

    @Override
    public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return rootScope;
        }
        return super.getSystemService(name);
    }
}
