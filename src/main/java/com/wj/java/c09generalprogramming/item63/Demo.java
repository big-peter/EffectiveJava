package com.wj.java.c09generalprogramming.item63;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/*
    Beware the performance of string concatenation
 */

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class Demo {

    private static final int LOOP_NUM = 10000;
    private static final String APPEND_STR = "a";

    @Benchmark
    public void testStringConcat(Blackhole bh){
        String s = "";
        for(int i = 0; i < LOOP_NUM; i++){
            s += APPEND_STR;
            bh.consume(APPEND_STR);
        }
    }

    @Benchmark
    public void testStringBuilderConcat(Blackhole bh){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LOOP_NUM; i++){
            stringBuilder.append(APPEND_STR);
            bh.consume(APPEND_STR);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Demo.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

}
