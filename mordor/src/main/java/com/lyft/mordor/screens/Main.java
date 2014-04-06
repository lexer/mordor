package com.lyft.mordor.screens;

import com.lyft.mordor.AppModule;
import com.lyft.mordor.android.ActionBarModule;
import com.lyft.mordor.android.ActionBarOwner;
import com.lyft.mordor.core.MainScope;
import com.lyft.mordor.utils.FlowOwner;
import com.lyft.mordor.views.MainView;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.Flow;
import flow.HasParent;
import flow.Parcer;
import mortar.Blueprint;
import rx.util.functions.Action0;

/**
 * Created by zakharov on 4/6/14.
 */
public class Main implements Blueprint {

    @Override public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module( //
            includes = ActionBarModule.class,
            injects = MainView.class,
            addsTo = AppModule.class, //
            library = true //
    )
    public static class Module {
        @Provides
        @MainScope
        Flow provideFlow(Presenter presenter) {
            return presenter.getFlow();
        }
    }

    @Singleton
    public static class Presenter extends FlowOwner<Blueprint, MainView> {

        @Inject
        Presenter(Parcer<Object> flowParcer) {
            super(flowParcer);
        }

        @Override protected Blueprint getFirstScreen() {
            return new LoginScreen();
        }
    }
}
