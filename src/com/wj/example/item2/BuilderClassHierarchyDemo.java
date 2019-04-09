package com.wj.example.item2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

abstract class BaseClass {

    enum Topping{HAM, ONION, PEPPER}
    private final Set<Topping> toppings;

    abstract static class Builder<T extends Builder<T>>{    // generic type with a recursive type parameter
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        T addTopping(Topping topping){
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract BaseClass build();
        protected abstract T self();
    }

    BaseClass(Builder<?> builder){
        toppings = builder.toppings.clone();
    }
}

class SubClass1 extends BaseClass {
    private final int size;

    static class Builder extends BaseClass.Builder<Builder> {
        private final int size; // required parameter

        Builder(int size){
            this.size = size;
        }

        @Override
        SubClass1 build(){
            return new SubClass1(this);
        }

        @Override
        protected Builder self(){
            return this;
        }
    }

    private SubClass1(Builder builder){
        super(builder);
        size = builder.size;
    }
}

class SubClass2 extends BaseClass {
    private final boolean sauceInside;

    static class Builder extends BaseClass.Builder<Builder> {
        private boolean sauceInside = false;    // optional parameter

        Builder sauceInside(){
            sauceInside = true;
            return this;
        }

        @Override
        SubClass2 build(){
            return new SubClass2(this);
        }

        @Override
        protected Builder self(){
            return this;
        }
    }

    private SubClass2(Builder builder){
        super(builder);
        sauceInside = builder.sauceInside;
    }
}


public class BuilderClassHierarchyDemo {

    public static void main(String[] args) {
        SubClass1 subClass1 = new SubClass1.Builder(3)
                .addTopping(BaseClass.Topping.HAM)
                .build();
        SubClass2 subClass2 = new SubClass2.Builder()
                .sauceInside()
                .build();
    }
}
