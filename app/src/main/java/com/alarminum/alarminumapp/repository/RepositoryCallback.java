package com.alarminum.alarminumapp.repository;

import com.alarminum.alarminumapp.utils.Result;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
