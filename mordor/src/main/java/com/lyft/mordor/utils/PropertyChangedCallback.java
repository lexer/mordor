package com.lyft.mordor.utils;

/**
* Created by zakharov on 4/6/14.
*/
public interface PropertyChangedCallback<T> {
    void onPropertyChanged(T value);
}
