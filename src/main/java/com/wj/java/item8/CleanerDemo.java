package com.wj.java.item8;

import java.lang.ref.Cleaner;

// An autocloseable class using a cleaner as a safety net
class Room implements AutoCloseable{
    // The behavior of cleaners during System.exit is implementation specific. No guarantee are made relating to whether
    // cleaning actions are invoked or not.

    private final static Cleaner cleaner = Cleaner.create();

    // Resource that requires cleaning. Must not refer to Room(use static nested class)!
    private static class State implements Runnable{

        int numJunkPiles;

        State(int numJunkPiles){
            this.numJunkPiles = numJunkPiles;
        }

        // Invoked by close method or cleaner
        @Override
        public void run() {
            System.out.println("Cleaning room");
            numJunkPiles = 0;
        }
    }

    private int numJunkPiles;
    // used to auto-close
    private final State state;
    // Our cleanable. Cleans the room when it's eligible for gc
    private final Cleaner.Cleanable cleanable;

    Room(int numJunkPiles){
        this.numJunkPiles = numJunkPiles;
        state = new State(this.numJunkPiles);
        cleanable = cleaner.register(this, state);
    }

    public int getNumJunkPiles(){
        return numJunkPiles;
    }

    @Override
    public void close() throws Exception {
        cleanable.clean();
    }

}


public class CleanerDemo {
    public static void main(String[] args) throws Exception {
        try(Room room = new Room(3);){
            System.out.println(room.getNumJunkPiles());
            System.out.println("Goodbye");
        }

        System.out.println("-------------------------------\n");

        Room room = new Room(5);
        // may or not invoke close method
        System.out.println("Peace Out");

        System.out.println("-------------------------------\n");

        Room room1 = new Room(10);
        System.gc();    // may or not invoke close method
        System.out.println("Cleaning Done");
    }
}
