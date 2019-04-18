package com.wj.java.c11concurrency.item79;

public interface ObservableSet<E> {

    void removeObserver(SetObserver<E> observer);
}
