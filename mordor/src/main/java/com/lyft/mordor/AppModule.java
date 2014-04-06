package com.lyft.mordor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyft.mordor.core.GsonParcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Parcer;

/**
 * Created by zakharov on 3/23/14.
 */

@Module(includes = {}, library = true)
public class AppModule {
    @Provides @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides @Singleton
    Parcer<Object> provideParcer(Gson gson) {
        return new GsonParcer<Object>(gson);
    }
}
