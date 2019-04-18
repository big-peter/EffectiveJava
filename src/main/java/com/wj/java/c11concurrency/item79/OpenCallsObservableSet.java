package com.wj.java.c11concurrency.item79;

import com.wj.java.c04classesandinterfaces.item18.ForwardingSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class OpenCallsObservableSet<E> extends ForwardingSet<E> implements ObservableSet<E> {

    private final List<SetObserver<E>> observers = new ArrayList<>();

    public OpenCallsObservableSet(Set<E> s){
        super(s);
    }

    public void addObserver(SetObserver<E> observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void removeObserver(SetObserver<E> observer){
        synchronized (observers){
            observers.remove(observer);
        }
    }

    public void removeAllObserver(){
        synchronized (observers){
            observers.clear();
        }
    }

    // invoke alien method from synchronized block
    private void notifyElementAdded(E element){
        List<SetObserver<E>> snapshot = null;
        synchronized (observers){
            snapshot = new ArrayList<>(observers);
        }
        for(SetObserver<E> observer : snapshot){
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
