package com.wj.example.concurrency.item79;

public interface ObservableSet<E> {

    void removeObserver(SetObserver<E> observer);
}
