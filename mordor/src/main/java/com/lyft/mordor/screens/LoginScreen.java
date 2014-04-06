package com.lyft.mordor.screens;

import android.os.Bundle;
import android.util.Log;

import com.lyft.mordor.R;
import com.lyft.mordor.utils.Binder;
import com.lyft.mordor.views.LoginView;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by zakharov on 4/6/14.
 */
@Layout(R.layout.login)
public class LoginScreen implements Blueprint {
    private static final String TAG = "LoginScreen";

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
            injects = LoginView.class,
            addsTo = Main.Module.class,
            library = true)
    static class Module {

    }

    @Singleton
    public static class Presenter extends ViewPresenter<LoginView> {
        private final Flow flow;
        private BehaviorSubject<Boolean> inProgress = BehaviorSubject.create(false);

        @Inject
        Presenter(Flow flow) {
            this.flow = flow;
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            LoginView view = getView();
            if (view == null) return;

        }

        public void login(String userName, String password) {
            inProgress.onNext(true);

            Observable.timer(10, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    inProgress.onNext(false);

                    Log.d(TAG, "inProgress:" + false);

                }
            });
        }

        public Observable<Boolean> inProgress() {
            return inProgress;
        }
    }
}
