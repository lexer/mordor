package com.lyft.mordor.screens;

import android.os.Bundle;
import android.util.Log;

import com.lyft.mordor.MainActivity;
import com.lyft.mordor.views.MainView;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.Blueprint;
import mortar.ViewPresenter;

/**
 * Created by zakharov on 3/23/14.
 */
public class Main implements Blueprint {
    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(injects = {MainActivity.class, MainView.class})
    class Module {
    }

    @Singleton
    public static class Presenter extends ViewPresenter<MainView> {

        private static final String TAG = "Main.Presenter";

        @Inject
        Presenter() {
            Log.d(TAG, "presenter created");
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            Log.d(TAG, "presenter onLoad");
            getView().show("hello");

        }

        @Override
        protected void onSave(Bundle outState) {

            Log.d(TAG, "presenter onSave");
            super.onSave(outState);

        }
    }
}
