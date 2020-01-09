package com.example.weatherapp;

public interface Lifecycles<V> {
    void bind(V view);
    void unBind();
}
