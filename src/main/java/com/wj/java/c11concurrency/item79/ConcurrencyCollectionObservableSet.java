package com.wj.java.c11concurrency.item79;

import com.wj.java.c04classesandinterfaces.item18.ForwardingSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrencyCollectionObservableSet<E> extends ForwardingSet<E> implements ObservableSet<E> {

    private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<>();

    public ConcurrencyCollectionObservableSet(Set<E> s){
        super(s);
    }

    public void addObserver(SetObserver<E> observer){
            observers.add(observer);
    }

    public void removeObserver(SetObserver<E> observer){
            observers.remove(observer);
    }

    public void removeAllObserver(){
            observers.clear();
    }

    // invoke alien method from synchronized block
    private void notifyElementAdded(E element){
        for(SetObserver<E> observer : observers){
            observer.added(this, element);
        }
    }

    @Override
    public boolean add(E element){
        boolean added = super.add(element);
        if (added)
            notifyElementAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c){
        boolean result = false;
        for (E e : c)
            result |= add(e);
        return result;
    }

}
