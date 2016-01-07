package com.myapps.ecowash.bl;

import com.parse.ParseException;

public interface ParseCallback<T> {
    void onSuccess(T result);
    void onFailure(ParseException exception);
}
