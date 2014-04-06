package com.lyft.mordor.utils;

import com.lyft.mordor.core.MainThread;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by zakharov on 4/6/14.
 */
public class Binder {
    CompositeSubscription subscriptions = new CompositeSubscription();

    public <T> Binder bind(final Observable<T> property, final PropertyChangedCallback<T> callback) {

        Subscription subscription = property
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<T>() {
                    T prevValue;

                    @Override
                    public void call(T newValue) {
                        if (!newValue.equals(prevValue)) {
                            callback.onPropertyChanged(newValue);
                            prevValue = newValue;
                        }
                    }
                });

        subscriptions.add(subscription);

        return this;
    }

    public void detach() {
        subscriptions.clear();
    }

}

