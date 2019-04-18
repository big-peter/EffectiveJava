package com.wj.java.c05generics.item28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// typesafe
public class ListBasedChooser<T> {
    private final List<T> choices;

    public ListBasedChooser(Collection<T> cl){
        choices = new ArrayList<>(cl);
    }

    public T choose(){
        Random rnd = ThreadLocalRandom.current();
        return choices.get(rnd.nextInt(choices.size()));
    }
}


class ArrayBasedChooser<T> {
    private final T[] choices;

    @SuppressWarnings("unchecked")
    ArrayBasedChooser(Collection<T> cl){
        // can grantee typesafe by programmer
        choices = (T[]) cl.toArray();
    }
}


class ObjectBasedChooser {
    private final Object[] choices;

    ObjectBasedChooser(Collection cl){
        choices = cl.toArray();
    }
}
