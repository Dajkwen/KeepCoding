package com.jkwen.s5;

/**
 * 相比较于 Singleton1，可以达到延迟加载的目的
 * 但影响了并发性能
 */
public class Singleton2 {
    private Singleton2() {}

    private static Singleton2 s;

    public synchronized static Singleton2 getInstance() {
        if (s == null) {
            s = new Singleton2();
        }
        return s;
    }
}
